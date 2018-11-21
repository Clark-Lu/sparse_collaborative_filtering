package com.example.demo.util;


import weka.clusterers.SimpleKMeans;
import weka.core.Instances;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by changlu on 9/21/18.
 */
public class KMeansCluster {

    public static Map<Integer,List<Long>> clusterStringList(int clusterNum) throws Exception {
        Map<Long, List<Long>> data = CsvReader.readData();
        List<Long> keyList = data.keySet().stream().collect(Collectors.toList());
        int[][] ints = DataUtil.formatData(data);
        SimpleKMeans simpleKMeans = new SimpleKMeans();
        simpleKMeans.setNumClusters(clusterNum);
        Instances instances = DataUtil.getInstances(ints);
        simpleKMeans.buildClusterer(instances);
        Map<Integer,List<Long>> result = new HashMap<>();
        for (int i = 0; i < clusterNum; i++) {
            result.put(i, new ArrayList<>());
        }
        for (int i = 0; i < instances.size(); i++) {
            result.get(simpleKMeans.clusterInstance(instances.get(i))).add(keyList.get(i));
        }
        return result;
    }

}