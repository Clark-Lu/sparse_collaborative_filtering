package com.example.demo.service;

import com.example.demo.BaseTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/*
 *created by LuChang
 *2018/11/30 16:32
 */
public class RecommendServiceTest extends BaseTest {

    @Autowired
    private RecommendService recommendService;

    /**
     * 靠前的大部分都应该是各位数是1的记录
     * @throws JsonProcessingException
     */
    @Test
    public void testRecommend() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println(objectMapper.writeValueAsString(recommendService.recommendList(2L)));
    }

}
