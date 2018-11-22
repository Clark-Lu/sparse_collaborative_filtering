package com.example.demo.model;

/*
 *created by LuChang
 *2018/11/21 09:32
 */
public class SparseMatrixElement {

    private final int i;

    private final int j;

    private final double value;

    public SparseMatrixElement(int i,int j,double value){
        this.i = i;
        this.j = j;
        this.value = value;
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }

    public double getValue() {
        return value;
    }
}
