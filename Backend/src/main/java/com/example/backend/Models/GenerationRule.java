package com.example.backend.Models;

import lombok.Data;

import java.util.List;

@Data
public class GenerationRule {
    private String tableName;
    private Integer entryCount;
    private List<String> columns;

    private List<String> references;

    public GenerationRule() {
    }

    public GenerationRule(String tableName, Integer entryCount, List<String> columns, List<String> references) {
        this.tableName = tableName;
        this.entryCount = entryCount;
        this.columns = columns;
        this.references = references;
    }
}
