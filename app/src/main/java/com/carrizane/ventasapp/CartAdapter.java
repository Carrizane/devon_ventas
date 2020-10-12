package com.carrizane.ventasapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private Context context;
    private List<Product> products;
    private ItemClickListener itemClickListener;

    public CartAdapter(Context context, List<Product> products, ItemClickListener itemClickListener) {
        this.context = context;
        this.products = products;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_card, parent, false);
        return new CartAdapter.CartViewHolder(view, itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Product product = products.get(position);
        holder.name.setText(product.getName());
        holder.price.setText(String.valueOf(product.getPrice()));
        holder.number.setText(String.valueOf(1));
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView name, price, number;
        CardView card_item;
        ItemClickListener itemClickListener;
        public CartViewHolder(@NonNull View itemView, ItemClickListener itemClickListener) {
            super(itemView);
            this.itemClickListener = itemClickListener;

            name = itemView.findViewById(R.id.cartName);
            price = itemView.findViewById(R.id.cartPrice);
            number = itemView.findViewById(R.id.cartNumber);
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onItemClick(v, getAdapterPosition());
        }
    }

    public interface ItemClickListener{
        void onItemClick(View view, int position);
    }

}
