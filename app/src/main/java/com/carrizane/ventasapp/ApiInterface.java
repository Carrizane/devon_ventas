package com.carrizane.ventasapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiInterface {

    @GET("product/all")
    Call<List<Product>> getAllProducts();

    @GET("product/get/{id}")
    Call<List<Product>> getProductsById(@Path("id") String id);

}
