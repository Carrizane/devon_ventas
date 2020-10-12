package com.carrizane.ventasapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.MainHolderView> {

    private Context context;
    private List<Product> products;
    private ItemClickListener itemClickListener;

    public ProductsAdapter(Context context, List<Product> products, ItemClickListener itemClickListener) {
        this.context = context;
        this.products = products;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public MainHolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_card, parent, false);
        return new MainHolderView(view, itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MainHolderView holder, int position) {
        Product product = products.get(position);
        Log.i("Product_ID", product.get_id());
        holder.name.setText(product.getName());
        holder.trademark.setText(product.getTrademark());
        holder.price.setText(String.valueOf(product.getPrice()));
        holder.stock.setText(String.valueOf(product.getStock()));
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    class MainHolderView extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name, trademark, price, stock;
        CardView card_item;
        ItemClickListener itemClickListener;

        public MainHolderView(@NonNull View itemView, ItemClickListener itemClickListener) {
            super(itemView);
            this.itemClickListener = itemClickListener;

            name = itemView.findViewById(R.id.productName);
            trademark = itemView.findViewById(R.id.productMark);
            price = itemView.findViewById(R.id.productPrice);
            stock = itemView.findViewById(R.id.productStock);

            card_item = itemView.findViewById(R.id.card_item);
            this.itemClickListener = itemClickListener;
            card_item.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onItemClick(v, getAdapterPosition());
            removeAt(getAdapterPosition());
        }
    }

    public interface ItemClickListener{
        void onItemClick(View view, int position);
    }

    public void removeAt(int position) {
        products.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, products.size());
    }

}
