package com.example.e_commerce.ui.categories;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.e_commerce.MainActivity;
import com.example.e_commerce.R;
import com.example.e_commerce.adapters.ProduitAdapter;
import com.example.e_commerce.ui.auth.AuthenticationViewModel;

import java.util.ArrayList;

public class MainCategoryFragment extends Fragment implements ProduitAdapter.OnItemClickedListner {
    private MainCategoryViewModel mainCategoryViewModel;
    private ProduitAdapter adapter;
    private RecyclerView rvSpecialProducts;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialiser le ViewModel en utilisant ViewModelProvider
        mainCategoryViewModel = new ViewModelProvider(this).get(MainCategoryViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_category, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        rvSpecialProducts = view.findViewById(R.id.rvSpecialProducts);
        rvSpecialProducts.setLayoutManager(new LinearLayoutManager(getContext()));
        rvSpecialProducts.setHasFixedSize(true);
        adapter = new ProduitAdapter(this);
        rvSpecialProducts.setAdapter(adapter);

        // Observer la LiveData pour mettre à jour l'interface utilisateur lorsque la liste des produits change
        mainCategoryViewModel.getProduitsLiveData().observe(getViewLifecycleOwner(), produits -> {
            // Mettre à jour l'adaptateur avec la nouvelle liste de produits
            if (produits != null) {
                Log.d("LISTE PRODUITS", String.valueOf(produits.size()));
                adapter.setListProduits(produits);
                adapter.notifyDataSetChanged();
            }
        });

        // Appeler la méthode pour récupérer tous les produits
        mainCategoryViewModel.getAllProduits();

    }

    @Override
    public void onItemClick(int position) {
        // Gérer le clic sur un élément de la liste si nécessaire
        //navController.navigate(R.id.action_produitsListFragment_to_detailFragment);
    }
}