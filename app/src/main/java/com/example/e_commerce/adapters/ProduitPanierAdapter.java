package com.example.e_commerce.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.e_commerce.R;
import com.example.e_commerce.model.model.Panier;

import java.util.List;

public class ProduitPanierAdapter extends RecyclerView.Adapter<ProduitPanierAdapter.ProduitPanierViewHolder> {

    private List<Panier> listProduitsPanier;

    private ProduitPanierAdapter.OnItemClickedListner onItemClickedListner;

    public void setListProduitsPanier(List<Panier> listProduitsPanier) {
        this.listProduitsPanier = listProduitsPanier;
        notifyDataSetChanged();
    }

    public List<Panier> getListProduitsPanier(){
        return listProduitsPanier;
    }

    public ProduitPanierAdapter(ProduitPanierAdapter.OnItemClickedListner onItemClickedListner) {
        this.onItemClickedListner = onItemClickedListner;
    }

    @NonNull
    @Override
    public ProduitPanierAdapter.ProduitPanierViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.produit_panier_item, parent,false);
        return new ProduitPanierAdapter.ProduitPanierViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProduitPanierAdapter.ProduitPanierViewHolder holder, int position) {
        Panier model = listProduitsPanier.get(position);
        holder.productCartName.setText(model.getProduit().getNom());
        Glide.with(holder.itemView).load(model.getProduit().getProduitImg()).into(holder.imageCartProduct);
        String prixText = String.valueOf(model.getProduit().getPrix());
        holder.productCartPrice.setText(prixText);
        holder.productQuantity.setText(String.valueOf(model.getQuantite()));

    }

    @Override
    public int getItemCount() {
        if(listProduitsPanier == null){
            return 0;
        }else {
            return listProduitsPanier.size();
        }
    }

    public class ProduitPanierViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView productCartName, productQuantity, productCartPrice;
        private ImageView imageCartProduct, imagePlus, imageMinus;

        public ProduitPanierViewHolder(@NonNull View itemView) {
            super(itemView);

            productCartName = itemView.findViewById(R.id.productCartName);
            imageCartProduct = itemView.findViewById(R.id.imageCartProduct);
            productCartPrice = itemView.findViewById(R.id.productCartPrice);
            productQuantity = itemView.findViewById(R.id.productQuantity);

            // Ajouter le clic sur imagePlus et imageMinus
            imagePlus = itemView.findViewById(R.id.imagePlus);
            imagePlus.setOnClickListener(this);

            imageMinus = itemView.findViewById(R.id.imageMinus);
            imageMinus.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();

            if (v.getId() == R.id.imagePlus) {
                // Clic sur imagePlus, incrémenter la quantité
                incrementQuantity(position);
            } else if (v.getId() == R.id.imageMinus) {
                // Clic sur imageMinus, décrémenter la quantité
                decrementQuantity(position);
            }
        }

    }

    private void incrementQuantity(int position) {
        if (position != RecyclerView.NO_POSITION) {
            Panier panier = listProduitsPanier.get(position);
            int currentQuantity = panier.getQuantite();
            panier.setQuantite(currentQuantity + 1);
            notifyItemChanged(position);
        }
    }

    private void decrementQuantity(int position) {
        if (position != RecyclerView.NO_POSITION) {
            Panier panier = listProduitsPanier.get(position);
            int currentQuantity = panier.getQuantite();

            if (currentQuantity > 1) {
                // Décrémenter la quantité uniquement si elle est supérieure à 1
                panier.setQuantite(currentQuantity - 1);
                notifyItemChanged(position);
            } else {
                // Supprimer l'article si la quantité est égale à 1
                listProduitsPanier.remove(position);
                notifyItemRemoved(position);

                // Mettre à jour la visibilité de rvCart ici si nécessaire
            }
        }
    }

    public interface OnItemClickedListner{
        void onImagePlusClick(int position);
        void onImageMinusClick(int position);
    }

}
