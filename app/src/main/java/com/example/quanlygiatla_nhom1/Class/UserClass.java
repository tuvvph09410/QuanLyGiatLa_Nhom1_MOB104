package com.example.quanlygiatla_nhom1.Class;

public class UserClass {
    private String name;
    private String phone;
    private String userName;
    private String password;
    private String role;
    private String delete;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDelete() {
        return delete;
    }

    public void setDelete(String delete) {
        this.delete = delete;
    }

    public UserClass() {
    }

    public UserClass(String name, String phone, String userName, String password, String role,String delete) {
        this.name = name;
        this.phone = phone;
        this.userName = userName;
        this.password = password;
        this.role = role;
        this.delete = delete;
    }
}
