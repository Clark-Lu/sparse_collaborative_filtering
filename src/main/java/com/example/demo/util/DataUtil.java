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

    public static int[][] formatData(Map<Long, List<Long>> data) {
        Set<Long> keys = data.keySet();
        List<Long> keyList = keys.stream().collect(Collectors.toList());
        List<Long> temp = new ArrayList<>();
        keyList.forEach(id -> temp.addAll(data.get(id)));
        List<Long> userList = temp.stream().distinct().collect(Collectors.toList());
        int[][] result = new int[keyList.size()][userList.size()];
        for (int i = 0; i < keyList.size(); i++) {
            for (int j = 0; j < userList.size(); j++) {
                if (data.get(keyList.get(i)).contains(userList.get(j))) {
                    result[i][j] = 1;
                } else {
                    result[i][j] = 0;
                }
            }
        }
        return result;
    }


    public static double[][] refreshParams(int[][] y, double[][] t, double[][] x){
        double[][] temp = Arrays.copyOf(t,t.length);
        for (int i = 0; i < t.length; i++) {
            for (int j = 0; j < t[i].length; j++) {
                temp[i][j] = temp[i][j] - calculateDerivative(y,t,x,i,j);
            }
        }
        return temp;
    }

    public static double calculateDerivative(int[][] y, double[][] t, double[][] x, int ti, int tj) {
        double result = 0;
        double error = calculateErrorFunction(y,t,x);
        double[][] temp = Arrays.copyOf(t,t.length);
        double deltaT = t[ti][tj]/100;
        temp[ti][tj] = t[ti][tj] - deltaT;
        double deltaY = calculateErrorFunction(y,temp,x) - error;
        try {
            result = deltaY/deltaT;
        } catch (Exception e) {
        }
        return result;
    }

    public static double calculateErrorFunction(int[][] y,double[][] t,double[][] x){
        double result = 0;
        for (int i = 0; i < y.length; i++) {
            int[] yi = y[i];
            for (int j = 0; j < yi.length; j++) {
                result = result + Math.pow(yi[j] - calculateMultiple(t[i],x[j]) ,2);
            }
        }
        return result;
    }

    private static double calculateMultiple(double[] x,double[] y){
        double result = 1;
        for (int i = 0; i < x.length; i++) {
            result = result + x[i]*y[i];
        }
        return result;
    }



    public static Instances getInstances(int[][] y){
        ArrayList<Attribute> attributes = new ArrayList<Attribute>(y[0].length);
        for (int i = 0; i < y[0].length; i++) {
            Attribute attribute = new Attribute(String.valueOf(i));
            attributes.add(attribute);
        }
        Instances instances = new Instances("vector", attributes, 0);
        for (int i = 0; i < y.length; i++) {
            Instance inst = new DenseInstance(y[0].length);
            for (int j = 0; j < y[i].length; j++) {
                inst.setValue(j,y[i][j]);
            }
            instances.add(inst);
        }
        return instances;

    }

    public static double[][] getRandomArray(int length,int size){
        double[][] result = new double[length][size];
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[i].length; j++) {
                result[i][j] = Math.random();
            }
        }
        return result;
    }



}
