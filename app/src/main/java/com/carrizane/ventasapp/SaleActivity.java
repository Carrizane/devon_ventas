package com.carrizane.ventasapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SaleActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    CartAdapter adapter;
    CartAdapter.ItemClickListener itemClickListener;
    List<Product> productList;

    ArrayList<String> products_id = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale);

        recyclerView = findViewById(R.id.cartRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Intent intent = getIntent();
        products_id = intent.getStringArrayListExtra("arrayId");

        getProductsById(products_id);
    }

    public void getProductsById(ArrayList<String> id){
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        for (int i = 0; i < id.size(); i++) {
            Call<List<Product>> call = apiInterface.getProductsById(id.get(i));
            call.enqueue(new Callback<List<Product>>() {
                @Override
                public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                    if(response.isSuccessful() && response.body() != null){
                        onGetResults(response.body());
                    }
                }
                @Override
                public void onFailure(Call<List<Product>> call, Throwable t) {
                    Log.i("Error", t.getLocalizedMessage());
                    onErrorLoading(t.getLocalizedMessage());
                }
            });
        }
    }

    public void onGetResults(List<Product> products){
        adapter = new CartAdapter(this, products, itemClickListener);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);

        productList = products;
    }

    public void onErrorLoading(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}