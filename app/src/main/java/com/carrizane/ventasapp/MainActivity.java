package com.carrizane.ventasapp;

import android.content.Intent;
import android.os.Bundle;

import com.andremion.counterfab.CounterFab;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefresh;

    ProductsAdapter adapter;
    ProductsAdapter.ItemClickListener itemClickListener;

    List<Product> productsList;

    CounterFab counter;
    FloatingActionButton sales;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        swipeRefresh = findViewById(R.id.swipe_refresh);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));

        getProducts();
        swipeRefresh.setOnRefreshListener(
                () -> getProducts()
        );

        counter = findViewById(R.id.counter_fab);
        sales = findViewById(R.id.salesButton);

        sales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SaleActivity.class));
            }
        });

        Intent intent = new Intent(MainActivity.this, CartActivity.class);
        ArrayList<Product> selected = new ArrayList<>();
        Bundle args = new Bundle();

        itemClickListener = ((view, position) -> {
            productsList.get(position).setQuantity(1);
            selected.add(productsList.get(position));
            args.putSerializable("arrayProduct", (Serializable) selected);
            intent.putExtra("array", args);
            Log.i("Cantidad_ID",String.valueOf(selected.size()));
            Toast.makeText(this, "Added " + productsList.get(position).getName() + " to cart", Toast.LENGTH_SHORT).show();
            counter.increase();
        });

        counter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });
    }

    public void getProducts(){
        ApiInterface apiInterface = ApiClient.getApiProductClient().create(ApiInterface.class);
        Call<List<Product>> call = apiInterface.getAllProducts();
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if(response.isSuccessful() && response.body() != null){
                    onGetResult(response.body());
                }
            }
            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                onErrorLoading(t.getLocalizedMessage());
            }
        });
    }

    public void onGetResult(List<Product> products){
        adapter = new ProductsAdapter(this, products, itemClickListener);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);

        productsList = products;
    }

    public void onErrorLoading(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}