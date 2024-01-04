package com.example.e_commerce.view.panier;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.e_commerce.R;
import com.example.e_commerce.adapters.ProduitPanierAdapter;
import com.example.e_commerce.model.model.Panier;
import com.example.e_commerce.viewModel.UserDetailsViewModel;

import java.util.List;

public class PanierFragment extends Fragment implements ProduitPanierAdapter.OnItemClickedListner{
    private UserDetailsViewModel userDetailsViewModel;
    private ProduitPanierAdapter adapter;
    private RecyclerView rvCart;
    private ConstraintLayout layout_cart_empty, totalBoxContainer;
    private TextView totalPrice;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialiser le ViewModel en utilisant ViewModelProvider
        userDetailsViewModel = new ViewModelProvider(this).get(UserDetailsViewModel.class);

        // Observer pour les changements du prix total
        userDetailsViewModel.getTotalPriceLiveData().observe(this, totalPrice -> {
            // Mettre à jour l'interface utilisateur avec le prix total
            Log.d("TOTAL PANIER", "Prix total du panier : " + totalPrice);
            // Mettez à jour le TextView du prix total dans l'interface utilisateur
            updateTotalPriceUI(totalPrice);
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_panier, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        userDetailsViewModel.calculateTotalPrice();

        totalPrice = view.findViewById(R.id.totalPrice);
        totalBoxContainer = view.findViewById(R.id.totalBoxContainer);

        layout_cart_empty = view.findViewById(R.id.layout_cart_empty);

        rvCart = view.findViewById(R.id.rvCart);
        rvCart.setLayoutManager(new LinearLayoutManager(getContext()));
        rvCart.setHasFixedSize(true);
        adapter = new ProduitPanierAdapter(this);
        rvCart.setAdapter(adapter);

        userDetailsViewModel.getPanierProduits().observe(getViewLifecycleOwner(), new Observer<List<Panier>>() {
            @Override
            public void onChanged(List<Panier> panierProduits) {
                // Mettez à jour la liste des produits de l'adaptateur
                //adapter.setListProduits(panierProduits);
                adapter.setListProduitsPanier(panierProduits);
                // Notifiez l'adaptateur du changement
                adapter.notifyDataSetChanged();

                // Mettez à jour la visibilité de rvCart en fonction de la taille de la liste des produits
                if (panierProduits != null && !panierProduits.isEmpty()) {
                    // Panier non vide, rendre rvCart visible
                    rvCart.setVisibility(View.VISIBLE);
                    layout_cart_empty.setVisibility(View.INVISIBLE);
                } else {
                    // Panier vide, rendre rvCart invisible et afficher le message d'absence de produits
                    rvCart.setVisibility(View.INVISIBLE);
                    layout_cart_empty.setVisibility(View.VISIBLE);
                }
            }
        });

    }


    private void updateTotalPriceUI(double totalPrice) {
        // Mettez à jour le TextView du prix total avec la nouvelle valeur
        this.totalPrice.setText(String.valueOf(totalPrice) + " EURO");

        // Mettez à jour la visibilité du conteneur totalBoxContainer en fonction de la liste des produits
        totalBoxContainer.setVisibility(adapter.getListProduitsPanier() != null && !adapter.getListProduitsPanier().isEmpty() ? View.VISIBLE : View.INVISIBLE);
    }


    @Override
    public void onImagePlusClick(int position) {
        String  idPanier = adapter.getListProduitsPanier().get(position).getIdPanier();
        userDetailsViewModel.incrementQuantityInPanier(idPanier);
    }

    @Override
    public void onImageMinusClick(int position) {
        String  idPanier = adapter.getListProduitsPanier().get(position).getIdPanier();
        userDetailsViewModel.decrementQuantityInPanier(idPanier);
    }
}