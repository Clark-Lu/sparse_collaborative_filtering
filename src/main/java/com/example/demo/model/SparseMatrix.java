package com.example.demo.model;

import java.util.List;

/*
 *created by LuChang
 *2018/11/21 09:40
 */
public class SparseMatrix {


    private List<String> attributeNameList;

    private final List<List<SparseMatrixElement>> matrix;

    private final int length;

    private final int size;

    public SparseMatrix(List<String> attributeNameList, List<List<SparseMatrixElement>> matrix) {
        this.attributeNameList = attributeNameList;
        this.matrix = matrix;
        this.length = matrix.size();
        this.size = attributeNameList.size();
    }

    public SparseMatrix(List<List<SparseMatrixElement>> matrix, int length, int size) {
        this.matrix = matrix;
        this.length = length;
        this.size = size;
    }

    public List<String> getAttributeNameList() {
        return attributeNameList;
    }

    public List<List<SparseMatrixElement>> getMatrix() {
        return matrix;
    }

    public int getLength() {
        return length;
    }

    public int getSize() {
        return size;
    }
}
