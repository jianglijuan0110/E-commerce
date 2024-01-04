package com.example.e_commerce.view.produit;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.e_commerce.R;
import com.example.e_commerce.model.model.Produit;
import com.example.e_commerce.view.bottom_menu.HomeFragment;
import com.example.e_commerce.viewModel.PanierDetailsViewModel;

public class ProduitDetailsFragment extends Fragment {
    private PanierDetailsViewModel panierDetailsViewModel;
    private ImageView viewPagerProductImage;
    private TextView productDescription, productPrice, productName;
    private Button buttonAddToCart;
    private Spinner quantiteSpinner;
    private int selectedQuantite = 1; // Variable globale pour stocker la quantité sélectionnée
    private NavController navController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialiser le ViewModel en utilisant ViewModelProvider
        panierDetailsViewModel = new ViewModelProvider(this).get(PanierDetailsViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_produit_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialisation de notre navController
        navController = Navigation.findNavController(view);

        productDescription = view.findViewById(R.id.productDescription);
        productPrice = view.findViewById(R.id.productPrice);
        productName = view.findViewById(R.id.productName);
        viewPagerProductImage = view.findViewById(R.id.viewPagerProductImage);

        buttonAddToCart = view.findViewById(R.id.buttonAddToCart);

        quantiteSpinner = view.findViewById(R.id.quantiteSpinner);
        //int quantite = Integer.parseInt(quantiteSpinner.toString());

        Produit produit = new Produit();

        // Récupérer les données du produit depuis le Bundle
        if (getArguments() != null) {
            Produit selectedProduit = getArguments().getParcelable("selectedProduit");

            // Mettre à jour l'interface utilisateur avec les détails du produit
            if (selectedProduit != null) {
                Log.d("PRODUIT SELECTIONNE", selectedProduit.getNom());
                productDescription.setText(selectedProduit.getDescription());
                productPrice.setText(String.valueOf(selectedProduit.getPrix()));
                productName.setText(selectedProduit.getNom());

                produit.setProduitId(selectedProduit.getProduitId());
                produit.setNom(selectedProduit.getNom());
                produit.setDescription(selectedProduit.getDescription());
                produit.setPrix(selectedProduit.getPrix());
                produit.setProduitImg(selectedProduit.getProduitImg());

                // Utilisez Glide pour charger l'image depuis Firebase Storage
                if (getContext() != null) {
                    Glide.with(getContext())
                            .load(selectedProduit.getProduitImg()) // charger l'image
                            .apply(new RequestOptions()) // Placeholder facultatif
                            .into(viewPagerProductImage);
                }
            }
        }

        quantiteSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Récupérer l'entier sélectionné
                String selectedValue = parentView.getItemAtPosition(position).toString();
                selectedQuantite = Integer.parseInt(selectedValue);

                // Faites ce que vous voulez avec la valeur sélectionnée
                // Par exemple, imprimer la valeur dans la console
                Log.d("Valeur sélectionnée : " , selectedValue);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Lorsque rien n'est sélectionné
                Log.d("Valeur sélectionnée : " , "1");
            }
        });

        buttonAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                panierDetailsViewModel.ajouterAuPanier(produit,selectedQuantite);

                Intent intent = new Intent(requireActivity(), HomeFragment.class);
                startActivity(intent);

                Toast.makeText(requireContext(), "Produit ajouté avec succès au panier !", Toast.LENGTH_SHORT).show();
            }
        });

    }
}