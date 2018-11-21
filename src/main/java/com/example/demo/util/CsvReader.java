package com.example.demo.util;

import com.example.demo.model.SparseMatrix;
import com.example.demo.model.SparseMatrixElement;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/*
 *created by LuChang
 *2018/11/20 18:24
 */
public class CsvReader {

    public static Map<Long, List<Long>> readData() {
        Map<Long, List<Long>> data = new HashMap<>();
        try {
            File file = ResourceUtils.getFile("classpath:test.csv");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            reader.readLine();
            String line = null;
            while ((line = reader.readLine()) != null) {
                String[] temp = line.split(",");
                Long userId = Long.valueOf(temp[0]);
                Long fanId = Long.valueOf(temp[1]);
                if (data.containsKey(userId)) {
                    data.get(userId).add(fanId);
                } else {
                    List<Long> fanList = new ArrayList<>();
                    fanList.add(fanId);
                    data.put(userId, fanList);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }


    public static SparseMatrix formatData(Map<Long, List<Long>> data) {
        Set<Long> keys = data.keySet();
        List<Long> keyList = keys.stream().collect(Collectors.toList());
        List<Long> temp = new ArrayList<>();
        keyList.forEach(id -> temp.addAll(data.get(id)));
        List<Long> userList = temp.stream().distinct().sorted().collect(Collectors.toList());
        List<String> attributeList = new ArrayList<>();
        userList.forEach(id -> attributeList.add(String.valueOf(id)));
        List<List<SparseMatrixElement>> matrix = new ArrayList<>();
        for (int i = 0; i < keyList.size(); i++) {
            List<SparseMatrixElement> list = new ArrayList<>();
            for (int j = 0; j < userList.size(); j++) {
                if (data.get(keyList.get(i)).contains(userList.get(j))) {
                    list.add(new SparseMatrixElement(i, j, 1));
                } else {
                    //只有user1对商品2没有评价，但根据喜欢商品1的大都喜欢商品2这个事实，最终计算结果user1对商品2的评分会比较高
                    if (i != 1 && j != 0)
                        list.add(new SparseMatrixElement(i, j, 0));
                }
            }
            matrix.add(list);
        }
        return new SparseMatrix(attributeList, matrix);
    }

    public static SparseMatrix getFormatData() {
        return formatData(readData());
    }


}
