package com.example.demo.service;

import com.example.demo.mapper.ThetaFeatureMapper;
import com.example.demo.mapper.XFeatureMapper;
import com.example.demo.mapper.XThetaScoreMapper;
import com.example.demo.model.Feature;
import com.example.demo.model.SparseMatrix;
import com.example.demo.model.SparseMatrixElement;
import com.example.demo.model.XThetaScore;
import com.example.demo.result.SparseCollaborativeFilterResult;
import com.example.demo.solver.SparseCollaborativeFilterSolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FeatureUpdateService {

    private static final Logger logger = LoggerFactory.getLogger(FeatureUpdateService.class);
    @Autowired
    private XThetaScoreMapper xThetaScoreMapper;
    @Autowired
    private XFeatureMapper xFeatureMapper;
    @Autowired
    private ThetaFeatureMapper thetaFeatureMapper;

    public void updateFeature(int featureNum) {
        logger.info("更新x特征，theta特征向量开始");
        List<XThetaScore> xThetaScoreList = xThetaScoreMapper.selectAll();
        List<Long> thetaIdList = xThetaScoreList
                .stream().map(xThetaScore -> xThetaScore.getThetaId()).distinct().sorted().collect(Collectors.toList());
        Map<Long, Integer> thetaIndexMap = new HashMap<>(thetaIdList.size());
        for (int i = 0; i < thetaIdList.size(); i++) {
            thetaIndexMap.put(thetaIdList.get(i), i);
        }
        List<SparseMatrixElement> sparseMatrixElementList = new ArrayList<>(xThetaScoreList.size());
        List<Long> xIdList = xThetaScoreList.stream()
                .map(xThetaScore -> xThetaScore.getxId()).distinct().sorted().collect(Collectors.toList());
        Map<Long, Integer> xIndexMap = new HashMap<>(xIdList.size());
        for (int i = 0; i < xIdList.size(); i++) {
            xIndexMap.put(xIdList.get(i), i);
        }
        logger.info("读取数据完成，初始化系数矩阵开始");
        double maxAmount = 1;
        for (int i = 0; i < xThetaScoreList.size(); i++) {
            XThetaScore xThetaScore = xThetaScoreList.get(i);
            if (thetaIndexMap.get(xThetaScore.getThetaId()) != null
                    && xIndexMap.get(xThetaScore.getxId()) != null) {
                SparseMatrixElement temp = new SparseMatrixElement(xIndexMap.get(xThetaScore.getxId()),
                        thetaIndexMap.get(xThetaScore.getThetaId()),
                        xThetaScore.getScore());
                sparseMatrixElementList.add(temp);
                if (temp.getValue() > maxAmount) {
                    maxAmount = temp.getValue();
                }
            }
        }
        //归一化
        for (SparseMatrixElement element : sparseMatrixElementList) {
            element.setValue(element.getValue() / maxAmount);
        }
        xThetaScoreList = null;
        thetaIndexMap = null;
        xIndexMap = null;
        SparseMatrix sparseMatrix = new SparseMatrix(sparseMatrixElementList, xIdList.size(), thetaIdList.size());
        logger.info("初始化稀疏矩阵完成，开始训练模型");
        SparseCollaborativeFilterResult result = SparseCollaborativeFilterSolver.solve(sparseMatrix, featureNum);
        sparseMatrix = null;
        sparseMatrixElementList = null;
        //回归原始值
        this.insertOrUpdateXFeature(xIdList, result.getX().getMatrix(), maxAmount);
        logger.info("更新x特征向量完成");
        this.insertOrUpdateThetaFeature(thetaIdList, result.getTheta().getMatrix());
        logger.info("更新theta特征向量完成");
    }

    public void insertOrUpdateXFeature(List<Long> xIdList, double[][] x, double maxAmount) {
        List<Feature> insertList = new ArrayList<>(100);
        for (int i = 0; i < xIdList.size(); i++) {
            Feature feature = this.getInitValueFeature(xIdList.get(i), x[i],maxAmount);
            Feature old = xFeatureMapper.selectByPrimaryKey(feature.getId());
            if (old == null) {
                insertList.add(feature);
                if (insertList.size() == 100) {
                    xFeatureMapper.batchInsertSelective(insertList);
                    insertList.clear();
                }
            } else {
                xFeatureMapper.updateByPrimaryKey(feature);
            }
        }
        if (insertList.size() > 0) {
            xFeatureMapper.batchInsertSelective(insertList);
        }
    }

    public void insertOrUpdateThetaFeature(List<Long> thetaIdList, double[][] theta) {
        List<Feature> insertList = new ArrayList<>(1000);
        for (int i = 0; i < thetaIdList.size(); i++) {
            Feature feature = this.getInitValueFeature(thetaIdList.get(i), theta[i]);
            Feature old = thetaFeatureMapper.selectByPrimaryKey(feature.getId());
            if (old == null) {
                insertList.add(feature);
                if (insertList.size() == 1000) {
                    thetaFeatureMapper.batchInsertSelective(insertList);
                    insertList.clear();
                }
            } else {
                thetaFeatureMapper.updateByPrimaryKey(feature);
            }
        }
        if (insertList.size() > 0) {
            thetaFeatureMapper.batchInsertSelective(insertList);
        }
    }


    private Feature getInitValueFeature(Long id, double[] features) {
        Feature feature = new Feature();
        feature.setId(id);
        feature.setFeature0(features.length > 0 ? features[0] : 0);
        feature.setFeature1(features.length > 1 ? features[1] : 0);
        feature.setFeature2(features.length > 2 ? features[2] : 0);
        feature.setFeature3(features.length > 3 ? features[3] : 0);
        feature.setFeature4(features.length > 4 ? features[4] : 0);
        feature.setFeature5(features.length > 5 ? features[5] : 0);
        feature.setFeature6(features.length > 6 ? features[6] : 0);
        feature.setFeature7(features.length > 7 ? features[7] : 0);
        feature.setFeature8(features.length > 8 ? features[8] : 0);
        feature.setFeature9(features.length > 9 ? features[9] : 0);
        feature.setFeature10(features.length > 10 ? features[10] : 0);
        feature.setFeature11(features.length > 11 ? features[11] : 0);
        feature.setFeature12(features.length > 12 ? features[12] : 0);
        feature.setFeature13(features.length > 13 ? features[13] : 0);
        feature.setFeature14(features.length > 14 ? features[14] : 0);
        feature.setFeature15(features.length > 15 ? features[15] : 0);
        feature.setFeature16(features.length > 16 ? features[16] : 0);
        feature.setFeature17(features.length > 17 ? features[17] : 0);
        feature.setFeature18(features.length > 18 ? features[18] : 0);
        feature.setFeature19(features.length > 19 ? features[19] : 0);
        return feature;
    }

    private Feature getInitValueFeature(Long id, double[] features,double maxAmount) {
        Feature feature = new Feature();
        feature.setId(id);
        feature.setFeature0(features.length > 0 ? features[0]*maxAmount : 0);
        feature.setFeature1(features.length > 1 ? features[1]*maxAmount : 0);
        feature.setFeature2(features.length > 2 ? features[2]*maxAmount : 0);
        feature.setFeature3(features.length > 3 ? features[3]*maxAmount : 0);
        feature.setFeature4(features.length > 4 ? features[4]*maxAmount : 0);
        feature.setFeature5(features.length > 5 ? features[5]*maxAmount : 0);
        feature.setFeature6(features.length > 6 ? features[6]*maxAmount : 0);
        feature.setFeature7(features.length > 7 ? features[7]*maxAmount : 0);
        feature.setFeature8(features.length > 8 ? features[8]*maxAmount : 0);
        feature.setFeature9(features.length > 9 ? features[9]*maxAmount : 0);
        feature.setFeature10(features.length > 10 ? features[10]*maxAmount : 0);
        feature.setFeature11(features.length > 11 ? features[11]*maxAmount : 0);
        feature.setFeature12(features.length > 12 ? features[12]*maxAmount : 0);
        feature.setFeature13(features.length > 13 ? features[13]*maxAmount : 0);
        feature.setFeature14(features.length > 14 ? features[14]*maxAmount : 0);
        feature.setFeature15(features.length > 15 ? features[15]*maxAmount : 0);
        feature.setFeature16(features.length > 16 ? features[16]*maxAmount : 0);
        feature.setFeature17(features.length > 17 ? features[17]*maxAmount : 0);
        feature.setFeature18(features.length > 18 ? features[18]*maxAmount : 0);
        feature.setFeature19(features.length > 19 ? features[19]*maxAmount : 0);
        return feature;
    }


}
