package com.example.e_commerce;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ShoppingActivity extends AppCompatActivity {
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping);

        // Initialisation de notre navController
        navController = Navigation.findNavController(this, R.id.shoppingHostFragment);

        // Connecter le BottomNavigationView au NavController
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);

    }
}