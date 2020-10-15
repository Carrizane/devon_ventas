package com.carrizane.ventasapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private Context context;
    private List<Product> products;
    private ItemClickListener itemClickListener;

    public CartAdapter(){}

    public CartAdapter(Context context, List<Product> products, ItemClickListener itemClickListener) {
        this.context = context;
        this.products = products;
        this.itemClickListener = itemClickListener;
    }

    public void setOnItemClickListener(ItemClickListener listener) {
        itemClickListener = listener;
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
        holder.number.setText(String.valueOf(product.getQuantity()));
        /*if(product.getQuantity() != 0){
            holder.number.setText(String.valueOf(product.getQuantity()));
        }else{
            holder.number.setText(String.valueOf(1));
        }*/
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView name, price, number;
        Button plus, minus;
        CardView card_item;
        ItemClickListener itemClickListener;
        public CartViewHolder(@NonNull View itemView, ItemClickListener itemClickListener) {
            super(itemView);
            this.itemClickListener = itemClickListener;

            plus = itemView.findViewById(R.id.plusBtn);
            minus = itemView.findViewById(R.id.minusBtn);

            name = itemView.findViewById(R.id.cartName);
            price = itemView.findViewById(R.id.cartPrice);
            number = itemView.findViewById(R.id.cartNumber);

            plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemClickListener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            itemClickListener.onPlusClick(position);
                        }
                    }
                }
            });

            minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemClickListener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            itemClickListener.onMinusClick(position);
                        }
                    }
                }
            });
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onItemClick(v, getAdapterPosition());
        }
    }

    public interface ItemClickListener{
        void onItemClick(View view, int position);
        void onPlusClick(int position);
        void onMinusClick(int position);
    }

}
