package com.carrizane.ventasapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    CartAdapter adapter;
    CartAdapter.ItemClickListener itemClickListener;
    List<Product> productList;

    ArrayList<Product> productsBundle;

    TextView total;
    Button order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        recyclerView = findViewById(R.id.cartRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        total = findViewById(R.id.totalPrice);
        order = findViewById(R.id.confirmBtn);

        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("array");
        productsBundle = (ArrayList<Product>) args.getSerializable("arrayProduct");
        Log.i("id_from_bundle", String.valueOf(productsBundle.get(0).getPrice()));

        total.setText(setTotal(productsBundle));

        if(args != null){
            setData(productsBundle);
        }

        adapter.setOnItemClickListener(new CartAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {}
            @Override
            public void onPlusClick(int position) {
                int n = productList.get(position).getQuantity();
                if (n > productList.get(position).getStock()){
                    Toast.makeText(CartActivity.this, "There's not enough stock", Toast.LENGTH_SHORT).show();
                }else{
                    n += 1;
                    plusNumber(position, n);
                    addTotal(productList.get(position).getPrice());
                }
            }
            @Override
            public void onMinusClick(int position) {
                int n = productList.get(position).getQuantity();
                if(n == 1){
                    Toast.makeText(CartActivity.this, "Min Value: 1", Toast.LENGTH_SHORT).show();
                }else{
                    n -= 1;
                    minusNumber(position, n);
                    subtractTotal(productList.get(position).getPrice());
                }
            }
        });

        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String t = total.getText().toString();
                Cart c = new Cart("Maria Juana", productList, t);
                addCart(c);
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
    }

    public void addCart(Cart cart){
        ApiInterface apiInterface = ApiClient.getApiCartClient().create(ApiInterface.class);
        Call<Cart> call = apiInterface.addCart(cart);
        call.enqueue(new Callback<Cart>() {
            @Override
            public void onResponse(Call<Cart> call, Response<Cart> response) {}
            @Override
            public void onFailure(Call<Cart> call, Throwable t) {
                onErrorLoading(t.getLocalizedMessage());
            }
        });
    }

    public void onErrorLoading(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public String setTotal(ArrayList<Product> products){
        Double n = 0.0;
        DecimalFormat numberFormat = new DecimalFormat("#.00");
        for (int i = 0; i < products.size(); i++) {
            n += products.get(i).getPrice();
        }
        return numberFormat.format(n);
    }

    public void setData(List<Product> products){
        adapter = new CartAdapter(this, products, itemClickListener);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);

        productList = products;
    }

    public void plusNumber(int position, int number){
        productList.get(position).setQuantity(number);
        adapter.notifyItemChanged(position);
    }

    public void minusNumber(int position, int number){
        productList.get(position).setQuantity(number);
        adapter.notifyItemChanged(position);
    }

    public void addTotal(Double number){
        Double t = Double.parseDouble(total.getText().toString());
        DecimalFormat numberFormat = new DecimalFormat("#.00");
        t += number;
        total.setText(String.valueOf(numberFormat.format(t)));
    }

    public void subtractTotal(Double number){
        Double t = Double.parseDouble(total.getText().toString());
        DecimalFormat numberFormat = new DecimalFormat("#.00");
        t -= number;
        total.setText(String.valueOf(numberFormat.format(t)));
    }
}