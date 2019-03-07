package com.example.demo.solver;

import com.example.demo.util.DataUtil;

/**
 * created by LuChang
 * 2019/3/7 14:43
 */
public class MultiVariableLineRegressionSolver {

    private double[][] xx;

    private double[] y;

    private double[] a;

    private double trainError;

    private double minError;

    private int trainTimes = 0;

    public MultiVariableLineRegressionSolver(double[][] xx, double[] y) {
        if (xx.length != y.length) {
            throw new RuntimeException("训练数据自变量与因变量长度不相等");
        }
        this.xx = xx;
        this.y = y;
        this.a = new double[xx[0].length];
    }

    public void train(int maxTimes, double minError) {
        a = DataUtil.getRandomArray(a.length);
        this.minError = minError;
        trainError = calculateError();
        for (int i = 0; i < maxTimes; i++) {
            trainTimes = i;
            if (trainError < minError) {
                break;
            }
            refreshParam();
        }
    }


    private void refreshParam() {
        for (int i = 0; i < a.length; i++) {
            refreshParam(i);
        }
    }

    private void refreshParam(int i) {
        double learnRate = 1;
        double error = 2 * trainError;
        double temp = a[i];
        double derivative = calculateDerivative(i);
        int times = 0;
        while (error > trainError && error > minError && times < 64) {
            a[i] = temp;
            a[i] = a[i] - learnRate * derivative;
            error = calculateError();
            double rate = derivative;
            if (rate > 5){
                rate = 5;
            }else if (rate < 2){
                rate = 2;
            }
            learnRate = learnRate / rate;
            times++;
        }
        trainError = error;
    }

    /**
     * -2x(y - yp)
     *
     * @param i
     * @return
     */
    private double calculateDerivative(int i) {
        double derivative = 0.0;
        for (int j = 0; j < y.length; j++) {
            derivative = derivative - 2 * xx[j][i] * (y[j] - predict(xx[j]));
        }
        return derivative;
    }

    private double calculateError() {
        double error = 0.0;
        for (int i = 0; i < y.length; i++) {
            error = error + Math.pow(y[i] - predict(xx[i]), 2);
        }
        return error;
    }

    public double predict(double[] x) {
        if (x.length != a.length) {
            throw new RuntimeException("x的长度不为" + a.length);
        }
        double result = 0.0;
        for (int i = 0; i < x.length; i++) {
            result = result + a[i] * x[i];
        }
        return result;
    }

    public double[] getA() {
        return a;
    }

    public int getTrainTimes() {
        return trainTimes;
    }

    public double getTrainError() {
        return trainError;
    }
}
