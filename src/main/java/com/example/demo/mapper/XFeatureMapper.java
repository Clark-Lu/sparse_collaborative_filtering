package com.example.demo.mapper;

import com.example.demo.model.Feature;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/*
 *created by LuChang
 *2018/11/30 14:16
 */
@Repository
public interface XFeatureMapper {

    Feature selectByPrimaryKey(@Param("id") Long id);

    void batchInsertSelective(@Param("featureList") List<Feature> featureList);

    void updateByPrimaryKey(Feature feature);

    List<Feature> selectAll();

}
