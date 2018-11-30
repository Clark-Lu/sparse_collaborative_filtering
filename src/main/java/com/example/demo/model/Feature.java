package com.example.demo.model;

/*
 *created by LuChang
 *2018/11/30 14:03
 */
public class Feature {

    private Long id;

    private Double feature0;

    private Double feature1;

    private Double feature2;

    private Double feature3;

    private Double feature4;

    private Double feature5;

    private Double feature6;

    private Double feature7;

    private Double feature8;

    private Double feature9;

    private Double feature10;

    private Double feature11;

    private Double feature12;

    private Double feature13;

    private Double feature14;

    private Double feature15;

    private Double feature16;

    private Double feature17;

    private Double feature18;

    private Double feature19;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getFeature0() {
        return feature0;
    }

    public void setFeature0(Double feature0) {
        this.feature0 = feature0;
    }

    public Double getFeature1() {
        return feature1;
    }

    public void setFeature1(Double feature1) {
        this.feature1 = feature1;
    }

    public Double getFeature2() {
        return feature2;
    }

    public void setFeature2(Double feature2) {
        this.feature2 = feature2;
    }

    public Double getFeature3() {
        return feature3;
    }

    public void setFeature3(Double feature3) {
        this.feature3 = feature3;
    }

    public Double getFeature4() {
        return feature4;
    }

    public void setFeature4(Double feature4) {
        this.feature4 = feature4;
    }

    public Double getFeature5() {
        return feature5;
    }

    public void setFeature5(Double feature5) {
        this.feature5 = feature5;
    }

    public Double getFeature6() {
        return feature6;
    }

    public void setFeature6(Double feature6) {
        this.feature6 = feature6;
    }

    public Double getFeature7() {
        return feature7;
    }

    public void setFeature7(Double feature7) {
        this.feature7 = feature7;
    }

    public Double getFeature8() {
        return feature8;
    }

    public void setFeature8(Double feature8) {
        this.feature8 = feature8;
    }

    public Double getFeature9() {
        return feature9;
    }

    public void setFeature9(Double feature9) {
        this.feature9 = feature9;
    }

    public Double getFeature10() {
        return feature10;
    }

    public void setFeature10(Double feature10) {
        this.feature10 = feature10;
    }

    public Double getFeature11() {
        return feature11;
    }

    public void setFeature11(Double feature11) {
        this.feature11 = feature11;
    }

    public Double getFeature12() {
        return feature12;
    }

    public void setFeature12(Double feature12) {
        this.feature12 = feature12;
    }

    public Double getFeature13() {
        return feature13;
    }

    public void setFeature13(Double feature13) {
        this.feature13 = feature13;
    }

    public Double getFeature14() {
        return feature14;
    }

    public void setFeature14(Double feature14) {
        this.feature14 = feature14;
    }

    public Double getFeature15() {
        return feature15;
    }

    public void setFeature15(Double feature15) {
        this.feature15 = feature15;
    }

    public Double getFeature16() {
        return feature16;
    }

    public void setFeature16(Double feature16) {
        this.feature16 = feature16;
    }

    public Double getFeature17() {
        return feature17;
    }

    public void setFeature17(Double feature17) {
        this.feature17 = feature17;
    }

    public Double getFeature18() {
        return feature18;
    }

    public void setFeature18(Double feature18) {
        this.feature18 = feature18;
    }

    public Double getFeature19() {
        return feature19;
    }

    public void setFeature19(Double feature19) {
        this.feature19 = feature19;
    }

    public double calculateScore(Feature feature){
        double result = 0;
        result = result + this.feature0 * feature.feature0;
        result = result + this.feature1 * feature.feature1;
        result = result + this.feature2 * feature.feature2;
        result = result + this.feature3 * feature.feature3;
        result = result + this.feature4 * feature.feature4;
        result = result + this.feature5 * feature.feature5;
        result = result + this.feature6 * feature.feature6;
        result = result + this.feature7 * feature.feature7;
        result = result + this.feature8 * feature.feature8;
        result = result + this.feature9 * feature.feature9;
        result = result + this.feature10 * feature.feature10;
        result = result + this.feature11 * feature.feature11;
        result = result + this.feature12 * feature.feature12;
        result = result + this.feature13 * feature.feature13;
        result = result + this.feature14 * feature.feature14;
        result = result + this.feature15 * feature.feature15;
        result = result + this.feature16 * feature.feature16;
        result = result + this.feature17 * feature.feature17;
        result = result + this.feature18 * feature.feature18;
        result = result + this.feature19 * feature.feature19;
        return result;
    }
}
