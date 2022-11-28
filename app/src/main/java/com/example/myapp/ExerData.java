package com.example.myapp;

public class ExerData {
    private String exer;
    private String cal;
    private String unit;
    public ExerData(String exer, String cal, String unit){
        this.exer=exer;
        this.cal = cal;
        this.unit= unit;
    }

    public String getExer(){
        return exer;
    }

    public void setExer(String exer) {
        this.exer = exer;
    }
    public String getCal(){
        return cal+"kcal";
    }
    public void setCal(String cal){
        this.cal=cal ;
    }
    public String getUnit(){
        return unit+"분";
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
