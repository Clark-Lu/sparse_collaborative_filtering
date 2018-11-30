package com.example.demo.service;

import com.example.demo.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/*
 *created by LuChang
 *2018/11/30 15:56
 */
public class FeatureUpdateServiceTest extends BaseTest {

    @Autowired
    private FeatureUpdateService featureUpdateService;

    @Test
    public void testUpdateFeature(){
        featureUpdateService.updateFeature(10);
    }

}
