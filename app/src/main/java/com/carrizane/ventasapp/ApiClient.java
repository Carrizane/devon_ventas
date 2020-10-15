package com.carrizane.ventasapp;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static final String ENDPOINT_PRODUCTS = "https://create-ventas-service.herokuapp.com/";
    private static final String ENDPOINT_CART = "https://cart-service.herokuapp.com/cart/";
    private static final String ENDPOINT_SALE= "http://158.101.116.176:2222/devon-market/api/";
    private static Retrofit retrofitProduct;
    private static Retrofit retrofitCart;
    private static Retrofit retrofitSale;

    public static Retrofit getApiProductClient(){
        if (retrofitProduct == null){
            retrofitProduct = new Retrofit.Builder().baseUrl(ENDPOINT_PRODUCTS).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofitProduct;
    }

    public static Retrofit getApiCartClient(){
        if (retrofitCart == null){
            retrofitCart = new Retrofit.Builder().baseUrl(ENDPOINT_CART).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofitCart;
    }

    public static Retrofit getApiSaleClient(){
        if (retrofitSale == null){
            retrofitSale = new Retrofit.Builder().baseUrl(ENDPOINT_SALE).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofitSale;
    }

}
