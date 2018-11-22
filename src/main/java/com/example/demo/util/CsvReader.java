package com.example.demo.util;

import com.example.demo.model.*;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/*
 *created by LuChang
 *2018/11/20 18:24
 */
public class CsvReader {

    public static List<CsvData> readData(String fileName) {
        List<CsvData> data = new ArrayList<>();
        try {
            File file = ResourceUtils.getFile("classpath:" + fileName);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            reader.readLine();
            String line = null;
            while ((line = reader.readLine()) != null) {
                String[] temp = line.split(",");
                int rowId = Integer.valueOf(temp[0]);
                int colId = Integer.valueOf(temp[1]);
                String score = temp[2];
                data.add(new CsvData(rowId, colId, score));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }


    public static SparseMatrix formatData(List<CsvData> data) {
        List<SparseMatrixElement> matrix = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            CsvData csvData = data.get(i);
            if (!csvData.getScore().equals("?")) {
                matrix.add(new SparseMatrixElement(csvData.getRowId(), csvData.getColId(), Double.valueOf(csvData.getScore())));
            }
        }
        int rows = (int) data.stream().map(d -> d.getRowId()).distinct().count();
        int cols = (int) data.stream().map(d -> d.getColId()).distinct().count();
        return new SparseMatrix(matrix, rows, cols);
    }

    public static SparseMatrix getFormatData(String fileName) {
        return formatData(readData(fileName));
    }

    public static RawDataMatrix readRawData(String fileName) {
        try {
            File file = ResourceUtils.getFile("classpath:" + fileName);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            reader.readLine();
            String line = null;
            List<Long> idList = new ArrayList<>();
            List<Long> userIdList = new ArrayList<>();
            Map<Long,List<Long>> relationMap = new HashMap<>();
            while ((line = reader.readLine()) != null) {
                String[] temp = line.split(",");
                Long id = Long.valueOf(temp[0]);
                Long userId = Long.valueOf(temp[1]);
                idList.add(id);
                userIdList.add(userId);
                if (relationMap.containsKey(id)){
                    relationMap.get(id).add(userId);
                }else {
                    List<Long> tempList = new ArrayList<>();
                    tempList.add(userId);
                    relationMap.put(id,tempList);
                }
            }
            List<Long> distinctIdList = idList.stream().distinct().collect(Collectors.toList());
            List<Long> distinctUserIdList = userIdList.stream().distinct().collect(Collectors.toList());
            double[][] matrix = new double[distinctIdList.size()][distinctUserIdList.size()];
            for (int i = 0; i < distinctIdList.size(); i++) {
                for (int j = 0; j < distinctUserIdList.size(); j++) {
                    if (relationMap.get(distinctIdList.get(i)).contains(distinctUserIdList.get(j))){
                        matrix[i][j] = 1;
                    }else {
                        matrix[i][j] = 0;
                    }
                }
            }
            return new RawDataMatrix(distinctIdList,distinctUserIdList,matrix);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
