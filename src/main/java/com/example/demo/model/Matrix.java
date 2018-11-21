package com.example.demo.model;

/*
 *created by LuChang
 *2018/11/21 09:53
 */
public class Matrix {

    private final String[] attributeNames;

    private final double[][] matrix;

    public Matrix(String[] attributeNames, double[][] matrix) {
        this.attributeNames = attributeNames;
        this.matrix = matrix;
    }

    public String[] getAttributeNames() {
        return attributeNames;
    }

    public double[][] getMatrix() {
        return matrix;
    }

    public Matrix multiplyTransfer(Matrix other){
        double[][] a = this.matrix;
        double[][] b = other.getMatrix();
        double[][] result = new double[a.length][b.length];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < b.length; j++) {
                result[i][j] = calculateDotMultiple(a[i],b[j]);
            }
        }
        return new Matrix(null,result);
    }

    private static double calculateDotMultiple(double[] x, double[] y) {
        double result = 0;
        for (int i = 0; i < x.length; i++) {
            result = result + x[i] * y[i];
        }
        return result;
    }
}
