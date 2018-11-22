package com.example.demo.util;

import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

import java.util.*;
import java.util.stream.Collectors;

/*
 *created by LuChang
 *2018/11/20 18:47
 */
public class DataUtil {

    public static double calculateDotMultiply(double[] x, double[] y){
        double result = 0;
        for (int i = 0; i < x.length; i++) {
            result = result + x[i]*y[i];
        }
        return result;
    }


    public static double[][] getRandomArray(int rowNum,int colNum){
        double[][] result = new double[rowNum][colNum];
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[i].length; j++) {
                result[i][j] = Math.random();
            }
        }
        return result;
    }



}
