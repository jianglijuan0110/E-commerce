package com.example.e_commerce.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.e_commerce.R;
import com.example.e_commerce.data.model.Produit;

import java.util.List;

public class ProduitAdapter extends RecyclerView.Adapter<ProduitAdapter.ProduitViewHolder> {
    private List<Produit> listProduits;

    public ProduitAdapter(List<Produit> listProduits) {
        this.listProduits = listProduits;
    }

    private OnItemClickedListner onItemClickedListner;

    public void setListProduits(List<Produit> listProduits) {
        this.listProduits = listProduits;
    }

    public ProduitAdapter(OnItemClickedListner onItemClickedListner) {
        this.onItemClickedListner = onItemClickedListner;
    }

    @NonNull
    @Override
    public ProduitViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.produit_item, parent,false);
        return new ProduitViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProduitViewHolder holder, int position) {
        Produit model = listProduits.get(position);
        holder.nameProduct.setText(model.getNom());
        Glide.with(holder.itemView).load(model.getProduitImg()).into(holder.img_product);
        String prixText = String.valueOf(model.getPrix());
        holder.priceProduct.setText(prixText);

    }

    @Override
    public int getItemCount() {
        if(listProduits == null){
            return 0;
        }else {
            return listProduits.size();
        }
    }

    public class ProduitViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView nameProduct;
        private ImageView img_product;
        private TextView priceProduct;
        private LinearLayout layoutProduct;

        public ProduitViewHolder(@NonNull View itemView) {
            super(itemView);

            nameProduct = itemView.findViewById(R.id.nameProduct);
            img_product = itemView.findViewById(R.id.img_product);
            priceProduct = itemView.findViewById(R.id.priceProduct);
            layoutProduct = itemView.findViewById(R.id.layoutProduct);
            layoutProduct.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onItemClickedListner.onItemClick(getAdapterPosition());
        }
    }
    public interface OnItemClickedListner{
        void onItemClick(int position);
    }

}
