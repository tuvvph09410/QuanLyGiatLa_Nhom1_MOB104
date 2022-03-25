package com.example.quanlygiatla_nhom1.Class;

public class BillDetailClass {
    private String BillID;
    private String ServiceID;
    private String WarehouseID;
    private int Quantity;
    private int Total;
    public BillDetailClass(String billID, String serviceID, String warehouseID, int quantity, int total) {
        BillID = billID;
        ServiceID = serviceID;
        WarehouseID = warehouseID;
        Quantity = quantity;
        Total = total;
    }
    public BillDetailClass() {
    }

    public String getBillID() {
        return BillID;
    }

    public void setBillID(String billID) {
        BillID = billID;
    }

    public String getServiceID() {
        return ServiceID;
    }

    public void setServiceID(String serviceID) {
        ServiceID = serviceID;
    }

    public String getWarehouseID() {
        return WarehouseID;
    }

    public void setWarehouseID(String warehouseID) {
        WarehouseID = warehouseID;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public int getTotal() {
        return Total;
    }

    public void setTotal(int total) {
        Total = total;
    }
}
