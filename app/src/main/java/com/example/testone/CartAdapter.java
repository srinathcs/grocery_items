package com.example.testone;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    public interface TotalPrice{
        void getTotalPrice(double price);
    }
    public interface DeleteItem {
        void onDeleteResult(GroceryItem item);
    }

    private DeleteItem deleteItem;
    private TotalPrice totalPrice;

    ArrayList<GroceryItem> items = new ArrayList<>();
    private Context mContext;
    private Fragment fragment;

    public CartAdapter(Context mContext, Fragment fragment) {
        this.mContext = mContext;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item,parent,false);
        return new ViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvName.setText(items.get(position).getName());
        holder.tvPrice.setText(items.get(position).getPrice() + "Rs");
        holder.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
                dialog.setTitle("Deleting...")
                        .setMessage("Are You Sure You Want To Delete This Item")
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                try {
                                    deleteItem = (DeleteItem) fragment;
                                    deleteItem.onDeleteResult(items.get(holder.getBindingAdapterPosition()));
                                }catch (ClassCastException e){
                                    e.printStackTrace();
                                }
                            }
                        });
                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(ArrayList<GroceryItem> items) {
        this.items = items;
        calculateTotalPrice();
        notifyDataSetChanged();
    }

    private void calculateTotalPrice(){
        double price = 0;
        for (GroceryItem item: items){
            price += item.getPrice();
        }
        price = Math.round(price*100.0)/100.0;
        try {
            totalPrice =(TotalPrice) fragment;
            totalPrice.getTotalPrice(price);
        }catch (ClassCastException e){
            e.printStackTrace();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private AppCompatTextView tvName, tvDelete, tvPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvDelete = itemView.findViewById(R.id.tvDelete);
            tvPrice = itemView.findViewById(R.id.tvPrice);
        }
    }
}
