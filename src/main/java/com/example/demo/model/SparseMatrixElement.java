package com.example.demo.model;

/*
 *created by LuChang
 *2018/11/21 09:32
 */
public class SparseMatrixElement {

    private final int row;

    private final int col;

    private final double value;

    public SparseMatrixElement(int row,int col,double value){
        this.row = row;
        this.col = col;
        this.value = value;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public double getValue() {
        return value;
    }
}
