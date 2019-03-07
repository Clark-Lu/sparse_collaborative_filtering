package com.example.demo.solver;

import org.junit.Test;

/**
 * created by LuChang
 * 2019/3/7 15:09
 */
public class MultiVariableLineRegressionSolverTest {

    /**
     * 特征值之间相关性越低越好
     * 特征值之间有强相关性的话，训练效果很差
     */
    @Test
    public void testTrain() {

        double[][] xx = new double[100][];
        double[] y = new double[100];
        for (int i = 0; i < y.length; i++) {
            xx[i] = new double[]{1, Math.random(), Math.random()};
            y[i] = 5 * xx[i][0] - 10 * xx[i][1] + 10 * xx[i][2];
        }
        MultiVariableLineRegressionSolver solver = new MultiVariableLineRegressionSolver(xx, y);
        solver.train(100, 0.000001);
        double[] a = solver.getA();
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i] + "   ");
        }
        System.out.println();
        System.out.println("训练次数：" + solver.getTrainTimes());
        System.out.println("训练误差：" + solver.getTrainError());
    }

}
