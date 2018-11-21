package com.example.demo.util;

import org.junit.Test;

import java.util.List;
import java.util.Map;

/*
 *created by LuChang
 *2018/11/20 19:58
 */
public class KMeansClusterTest {

    @Test
    public void test() throws Exception {
        Map<Integer, List<Long>> integerListMap = KMeansCluster.clusterStringList(8);

        for (Integer integer : integerListMap.keySet()){
            System.out.println(integer + ":");
            for (int i = 0; i < integerListMap.get(integer).size(); i++) {
                System.out.println(integerListMap.get(integer).get(i));
            }
            System.out.println("________________________________");
        }
    }
}
