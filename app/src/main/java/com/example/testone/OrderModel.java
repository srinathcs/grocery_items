package com.example.testone;

import java.util.ArrayList;

public class OrderModel {
    private int id;
    private ArrayList<GroceryItem> items;
    private String address;
    private String email;
    private String phoneNumber;
    private String zipCode;
    private double totalPrice;
    private String paymentMethod;
    private boolean success;


    public OrderModel(ArrayList<GroceryItem> items, String address, String email, String phoneNumber, String zipCode, double totalPrice, String paymentMethod, boolean success) {
        this.id = Utils.getOrderId();
        this.items = items;
        this.address = address;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.zipCode = zipCode;
        this.totalPrice = totalPrice;
        this.paymentMethod = paymentMethod;
        this.success = success;
    }

    public OrderModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<GroceryItem> getItems() {
        return items;
    }

    public void setItems(ArrayList<GroceryItem> items) {
        this.items = items;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "OrderModel{" +
                "id=" + id +
                ", items=" + items +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", totalPrice=" + totalPrice +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", success=" + success +
                '}';
    }
}
