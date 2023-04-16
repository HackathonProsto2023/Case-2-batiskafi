package com.example.backend.Controller;

import com.example.backend.Models.Table;
import com.google.gson.Gson;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@CrossOrigin
@RestController
@RequestMapping(value = "/api")
public class Controller {

    @GetMapping("/dbase")
    @ResponseBody
    public ResponseEntity<?> getDatabase() {
        Connection c = null;
        Statement statement = null;
        try {
            c = DriverManager.getConnection("jdbc:postgresql://194.58.108.56:5432/prostoXsber",
                    "admin", "admin");

            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            statement = c.createStatement();
            String sql = "SELECT tablename\n" +
                    "FROM pg_catalog.pg_tables\n" +
                    "WHERE schemaname != 'pg_catalog' AND \n" +
                    "    schemaname != 'information_schema';";
            ResultSet resultSet = statement.executeQuery(sql);

            List<String> tableNames = new ArrayList<>();

            // table names
            while (resultSet.next()) {
                String tableName = resultSet.getString("tablename");
                tableNames.add(tableName);
            }

            List<Table> tables = new ArrayList<>(tableNames.size());
            for (int i = 0; i < tableNames.size(); ++i) {
                // column names and data types for each table

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


                // constraints for each table

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

                for (String type : constraintsTypes) {
                    if (Objects.equals(type, "CHECK")) {
                        type = "";
                    }
                }

                List<String> references = new ArrayList<>();

                Table table = new Table(tableNames.get(i), columnNames, dataTypes, constraintsTypes, references);
                tables.add(table);
            }

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
    public ResponseEntity<?> generateData(@RequestBody String regex) {


        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/download")
    public ResponseEntity<?> getDistribution() {


        return new ResponseEntity<>(HttpStatus.OK);
    }


}