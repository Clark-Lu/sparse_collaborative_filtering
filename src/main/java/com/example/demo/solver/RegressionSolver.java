package com.example.demo.solver;

import com.example.demo.util.DataUtil;

/**
 * created by LuChang
 * 2019/3/6 10:40
 */
public class RegressionSolver {

    private double[][] xx;

    private double[] y;

    private String formula;

    private double[] a;

    private double trainError;

    public RegressionSolver(double[][] xx, double[] y, String formula) {
        this.xx = xx;
        this.y = y;
        this.formula = formula;
        a = new double[xx[0].length];
    }

    /**
     * 误差函数  error = SUM((y - ax)2)
     * 迭代方程  a = a - lr*derivative(error)
     */
    public void train(int times,double minError){
        a = DataUtil.getRandomArray(a.length);
        trainError = this.calculateError();
        System.out.println("init error is " + trainError);
        for (int i = 0; i < times; i++) {
            refreshParam(minError);
            trainError = this.calculateError();
        }
        System.out.println("final error is " + trainError);
    }

    private void refreshParam(double minError){
        for (int i = 0; i < a.length; i++) {
            refreshParam(i,minError);
        }
    }

    private void refreshParam(int i,double minError){
        double learnRate = 2;
        double error = trainError + 1;
        double temp = a[i];
        //自适应学习率
        while (error >= trainError && error > minError) {
            a[i] = temp;
            a[i] = a[i] - learnRate * calculateDerivative(i);
            error = calculateError();
            learnRate = learnRate/2;
        }
    }


    private double calculateDerivative(int j){
        double derivative = 0;
        for (int i = 0; i < y.length; i++) {
            derivative = derivative -2 * xx[i][j] * (y[i] - DataUtil.calculateFormula(formula,xx[i],a));
        }
        return derivative;
    }

    private double calculateError(){
        double error = 0.0;
        for (int i = 0; i < xx.length; i++) {
            error = error + Math.pow((y[i] - DataUtil.calculateFormula(formula,xx[i],a)),2);
        }
        return error;
    }

    public double predict(double[] x){
        return DataUtil.calculateFormula(formula,x,a);
    }

    public double[] getA() {
        return a;
    }

    public double getTrainError() {
        return trainError;
    }
}
