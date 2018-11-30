package com.example.demo;

import com.example.demo.mapper.XThetaScoreMapper;
import com.example.demo.model.XThetaScore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BaseTest {

    @Autowired
    private XThetaScoreMapper xThetaScoreMapper;

    @Test
    public void contextLoads() {
    }

    //theta偏好id个位数相同的x
    @Test
    public void initXThetaScore(){
        for (long i = 1; i <= 10000; i++) {
            int j = 0;
            List<XThetaScore> list = new ArrayList<>(20);
            while (j<100){
                long xId = (long) (Math.random() * 1000);
                if (xId % 10 == i % 10){
                    XThetaScore xThetaScore = new XThetaScore();
                    xThetaScore.setThetaId(i);
                    xThetaScore.setxId(xId);
                    xThetaScore.setScore(1.0);
                    j++;
                    list.add(xThetaScore);
                }else if (Math.random() > 0.9){
                    XThetaScore xThetaScore = new XThetaScore();
                    xThetaScore.setThetaId(i);
                    xThetaScore.setxId(xId);
                    xThetaScore.setScore(0.0);
                    j++;
                    list.add(xThetaScore);
                }
            }
            xThetaScoreMapper.batchInsert(list);
            System.out.println("初始化theta" + i);
        }
    }

}
