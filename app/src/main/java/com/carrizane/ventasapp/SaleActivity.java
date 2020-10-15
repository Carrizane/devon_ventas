package com.carrizane.ventasapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SaleActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    SaleAdapter adapter;
    SaleAdapter.ItemClickListener itemClickListener;
    List<Cart> cartList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale);

        recyclerView = findViewById(R.id.saleRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        getSales();
    }

    private void getSales(){
        ApiInterface apiInterface = ApiClient.getApiSaleClient().create(ApiInterface.class);
        Call<List<Cart>> call = apiInterface.getAllSale();
        call.enqueue(new Callback<List<Cart>>() {
            @Override
            public void onResponse(Call<List<Cart>> call, Response<List<Cart>> response) {
                if(response.isSuccessful() && response.body() != null){
                    setData(response.body());
                }
            }
            @Override
            public void onFailure(Call<List<Cart>> call, Throwable t) {
                onErrorLoading(t.getLocalizedMessage());
            }
        });
    }

    private void setData(List<Cart> cart){
        adapter = new SaleAdapter(this, cart, itemClickListener);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);

        cartList = cart;
    }

    public void onErrorLoading(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}