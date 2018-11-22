package com.example.demo.model;

/*
 *created by LuChang
 *2018/11/22 10:32
 */
public class CsvData {

    private int rowId;

    private int colId;

    private String score;

    public CsvData(int rowId, int colId, String score) {
        this.rowId = rowId;
        this.colId = colId;
        this.score = score;
    }

    public int getRowId() {
        return rowId;
    }

    public void setRowId(int rowId) {
        this.rowId = rowId;
    }

    public int getColId() {
        return colId;
    }

    public void setColId(int colId) {
        this.colId = colId;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
