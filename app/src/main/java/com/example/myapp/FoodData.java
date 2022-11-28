package com.example.myapp;

public class FoodData {
    private String food;
    private String cal;
    private String unit;
    private String carbo; //탄
    private String protein; //단
    private String fat; //지
    private String sugars; //당류
    private String sodium; //나트륨

    public FoodData(String food, String cal, String unit, String carbo, String protein, String fat, String sugars, String sodium){
        this.food=food;
        this.cal = cal;
        this.unit= unit;
        this.carbo=carbo;
        this.protein=protein;
        this.fat=fat;
        this.sugars=sugars;
        this.sodium=sodium;
    }

    public String getFood(){
        return food;
    }
    public void setFood(String food) {
        this.food = food;
    }
    public String getCal(){return cal+"kcal";}
    public void setCal(String cal){ this.cal=cal ;}
    public String getUnit(){return unit;}
    public void setUnit(String unit) {this.unit = unit;}

    public String getCarbo() { return "탄수화물: "+carbo+"g";}
    public void setCarbo(String carbo) { this.carbo=carbo; }
    public String getProtein() { return "단백질: "+protein+"g";}
    public void setProtein(String protein) { this.protein=protein; }
    public String getFat() { return "지방: "+fat+"g";}
    public void setFat(String fat) { this.fat=fat; }
    public String getSugars() { return "당류: "+sugars+"g";}
    public void setSugars(String sugars) { this.sugars=sugars; }
    public String getSodium() { return "나트륨: "+sodium+"mg";}
    public void setSodium(String sodium) { this.sodium=sodium; }
}
