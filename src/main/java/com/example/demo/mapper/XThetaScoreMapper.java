package com.example.demo.mapper;

import com.example.demo.model.XThetaScore;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/*
 *created by LuChang
 *2018/11/30 14:19
 */
@Repository
public interface XThetaScoreMapper {

    List<XThetaScore> selectAll();

    XThetaScore selectByXIdAndThetaId(@Param("xId") Long xId,@Param("thetaId") Long thetaId);

    void updateByXIdAndThetaId( XThetaScore xThetaScore);

    void batchInsert(@Param("xThetaScoreList") List<XThetaScore> xThetaScoreList);

}
