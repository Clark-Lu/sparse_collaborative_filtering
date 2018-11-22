package com.example.demo.model;

import java.util.List;

/*
 *created by LuChang
 *2018/11/22 16:07
 */
public class RawDataMatrix {

    private List<Long> rowIdList;

    private List<Long> colIdList;

    private double[][] matrix;

    public RawDataMatrix(){

    }

    public RawDataMatrix(List<Long> rowIdList, List<Long> colIdList, double[][] matrix) {
        this.rowIdList = rowIdList;
        this.colIdList = colIdList;
        this.matrix = matrix;
    }

    public List<Long> getRowIdList() {
        return rowIdList;
    }

    public List<Long> getColIdList() {
        return colIdList;
    }

    public double[][] getMatrix() {
        return matrix;
    }
}
