package com.example.e_commerce.ui.auth;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.e_commerce.R;

public class LogoutFragment extends Fragment {

    private AuthenticationViewModel authenticationViewModel;
    private Button logoutButton;
    private NavController navController;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialiser le ViewModel en utilisant ViewModelProvider
        authenticationViewModel = new ViewModelProvider(this).get(AuthenticationViewModel.class);

        authenticationViewModel.getLoggedStatus().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isUserLogged) {
                // Mettez à jour l'interface utilisateur ou effectuez d'autres actions en fonction de l'état de connexion
                if (isUserLogged) {
                    // L'utilisateur est connecté
                    // Naviguer vers une autre activité ou effectuer d'autres actions
                    navController.navigate(R.id.action_logoutFragment_to_loginFragment);
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_logout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        logoutButton = view.findViewById(R.id.buttonLogoutLogout);

        // Initialisation de notre navController
        navController = Navigation.findNavController(view);

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Appeler la méthode de registre dans le ViewModel
                authenticationViewModel.logout();
                // Afficher le Toast de succès
                Toast.makeText(requireContext(), "Vous êtes maintenant déconnecté !", Toast.LENGTH_SHORT).show();
            }
        });
    }
}