package com.example.myapp;

public class FoodData {
    private String food;
    private String cal;
    private String unit;
    public FoodData(String food, String cal, String unit){
        this.food=food;
        this.cal = cal;
        this.unit= unit;
    }

    public String getFood(){
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }
    public String getCal(){
        return cal+"kcal";
    }
    public void setCal(String cal){
        this.cal=cal ;
    }
    public String getUnit(){
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
