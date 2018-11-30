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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 *created by LuChang
 *2018/11/21 09:49
 */
public class SparseCollaborativeFilterSolver {

    private static final Logger logger = LoggerFactory.getLogger(SparseCollaborativeFilterSolver.class);


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
        int times = 0;
        logger.info("初始误差为{}", error);
        List<SparseMatrixElement> sparseMatrixElementList = sparseMatrix.getMatrix();
        Map<Integer, List<SparseMatrixElement>> rowIndexMap = new HashMap<>(sparseMatrix.getRowNum());
        Map<Integer, List<SparseMatrixElement>> colIndexMap = new HashMap<>(sparseMatrix.getColNum());
        for (int i = 0; i < sparseMatrix.getRowNum(); i++) {
            rowIndexMap.put(i, new ArrayList<>(200));
        }
        for (int i = 0; i < sparseMatrix.getColNum(); i++) {
            colIndexMap.put(i, new ArrayList<>(20));
        }
        for (int i = 0; i < sparseMatrixElementList.size(); i++) {
            SparseMatrixElement sparseMatrixElement = sparseMatrixElementList.get(i);
            rowIndexMap.get(sparseMatrixElement.getRow()).add(sparseMatrixElement);
            colIndexMap.get(sparseMatrixElement.getCol()).add(sparseMatrixElement);
        }
        double minErrorRate = 0.00001;
        double minError = 0.00001;
        while (errorRate > minErrorRate && times < 1000 && error > minError) {
            refreshParams(rowIndexMap, colIndexMap, theta, x);
            double newError = calculateErrorFunction(sparseMatrix.getMatrix(), theta, x);
            errorRate = Math.abs((newError - error) / error);
            error = newError;
            times++;
            logger.info("第{}次迭代，误差率变化为{}，误差为{}", times, errorRate, error);
        }
        return new SparseCollaborativeFilterResult(new Matrix(null, theta), new Matrix(null, x));
    }

    //非标准梯度下降法，后续参数的计算利用本次迭代中的值计算，根据数据量及数据数值大小调整学习率
    private static void refreshParams(Map<Integer, List<SparseMatrixElement>> rowIndexMap,
                                      Map<Integer, List<SparseMatrixElement>> colIndexMap,
                                      double[][] theta, double[][] x) {
        for (int i = 0; i < theta.length; i++) {
            for (int j = 0; j < theta[i].length; j++) {
                double derivative = calculateDerivativeTheta(colIndexMap, theta, x, i, j);
                refreshParam(theta, i, j, derivative);
            }
        }
        for (int i = 0; i < x.length; i++) {
            for (int j = 0; j < x[i].length; j++) {
                double derivative = calculateDerivativeX(rowIndexMap, theta, x, i, j);
                refreshParam(x, i, j, derivative);
            }
        }
    }

    //学习率调参直至收敛
    private static void refreshParam(double[][] param, int i, int j, double derivative) {
        int k = 0;
        double temp = 10;
        while (temp >= 1 || temp <= 0){
            temp = param[i][j] - derivative/Math.pow(10,k);
            k++;
        }
        param[i][j] = param[i][j] - 2*derivative/Math.pow(10,k);
    }


    private static double calculateErrorFunction(List<SparseMatrixElement> sparseList, double[][] theta, double[][] x) {
        double result = 0;
        for (SparseMatrixElement element : sparseList) {
            double temp = element.getValue() - DataUtil.calculateDotMultiply(theta[element.getCol()], x[element.getRow()]);
            result = result + temp * temp;
        }
        return result;
    }

    private static double calculateDerivativeTheta(Map<Integer, List<SparseMatrixElement>> colIndexMap, double[][] theta, double[][] x, int i, int j) {
        double deltaTheta = 0.0000001;
        double deltaError = 0;
        for (SparseMatrixElement element : colIndexMap.get(i)) {
            double temp = element.getValue() - DataUtil.calculateDotMultiply(theta[element.getCol()], x[element.getRow()]);
            double alterTemp = temp + theta[element.getCol()][j] * x[element.getRow()][j]
                    - (theta[element.getCol()][j] - deltaTheta) * x[element.getRow()][j];
            deltaError = deltaError + temp * temp - alterTemp * alterTemp;
        }
        return deltaError / deltaTheta;
    }

    private static double calculateDerivativeX(Map<Integer, List<SparseMatrixElement>> rowIndexMap, double[][] theta, double[][] x, int i, int j) {
        double deltaX = 0.0000001;
        double deltaError = 0;
        for (SparseMatrixElement element : rowIndexMap.get(i)) {
            double temp = element.getValue() - DataUtil.calculateDotMultiply(theta[element.getCol()], x[element.getRow()]);
            double alterTemp = temp + theta[element.getCol()][j] * x[element.getRow()][j]
                    - (x[element.getRow()][j] - deltaX) * theta[element.getCol()][j];
            deltaError = deltaError + temp * temp - alterTemp * alterTemp;
        }
        return deltaError / deltaX;
    }


}