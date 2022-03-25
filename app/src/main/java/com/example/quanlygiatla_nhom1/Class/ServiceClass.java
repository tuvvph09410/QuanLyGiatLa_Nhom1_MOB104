package com.example.quanlygiatla_nhom1.Class;

public class ServiceClass {
    private String id;
    private String service_type;
    private String product_type;
    private String unit;
    private int price;
    private String status;

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProduct_type() {
        return product_type;
    }

    public void setProduct_type(String product_type) {
        this.product_type = product_type;
    }

    public String getService_type() {
        return service_type;
    }

    public void setService_type(String service_type) {
        this.service_type = service_type;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ServiceClass(String id, String service_type, String product_type, String unit, int price, String status) {
        this.id = id;
        this.product_type = product_type;
        this.service_type = service_type;
        this.unit = unit;
        this.price = price;
        this.status = status;
    }

    public ServiceClass() {
    }
}
