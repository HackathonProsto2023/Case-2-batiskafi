package com.example.backend.Models;


import lombok.Data;

@Data
public class Table {

    private String tableName;
    private String[] columnName;
    private String[] dataType;
    private String[] constraintType;
    private String[] references;

    public Table() {
    }

    public Table(String tableName, String[] columnName, String[] dataType, String[] constraintType, String[] references) {
        this.tableName = tableName;
        this.columnName = columnName;
        this.dataType = dataType;
        this.constraintType = constraintType;
        this.references = references;
    }
}
