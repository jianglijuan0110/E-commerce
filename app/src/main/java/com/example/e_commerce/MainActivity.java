package com.example.e_commerce;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDeepLinkBuilder;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.app.PendingIntent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialisation de notre navController
        /*navController = Navigation.findNavController(this, R.id.navHostFragment);

        // Connecter le BottomNavigationView au NavController
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);*/
    }
}