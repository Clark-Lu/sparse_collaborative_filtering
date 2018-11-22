package com.example.demo.solver;

import com.example.demo.model.Matrix;
import com.example.demo.model.SparseMatrix;
import com.example.demo.model.SparseMatrixElement;
import com.example.demo.result.SparseCollabrotiveFilterResult;
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
public class SparseCollabrotiveFilterSolver {


    public static void main(String[] args) throws JsonProcessingException {
        SparseCollabrotiveFilterResult solve = solve(CsvReader.getFormatData(), 2);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        System.out.println(objectMapper.writeValueAsString(solve.getX().multiplyTransfer(solve.getTheta()).getMatrix()));
    }


    public static SparseCollabrotiveFilterResult solve(SparseMatrix sparseMatrix, int thetaNum) {
        double[][] theta = DataUtil.getRandomArray(sparseMatrix.getSize(), thetaNum);
        double[][] x = DataUtil.getRandomArray(sparseMatrix.getLength(), thetaNum);
        double error = calculateErrorFunction(sparseMatrix.getMatrix(), theta, x);
        double errorRate = 1;
        while (errorRate > 0.00001) {
            refreshParams(sparseMatrix.getMatrix(), theta, x);
            double newError = calculateErrorFunction(sparseMatrix.getMatrix(), theta, x);
            errorRate = Math.abs((newError - error) / error);
            System.out.println(error);
            error = newError;
        }
        return new SparseCollabrotiveFilterResult(new Matrix(null, theta), new Matrix(null, x));
    }

    //非标准梯度下降法，后续参数的计算利用本次迭代中的值计算
    private static void refreshParams(List<SparseMatrixElement> sparseList, double[][] theta, double[][] x) {
        for (int i = 0; i < theta.length; i++) {
            for (int j = 0; j < theta[i].length; j++) {
                theta[i][j] = theta[i][j] - 0.0001 * calculateDerivative(sparseList, theta, x, i, j);
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
            double temp = element.getValue() - calculateDotMultiple(theta[element.getJ()], x[element.getI()]);
            result = result + temp * temp;
        }
        return result;
    }

    private static double calculateDerivative(List<SparseMatrixElement> sparseList, double[][] theta, double[][] x, int i, int j) {
        double deltaTheta = 0.0001;
        double deltaError = 0;
        double error = 0;
        double alterError = 0;

        for (SparseMatrixElement element : sparseList) {
            double temp = element.getValue() - calculateDotMultiple(theta[element.getJ()], x[element.getI()]);
            error = error + temp * temp;
            if (element.getJ() == i) {
                double alterTemp = temp + theta[element.getJ()][j] * x[element.getI()][j]
                        - (theta[element.getJ()][j] - deltaTheta) * x[element.getI()][j];
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
            double temp = element.getValue() - calculateDotMultiple(theta[element.getJ()], x[element.getI()]);
            error = error + temp * temp;
            if (element.getI() == i) {
                double alterTemp = temp + theta[element.getJ()][j] * x[element.getI()][j]
                        - (x[element.getI()][j] - deltaX) * theta[element.getJ()][j];
                alterError = alterError + alterTemp * alterTemp;
            } else {
                alterError = alterError + temp * temp;
            }
        }
        deltaError = error - alterError;
        return deltaError / deltaX;
    }

    private static double calculateDotMultiple(double[] x, double[] y) {
        double result = 0;
        for (int i = 0; i < x.length; i++) {
            result = result + x[i] * y[i];
        }
        return result;
    }

}
