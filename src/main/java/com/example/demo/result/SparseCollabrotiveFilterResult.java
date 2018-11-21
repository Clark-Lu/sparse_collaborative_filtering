package com.example.demo.result;

import com.example.demo.model.Matrix;

/*
 *created by LuChang
 *2018/11/21 09:59
 */
public class SparseCollabrotiveFilterResult {


    private Matrix theta;


    private Matrix x;

    public SparseCollabrotiveFilterResult(Matrix theta, Matrix x) {
        this.theta = theta;
        this.x = x;
    }

    public Matrix getTheta() {
        return theta;
    }

    public Matrix getX() {
        return x;
    }

}
