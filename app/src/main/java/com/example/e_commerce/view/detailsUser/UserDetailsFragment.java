package com.example.e_commerce.view.detailsUser;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.e_commerce.R;
import com.example.e_commerce.viewModel.UserDetailsViewModel;

public class UserDetailsFragment extends Fragment {
    private UserDetailsViewModel userDetailsViewModel;
    private EditText edFirstName, edLastName;
    private Button saveInfo;
    private NavController navController;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialiser le ViewModel en utilisant ViewModelProvider
        userDetailsViewModel = new ViewModelProvider(this).get(UserDetailsViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_details_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        saveInfo = view.findViewById(R.id.saveInfo);
        edFirstName = view.findViewById(R.id.edFirstName);
        edLastName = view.findViewById(R.id.edLastName);

        // Initialisation de notre navController
        navController = Navigation.findNavController(view);

        // Pour récupérer les informations du client
        userDetailsViewModel.getClientInfo().observe(getViewLifecycleOwner(), clientInfo -> {
            // Update UI with client information
            if (clientInfo != null) {
                edFirstName.setText(clientInfo.getNom());
                edLastName.setText(clientInfo.getPrenom());
            }
        });

        saveInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nom = edFirstName.getText().toString();
                String prenom = edLastName.getText().toString();
                userDetailsViewModel.updateProfile(nom,prenom);

                Toast.makeText(requireContext(), "Profil mis à jour avec succès !", Toast.LENGTH_SHORT).show();
                navController.navigate(R.id.action_userDetailsFragment_to_profileFragment);
            }
        });

    }

}