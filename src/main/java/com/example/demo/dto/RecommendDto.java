package com.example.demo.dto;

/*
 *created by LuChang
 *2018/11/30 14:11
 */
public class RecommendDto implements Comparable<RecommendDto>{

    private Long id;

    private double score;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    //逆序排列
    @Override
    public int compareTo(RecommendDto o) {
        if (this.score - o.score > 0){
            return -1;
        }else {
            return 1;
        }
    }
}
