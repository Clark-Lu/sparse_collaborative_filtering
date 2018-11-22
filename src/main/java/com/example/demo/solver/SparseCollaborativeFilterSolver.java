package com.example.demo.solver;

import com.example.demo.model.Matrix;
import com.example.demo.model.SparseMatrix;
import com.example.demo.model.SparseMatrixElement;
import com.example.demo.result.SparseCollaborativeFilterResult;
import com.example.demo.util.CsvReader;
import com.example.demo.util.DataUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.util.List;

/*
 *created by LuChang
 *2018/11/21 09:49
 */
public class SparseCollaborativeFilterSolver {


    public static void main(String[] args) throws JsonProcessingException {
        SparseCollaborativeFilterResult solve = solve(CsvReader.getFormatData("test.csv"), 2);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        /**
         * 得分矩阵
         *       user1  user2  user3  user4  user5
         * movie1  1      0     1       0      1
         * movie2  ?      0     1       0      1
         * movie3  0      1     0       ?      0
         * movie4  ?      1     0       1      0
         *
         * 求解结果近似
         *       user1  user2  user3  user4  user5
         * movie1  1      0     1       0      1
         * movie2  1      0     1       0      1
         * movie3  0      1     0       1      0
         * movie4  0      1     0       1      0
         *
         */
        System.out.println(objectMapper.writeValueAsString(solve.getX().multiplyTransfer(solve.getTheta()).getMatrix()));
        System.out.println("X: " + objectMapper.writeValueAsString(solve.getX()));
        System.out.println("theta: " + objectMapper.writeValueAsString(solve.getTheta()));
    }


    public static SparseCollaborativeFilterResult solve(SparseMatrix sparseMatrix, int featureNum) {
        double[][] theta = DataUtil.getRandomArray(sparseMatrix.getColNum(), featureNum);
        double[][] x = DataUtil.getRandomArray(sparseMatrix.getRowNum(), featureNum);
        double error = calculateErrorFunction(sparseMatrix.getMatrix(), theta, x);
        double errorRate = 1;
        while (errorRate > 0.00001) {
            refreshParams(sparseMatrix.getMatrix(), theta, x);
            double newError = calculateErrorFunction(sparseMatrix.getMatrix(), theta, x);
            errorRate = Math.abs((newError - error) / error);
            System.out.println(error);
            error = newError;
        }
        return new SparseCollaborativeFilterResult(new Matrix(null, theta), new Matrix(null, x));
    }

    //非标准梯度下降法，后续参数的计算利用本次迭代中的值计算，根据数据量及数据数值大小调整学习率
    private static void refreshParams(List<SparseMatrixElement> sparseList, double[][] theta, double[][] x) {
        for (int i = 0; i < theta.length; i++) {
            for (int j = 0; j < theta[i].length; j++) {
                theta[i][j] = theta[i][j] - 0.0001 * calculateDerivativeTheta(sparseList, theta, x, i, j);
            }
        }
        for (int i = 0; i < x.length; i++) {
            for (int j = 0; j < x[i].length; j++) {
                x[i][j] = x[i][j] - 0.0001 * calculateDerivativeX(sparseList, theta, x, i, j);
            }
        }
    }


    private static double calculateErrorFunction(List<SparseMatrixElement> sparseList, double[][] theta, double[][] x) {
        double result = 0;
        for (SparseMatrixElement element : sparseList) {
            double temp = element.getValue() - DataUtil.calculateDotMultiply(theta[element.getCol()], x[element.getRow()]);
            result = result + temp * temp;
        }
        return result;
    }

    private static double calculateDerivativeTheta(List<SparseMatrixElement> sparseList, double[][] theta, double[][] x, int i, int j) {
        double deltaTheta = 0.0001;
        double deltaError = 0;
        double error = 0;
        double alterError = 0;

        for (SparseMatrixElement element : sparseList) {
            double temp = element.getValue() - DataUtil.calculateDotMultiply(theta[element.getCol()], x[element.getRow()]);
            error = error + temp * temp;
            if (element.getCol() == i) {
                double alterTemp = temp + theta[element.getCol()][j] * x[element.getRow()][j]
                        - (theta[element.getCol()][j] - deltaTheta) * x[element.getRow()][j];
                alterError = alterError + alterTemp * alterTemp;
            } else {
                alterError = alterError + temp * temp;
            }
        }
        deltaError = error - alterError;
        return deltaError / deltaTheta;
    }

    private static double calculateDerivativeX(List<SparseMatrixElement> sparseList, double[][] theta, double[][] x, int i, int j) {
        double deltaX = 0.000001;
        double deltaError = 0;
        double error = 0;
        double alterError = 0;
        for (SparseMatrixElement element : sparseList) {
            double temp = element.getValue() - DataUtil.calculateDotMultiply(theta[element.getCol()], x[element.getRow()]);
            error = error + temp * temp;
            if (element.getRow() == i) {
                double alterTemp = temp + theta[element.getCol()][j] * x[element.getRow()][j]
                        - (x[element.getRow()][j] - deltaX) * theta[element.getCol()][j];
                alterError = alterError + alterTemp * alterTemp;
            } else {
                alterError = alterError + temp * temp;
            }
        }
        deltaError = error - alterError;
        return deltaError / deltaX;
    }


}
