package com.example.testone;

import static com.example.testone.GroceryItemActivity.GROCERY_ITEM_KEY;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class GroceryItemAdapter extends RecyclerView.Adapter<GroceryItemAdapter.ViewHolder> {
    private ArrayList<GroceryItem> items = new ArrayList<>();
    private Context mContext;

    public GroceryItemAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.grocery_item, parent, false);
        return new ViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.tvName.setText(items.get(position).getName());
        holder.tvPrice.setText(String.valueOf(items.get(position).getPrice()) + "Rs");
        Glide.with(mContext)
                .asBitmap()
                .load(items.get(position).getImageUrl())
                .into(holder.ivItem);
        holder.mcvParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, GroceryItemActivity.class);
                intent.putExtra(GROCERY_ITEM_KEY, items.get(holder.getBindingAdapterPosition()));
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(ArrayList<GroceryItem> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private AppCompatTextView tvPrice, tvName;
        private AppCompatImageView ivItem;
        private MaterialCardView mcvParent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            ivItem = itemView.findViewById(R.id.ivItem);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            mcvParent = itemView.findViewById(R.id.mcvParent);
        }
    }
}