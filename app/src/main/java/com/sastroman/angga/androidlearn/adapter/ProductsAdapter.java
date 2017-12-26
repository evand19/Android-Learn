package com.sastroman.angga.androidlearn.adapter;

/**
 * Created by Angga N P on 12/13/2017.
 */


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sastroman.angga.androidlearn.R;
import com.sastroman.angga.androidlearn.model.Products;

import java.util.ArrayList;
import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductsViewHolder> implements Filterable {

    private List<Products> products;
    private List<Products> filProducts;
    private int rowLayout;
    private Context context;
    private MoviesAdapter listener;

    public class ProductsViewHolder extends RecyclerView.ViewHolder {
        LinearLayout productsLayout;
        TextView productsName;
        TextView data;
        TextView productsDescription;


        public ProductsViewHolder(View v) {
            super(v);
            productsLayout = (LinearLayout) v.findViewById(R.id.movies_layout);
            productsName = (TextView) v.findViewById(R.id.title);
            data = (TextView) v.findViewById(R.id.subtitle);
            productsDescription = (TextView) v.findViewById(R.id.description);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //listener.onContactSelected(filmovies.get(getAdapterPosition()));
                }
            });
        }
    }

    public ProductsAdapter(List<Products> products, int rowLayout, Context context) {
        this.products = products;
        this.filProducts = products;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public ProductsAdapter.ProductsViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new ProductsViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ProductsViewHolder holder, final int position) {
        holder.productsName.setText(products.get(position).getName());
        holder.data.setText(products.get(position).getPrice());
        holder.productsDescription.setText(products.get(position).getType());
    }

    @Override
    public int getItemCount() {
        return filProducts.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    filProducts = products;
                } else {
                    List<Products> filteredList = new ArrayList<>();
                    for (Products row : products) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    filProducts = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filProducts;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filProducts = (ArrayList<Products>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface ContactsAdapterListener {
        void onContactSelected(Products products);
    }
}