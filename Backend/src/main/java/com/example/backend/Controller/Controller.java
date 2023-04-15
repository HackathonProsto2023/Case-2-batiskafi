package com.example.backend.Controller;

import com.example.backend.Models.Table;
import com.example.backend.Serialization.TableSerializer;
import com.google.gson.Gson;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(value = "/api")
public class Controller {

    @GetMapping("/dbase")
    @ResponseBody
    public ResponseEntity<?> getDatabase() {
        String tableName1 = "Position";
        String[] columns1 = {"PositionID", "PositionName"};
        String[] dataTypes1 = {"integer", "string"};
        String[] constraintTypes1 = {"PK", ""};
        String[] references1 = {};
        Table table1 = new Table(tableName1, columns1, dataTypes1, constraintTypes1, references1);


        String tableName2 = "Project";
        String[] columns2 = {"ProjectID", "ProjectName", "Description"};
        String[] dataTypes2 = {"integer", "string", "string"};
        String[] constraintTypes2 = {"PK", "", ""};
        String[] references2 = {};
        Table table2 = new Table(tableName2, columns2, dataTypes2, constraintTypes2, references2);

        String tableName3 = "Employee";
        String[] columns3 = {"EmployeeID", "FirstName", "LastName", "PositionID", "ProjectID"};
        String[] dataTypes3 = {"integer", "string", "string", "integer", "integer"};
        String[] constraintTypes3 = {"PK", "", "", "FK", "FK"};
        String[] references3 = {"Position", "Project"};
        Table table3 = new Table(tableName3, columns3, dataTypes3, constraintTypes3, references3);

        Table[] tables = {table1, table2, table3};

        Gson gson = new Gson();
        String json = gson.toJson(tables);
        return new ResponseEntity<>(json ,HttpStatus.OK);
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
