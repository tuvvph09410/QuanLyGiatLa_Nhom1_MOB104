package com.example.quanlygiatla_nhom1.Activity.Class;

public class BillClass {
    private String id;
    private String customer_name;
    private String customer_phone;
    private String date;
    private String user_name;
    private String status;
    private String delete;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getCustomer_name() {
        return customer_name;
    }
    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }
    public String getCustomer_phone() {
        return customer_phone;
    }
    public void setCustomer_phone(String customer_phone) {
        this.customer_phone = customer_phone;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getUser_name() {
        return user_name;
    }
    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getDelete() {
        return delete;
    }
    public void setDelete(String delete) {
        this.delete = delete;
    }
    public BillClass() {
    }

    public BillClass(String id, String customer_name, String customer_phone, String date, String user_name, String status, String delete) {
        this.id = id;
        this.customer_name = customer_name;
        this.customer_phone = customer_phone;
        this.date = date;
        this.user_name = user_name;
        this.status = status;
        this.delete = delete;
    }
}
