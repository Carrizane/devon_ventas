package com.carrizane.ventasapp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Product {

    @Expose
    @SerializedName("_id")
    private String _id;

    @Expose
    @SerializedName("name")
    private String name;

    @Expose
    @SerializedName("trademark")
    private String trademark;

    @Expose
    @SerializedName("stock")
    private int stock;

    @Expose
    @SerializedName("price")
    private Double price;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTrademark() {
        return trademark;
    }

    public void setTrademark(String trademark) {
        this.trademark = trademark;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
