package com.example.demo.model;

import java.util.List;

/*
 *created by LuChang
 *2018/11/21 09:40
 */
public class SparseMatrix {


    private List<String> attributeNameList;

    private final List<SparseMatrixElement> matrix;

    private final int rowNum;

    private final int colNum;

    public SparseMatrix(List<String> attributeNameList, List<SparseMatrixElement> matrix) {
        this.attributeNameList = attributeNameList;
        this.matrix = matrix;
        this.rowNum = matrix.size();
        this.colNum = attributeNameList.size();
    }

    public SparseMatrix(List<SparseMatrixElement> matrix, int length, int size) {
        this.matrix = matrix;
        this.rowNum = length;
        this.colNum = size;
    }

    public List<String> getAttributeNameList() {
        return attributeNameList;
    }

    public List<SparseMatrixElement> getMatrix() {
        return matrix;
    }

    public int getRowNum() {
        return rowNum;
    }

    public int getColNum() {
        return colNum;
    }
}
