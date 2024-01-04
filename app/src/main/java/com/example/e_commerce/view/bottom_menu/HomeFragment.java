package com.example.e_commerce.view.bottom_menu;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.e_commerce.R;
import com.example.e_commerce.adapters.HomeViewPagerAdapter;
import com.example.e_commerce.view.produit.ListProduitsFragment;
import com.example.e_commerce.view.produit.SaveProduitFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private HomeViewPagerAdapter viewPagerAdapter;
    private TabLayoutMediator tabLayoutMediator;
    private ViewPager2 viewPager;
    private TabLayout tabLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialiser la liste de fragments
        List<Fragment> categoriesFragments = new ArrayList<>();
        categoriesFragments.add(new ListProduitsFragment());
        categoriesFragments.add(new SaveProduitFragment());

        // Créer l'adaptateur pour le ViewPager
        viewPagerAdapter = new HomeViewPagerAdapter(getChildFragmentManager(), getLifecycle(), categoriesFragments);

        // Configurer le ViewPager
        viewPager = view.findViewById(R.id.viewpagerHome);
        viewPager.setAdapter(viewPagerAdapter);

        // Configurer le TabLayout
        tabLayout = view.findViewById(R.id.tabLayout);
        tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager, ((tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Accueil");
                    break;
                case 1:
                    tab.setText("Ajouter produit");
                    break;
                default:
                    break;
            }
        }));
        tabLayoutMediator.attach();
    }
}