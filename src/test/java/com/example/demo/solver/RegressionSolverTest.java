package com.example.demo.solver;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

/**
 * created by LuChang
 * 2019/3/6 12:58
 */
public class RegressionSolverTest {



    @Test
    public void testTrain() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        double[][] xx = new double[100][];
        double[] y = new double[100];
        for (int i = 0; i < 100; i++) {
            double[] x = {1,i,i*i+1};
            xx[i] = x;
            y[i] = 10.0 + i + 20*(i*i+1);
        }

        RegressionSolver solver = new RegressionSolver(xx,y,"a0+a1*x1+a2*x2");
        solver.train(1000,0.00001);
        System.out.println(objectMapper.writeValueAsString(xx));
        System.out.println(objectMapper.writeValueAsString(y));
        System.out.println(objectMapper.writeValueAsString(solver.getA()));
    }

}
