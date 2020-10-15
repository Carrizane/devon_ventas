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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class SaleAdapter extends RecyclerView.Adapter<SaleAdapter.SaleHolderView> {

    private Context context;
    private List<Cart> cartList;
    private ItemClickListener itemClickListener;

    public SaleAdapter(Context context, List<Cart> cartList, ItemClickListener itemClickListener) {
        this.context = context;
        this.cartList = cartList;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public SaleHolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_sale, parent, false);
        return new SaleHolderView(view, itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull SaleHolderView holder, int position) {
        Cart cart = cartList.get(position);
        holder.code.setText(cart.get_id().substring(0, 7));
        holder.total.setText(cart.getTotal());
        holder.date.setText(cart.getDate());
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    class SaleHolderView extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView code, total, date;
        CardView card_item;
        ItemClickListener itemClickListener;

        public SaleHolderView(@NonNull View itemView, ItemClickListener itemClickListener) {
            super(itemView);
            this.itemClickListener = itemClickListener;

            code = itemView.findViewById(R.id.saleCode);
            total = itemView.findViewById(R.id.saleTotal);
            date = itemView.findViewById(R.id.saleDate);

            card_item = itemView.findViewById(R.id.saleCard);
            this.itemClickListener = itemClickListener;
            card_item.setOnClickListener(this);
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
