package com.example.demo.service;

import com.example.demo.dto.RecommendDto;
import com.example.demo.mapper.ThetaFeatureMapper;
import com.example.demo.mapper.XFeatureMapper;
import com.example.demo.model.Feature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/*
 *created by LuChang
 *2018/11/26 17:00
 */
@Service
public class RecommendService {

    @Autowired
    private ThetaFeatureMapper thetaFeatureMapper;

    @Autowired
    private XFeatureMapper xFeatureMapper;

    public List<Long> recommendList(Long thetaId) {
        List<Feature> xFeatureList = xFeatureMapper.selectAll();
        Feature thetaFeature = thetaFeatureMapper.selectByPrimaryKey(thetaId);
        if (thetaFeature == null){
            return new ArrayList<>();
        }
        List<RecommendDto> recommendDtoList = new ArrayList<>();
        for (int i = 0; i < xFeatureList.size(); i++) {
            RecommendDto recommendDto = new RecommendDto();
            recommendDto.setId(xFeatureList.get(i).getId());
            recommendDto.setScore(thetaFeature.calculateScore(xFeatureList.get(i)));
            recommendDtoList.add(recommendDto);
        }
        List<Long> recommendIdList = recommendDtoList.stream().sorted().map(dto -> dto.getId()).collect(Collectors.toList());
        return recommendIdList;
    }

}
