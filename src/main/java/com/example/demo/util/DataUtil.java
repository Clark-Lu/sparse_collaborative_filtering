package com.example.demo.util;

import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/*
 *created by LuChang
 *2018/11/20 18:47
 */
public class DataUtil {

    public static double calculateDotMultiply(double[] x, double[] y) {
        double result = 0;
        for (int i = 0; i < x.length; i++) {
            result = result + x[i] * y[i];
        }
        return result;
    }


    public static double[][] getRandomArray(int rowNum, int colNum) {
        double[][] result = new double[rowNum][colNum];
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[i].length; j++) {
                result[i][j] = Math.random();
            }
        }
        return result;
    }

    public static double[] getRandomArray(int length) {
        double[] result = new double[length];
        for (int i = 0; i < result.length; i++) {
            result[i] = Math.random();
        }
        return result;
    }


    public static double calculateFormula(String formula, double[] x, double[] a) {
        String elFormula = formula.replaceAll("x", "#x").replaceAll("a", "#a");
        ExpressionParser expressionParser = new SpelExpressionParser();
        Expression expression = expressionParser.parseExpression(elFormula);
        EvaluationContext context = new StandardEvaluationContext();
        for (int i = 0; i < x.length; i++) {
            context.setVariable("x" + i, x[i]);
        }
        for (int i = 0; i < a.length; i++) {
            context.setVariable("a" + i, a[i]);
        }
        double value = expression.getValue(context, Double.class);
        return value;
    }


}
