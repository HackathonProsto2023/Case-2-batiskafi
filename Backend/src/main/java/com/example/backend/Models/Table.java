package com.example.backend.Models;

import lombok.Data;

import java.util.List;

@Data
public class Table {

    private String tableName;
    private List<String> columnName;
    private List<String> dataType;
    private List<String> constraintType;
    private List<String> references;

    public Table() { }

    public Table(String tableName, List<String> columnName, List<String> dataType, List<String> constraintType, List<String> references) {
        this.tableName = tableName;
        this.columnName = columnName;
        this.dataType = dataType;
        this.constraintType = constraintType;
        this.references = references;
    }
}
