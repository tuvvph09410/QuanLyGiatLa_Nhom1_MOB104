package com.example.quanlygiatla_nhom1.Class;

public class ProfitClass {
    String date;
    Integer price;
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public ProfitClass() {
    }

    public ProfitClass(String date, Integer price) {
        this.date = date;
        this.price = price;
    }
}
