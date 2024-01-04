package com.example.e_commerce.view.detailsUser;

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
import android.widget.ImageView;
import android.widget.TextView;

import com.example.e_commerce.MainActivity;
import com.example.e_commerce.R;
import com.example.e_commerce.model.model.Client;
import com.example.e_commerce.model.repository.ClientRepository;
import com.example.e_commerce.viewModel.UserDetailsViewModel;

public class ProfileFragment extends Fragment implements ClientRepository.ClientInfoCallback {

    private UserDetailsViewModel userDetailsViewModel;
    private ImageView imageUser;
    private TextView userName, allOrders, logout, edit_personal_details;
    private NavController navController;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialiser le ViewModel en utilisant ViewModelProvider
        userDetailsViewModel = new ViewModelProvider(this).get(UserDetailsViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imageUser = view.findViewById(R.id.imageUser);
        logout = view.findViewById(R.id.logout);
        edit_personal_details = view.findViewById(R.id.edit_personal_details);
        userName = view.findViewById(R.id.userName);
        allOrders = view.findViewById(R.id.allOrders);

        // Initialisation de notre navController
        navController = Navigation.findNavController(view);

        // Pour récupérer les informations du client
        userDetailsViewModel.getClientInfo().observe(getViewLifecycleOwner(), clientInfo -> {
            // Update UI with client information
            if (clientInfo != null) {
                userName.setText(clientInfo.getNom());
            }
        });

        edit_personal_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_profileFragment_to_userDetailsFragment);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userDetailsViewModel.logOut();
                Intent intent = new Intent(requireActivity(), MainActivity.class);
                startActivity(intent);

                requireActivity().finish();
            }
        });

        allOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //navController profil to orders
            }
        });
    }

    @Override
    public void onClientInfoLoaded(Client client) {
        // Traitement des informations du client ici
        Log.d("PROFIL OK", "Informations client récupérées avec succès: " + client.toString());
    }

    @Override
    public void onClientInfoError(String errorMessage) {
        // Gestion des erreurs lors de la récupération des informations client
        Log.e("PROFIL (ECHEC)", "Erreur lors de la récupération des informations client: " + errorMessage);
    }
}