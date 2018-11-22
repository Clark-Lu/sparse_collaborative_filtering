package com.example.demo.solver;

import com.example.demo.model.RawDataMatrix;
import com.example.demo.util.CsvReader;
import com.example.demo.util.DataUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 *created by LuChang
 *2018/11/22 16:22
 */
public class KMeansClusterSolver {

    public static void main(String[] args) {
        RawDataMatrix rawDataMatrix = CsvReader.readRawData("cluster.csv");
        Map<Integer, List<Long>> clusters = cluster(rawDataMatrix, 2);
        System.out.println(clusters);
    }

    public static Map<Integer, List<Long>> cluster(RawDataMatrix rawDataMatrix,int clusterNum){
        Map<Integer,List<Long>> result = new HashMap<>();
        for (int i = 0; i < clusterNum; i++) {
            result.put(i,new ArrayList<>());
        }
        double[][] centroids = DataUtil.getRandomArray(clusterNum,rawDataMatrix.getColIdList().size());
        Map<Integer,List<Integer>> clusters = cluster(rawDataMatrix.getMatrix(),centroids);
        double error = calculateErrorFunction(rawDataMatrix.getMatrix(),clusters,centroids);
        while (error > 1){
            refreshCentroids(rawDataMatrix.getMatrix(),clusters,centroids);
            clusters = cluster(rawDataMatrix.getMatrix(),centroids);
            error = calculateErrorFunction(rawDataMatrix.getMatrix(),clusters,centroids);
            System.out.println(error);
        }
        for (int i = 0; i < clusterNum; i++) {
            List<Integer> list = clusters.get(i);
            for (Integer index : list){
                result.get(i).add(rawDataMatrix.getRowIdList().get(index));
            }
        }
        return result;
    }

    public static double calculateErrorFunction(double[][] matrix,Map<Integer,List<Integer>> clusters,double[][] centroids){
        double error = 0;
        for (Integer key : clusters.keySet()){
            List<Integer> rowList = clusters.get(key);
            for (Integer i : rowList){
                error = error + calcuilateDistance(matrix[i],centroids[key]);
            }
        }
        return error;
    }

    public static double calcuilateDistance(double[] row,double[] centroid){
        double result = 0;
        for (int j = 0; j < centroid.length; j++) {
            result = result + Math.abs(row[j] - centroid[j]);
        }
        return result;
    }

    public static Map<Integer,List<Integer>> cluster(double[][] matrix,double[][] centroids){
        Map<Integer,List<Integer>> result = new HashMap<>();
        for (int i = 0; i < centroids.length; i++) {
            result.put(i,new ArrayList<>());
        }
        for (int i = 0; i < matrix.length; i++) {
            double minError = Integer.MAX_VALUE;
            int centroid = 0;
            for (int j = 0; j < centroids.length; j++) {
                double distance = calcuilateDistance(matrix[i],centroids[j]);
                if (minError >distance){
                    minError = distance;
                    centroid = j;
                }
            }
            result.get(centroid).add(i);
        }
        return result;
    }

    public static void refreshCentroids(double[][] matrix, Map<Integer,List<Integer>> result,double[][] centroids){
        for (int i = 0; i < centroids.length; i++) {
            List<Integer> list = result.get(i);
            double[] temp = new double[centroids[i].length];
            for (Integer index : list){
                for (int j = 0; j < matrix[index].length; j++) {
                    temp[j] = temp[j] + matrix[index][j]/list.size();
                }
            }
            centroids[i] = temp;
        }
    }

}
