package com.example.demo.util;

import org.junit.Test;

/**
 * created by LuChang
 * 2019/3/6 10:54
 */
public class DataUtilTest {

    @Test
    public void testCalculateFormula(){
        String formula = "a0*x0+a1*x1+a2*x2*x2";
        double[] x = {1.0,1.0,1.0};
        double[] a = {1.0,10.0,1.0};
        double v = DataUtil.calculateFormula(formula, x, a);
        System.out.println(v);
    }

}
