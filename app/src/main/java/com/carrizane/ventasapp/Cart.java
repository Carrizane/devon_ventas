package com.carrizane.ventasapp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class Cart {

    @Expose
    @SerializedName("_id")
    private String _id;

    @Expose
    @SerializedName("client")
    private String client;

    @Expose
    @SerializedName("products")
    private List<Product> products;

    @Expose
    @SerializedName("date")
    private String date;

    @Expose
    @SerializedName("total")
    private String total;

    public Cart(String client, List<Product> products, String total) {
        this.client = client;
        this.products = products;
        this.total = total;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

}
