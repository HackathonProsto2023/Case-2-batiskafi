package com.example.backend.Controller;

import com.example.backend.Models.GenerationRule;
import com.example.backend.Models.Table;
import com.github.javafaker.Faker;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.*;
import java.util.*;

@CrossOrigin
@RestController
@RequestMapping(value = "/api")
public class Controller {

    private final Faker faker = new Faker(new Locale("en"));

    @GetMapping("/dbase")
    @ResponseBody
    public ResponseEntity<?> getDatabase() {
        Connection c = null;
        Statement statement = null;
        try {
            // connect to database
            c = DriverManager.getConnection("jdbc:postgresql://194.58.108.56:5432/prostoXsber",
                    "admin", "admin");

            c.setAutoCommit(false);
            System.out.println("Opened database successfully");


            // get table names
            statement = c.createStatement();
            String sql = "SELECT tablename\n" +
                    "FROM pg_catalog.pg_tables\n" +
                    "WHERE schemaname != 'pg_catalog' AND \n" +
                    "    schemaname != 'information_schema';";
            ResultSet resultSet = statement.executeQuery(sql);

            List<String> tableNames = new ArrayList<>();


            while (resultSet.next()) {
                String tableName = resultSet.getString("tablename");
                tableNames.add(tableName);
            }

            List<Table> tables = new ArrayList<>(tableNames.size());
            for (int i = 0; i < tableNames.size(); ++i) {

                // get column names and data types for each table

                List<String> columnNames = new ArrayList<>();
                List<String> dataTypes = new ArrayList<>();
                sql = "select column_name,data_type\n" +
                        "from information_schema.columns\n" +
                        "WHERE table_name = '" + tableNames.get(i) + "'";

                resultSet = statement.executeQuery(sql);
                while (resultSet.next()) {
                    columnNames.add(resultSet.getString("column_name"));
                    dataTypes.add(resultSet.getString("data_type"));
                }


                // get constraints for each table

                List<String> constraintsTypes = new ArrayList<>();

                for (int j = 0; j < columnNames.size(); ++j) {
                    sql = "select constraint_name, constraint_type\n" +
                            "from information_schema.table_constraints\n" +
                            "where table_name='" + tableNames.get(i) + "' and constraint_name like'%" + columnNames.get(j) + "%'";

                    resultSet = statement.executeQuery(sql);
                    while (resultSet.next()) {
                        constraintsTypes.add(resultSet.getString("constraint_type"));
                    }
                }

                for (int j = 0; j < constraintsTypes.size(); ++j) {
                    if (Objects.equals(constraintsTypes.get(j), "CHECK")) {
                        constraintsTypes.set(j, "");
                    }
                }

                List<String> references = new ArrayList<>();

                Table table = new Table(tableNames.get(i), columnNames, dataTypes, constraintsTypes, references);
                tables.add(table);
            }

            // get references table
            sql = "SELECT conrelid::regclass::text  AS tbl\n" +
                    "     , confrelid::regclass::text AS referenced_tbl\n" +
                    "FROM   pg_constraint\n" +
                    "WHERE  contype = 'f'\n" +
                    "ORDER  BY conname;";

            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                String tableName = resultSet.getString("tbl");
                String reference = resultSet.getString("referenced_tbl");
                tableName = tableName.substring(tableName.indexOf(".") + 1);
                reference = reference.substring(reference.indexOf(".") + 1);
                if (reference.startsWith("\"")) {
                    reference = reference.substring(1, reference.length() - 1);
                }
                System.out.println(tableName);
                int index = tableNames.indexOf(tableName);
                Table table = tables.get(index);
                List<String> references = table.getReferences();
                references.add(reference);

            }

            resultSet.close();
            statement.close();
            c.close();


            Gson gson = new Gson();
            String json = gson.toJson(tables);

            return new ResponseEntity<>(json, HttpStatus.OK);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @PostMapping("/generate")
    @ResponseBody
    public ResponseEntity<?> generateData(@RequestBody String json) {
        try {
            // parse json with rules for generation data
            Gson gson = new Gson();
            TypeToken<GenerationRule> collectionType = new TypeToken<>() {};
            GenerationRule data = gson.fromJson(json, collectionType);

            // connection to database
            Connection c = null;
            Statement statement = null;

            c = DriverManager.getConnection("jdbc:postgresql://194.58.108.56:5432/prostoXsber",
                    "admin", "admin");

            System.out.println("Opened database successfully");

            statement = c.createStatement();

            System.out.println("TableName = " + data.getTableName() + " , entryCount = " + data.getEntryCount());

            for (int i = 0; i < data.getEntryCount(); ++i) {
                StringBuilder sql = new StringBuilder("INSERT INTO sber.\"" + data.getTableName() + "\" \n" +
                        "VALUES (");
                // directory table
                if (data.getReferences().isEmpty()) {
                    for(String rule : data.getColumns()){
                        System.out.println(rule);
                        if (Objects.equals(rule, "PK")) {
                            sql.append("DEFAULT,");
                        } else if (Objects.equals(rule, "0")) {
                            String regex = String.format("[1-9]{10}");
                            sql.append(faker.regexify(regex)).append(",");
                        } else if (Objects.equals(rule, "")) {
                            String regex = String.format("[a-zA-Z]{10}");
                            sql.append("'").append(faker.regexify(regex)).append("',");
                        } else {
                            String value = faker.regexify(rule);
                            System.out.println(value);
                            sql.append("'").append(value).append("',");
                        }
                    }
                    sql = new StringBuilder(sql.substring(0, sql.length() - 1));
                    sql.append(");");


                    System.out.println(sql);

                    statement.executeUpdate(sql.toString());

                }
                else { // main table


                    // get metadata for reference tables
                    String refTable = data.getReferences().get(0);
                    String columnName = "";
                    for (int j = 0; j < data.getColumns().size(); ++j) {
                        if (Objects.equals(data.getColumns().get(j), "FK")) {
                            sql = new StringBuilder("SELECT conrelid::regclass::text  AS tbl\n" +
                                    "     , conname                   AS fk_constraint\n" +
                                    "     , confrelid::regclass::text AS referenced_tbl\n" +
                                    "FROM   pg_constraint\n" +
                                    "WHERE  contype = 'f'\n" +
                                    "AND    conrelid = 'sber." + refTable + "'::regclass  -- table name\n" +
                                    "ORDER  BY conname;");

                            ResultSet resultSet = statement.executeQuery(sql.toString());
                            while (resultSet.next()) {
                                String tableName = resultSet.getString("tbl");
                                String foreignKey = resultSet.getString("fk_constraint");
                                String reference = resultSet.getString("referenced_tbl");
                                tableName = tableName.substring(tableName.indexOf(".") + 1);
                                reference = reference.substring(reference.indexOf(".") + 1);
                                if (reference.startsWith("\"")) {
                                    reference = reference.substring(1, reference.length() - 1);
                                }
                                System.out.println(tableName + " " + foreignKey + " " + reference);
                                columnName = foreignKey;
                            }

                        }
                    }


                    // insert data to database
                    for(String rule : data.getColumns()){
                        System.out.println(rule);
                        if (Objects.equals(rule, "PK")) {
                            sql.append("DEFAULT,");
                        } else if (Objects.equals(rule, "0")) {
                            String regex = String.format("[1-9]{10}");
                            sql.append(faker.regexify(regex)).append(",");
                        } else if (Objects.equals(rule, "")) {
                            String regex = String.format("[a-zA-Z]{10}");
                            sql.append("'").append(faker.regexify(regex)).append("',");
                        } else {
                            String value = faker.regexify(rule);
                            System.out.println(value);
                            sql.append("'").append(value).append("',");
                        }
                    }
                    sql = new StringBuilder(sql.substring(0, sql.length() - 1));
                    sql.append(");");


                    System.out.println(sql);

                    statement.executeUpdate(sql.toString());

                }
            }


            statement.close();
            c.close();

        } catch (Exception ex) {
            System.out.println(ex);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/download")
    public ResponseEntity<?> getDistribution() {


        return new ResponseEntity<>(HttpStatus.OK);
    }


}