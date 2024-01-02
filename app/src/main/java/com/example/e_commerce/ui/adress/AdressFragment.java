package com.example.e_commerce.ui.adress;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.e_commerce.R;
import com.example.e_commerce.data.model.Adresse;
import com.example.e_commerce.data.model.Client;
import com.example.e_commerce.ui.auth.AuthenticationViewModel;
import com.google.firebase.auth.FirebaseUser;

public class AdressFragment extends Fragment {
    private AdressViewModel adressViewModel;
    private EditText edAddressTitle, edFullName, edStreet, edPhone, edCity, edState;
    private Button buttonDelelte, buttonSave;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialiser le ViewModel en utilisant ViewModelProvider
        adressViewModel = new ViewModelProvider(this).get(AdressViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_adress, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        edAddressTitle = view.findViewById(R.id.edAddressTitle);
        edFullName = view.findViewById(R.id.edFullName);
        edStreet = view.findViewById(R.id.edStreet);
        edPhone = view.findViewById(R.id.edPhone);
        edCity = view.findViewById(R.id.edCity);
        edState = view.findViewById(R.id.edState);

        buttonDelelte = view.findViewById(R.id.buttonDelelte);
        buttonSave = view.findViewById(R.id.buttonSave);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Récupérer les données depuis les champs d'entrée
                String titre = edAddressTitle.getText().toString();
                String nom = edFullName.getText().toString();
                String rue = edStreet.getText().toString();
                String phone = edPhone.getText().toString();
                String ville = edCity.getText().toString();
                String pays = edState.getText().toString();

                Adresse adresse = new Adresse(titre,ville,pays,nom,rue,phone);
                adressViewModel.addAdress(adresse);
            }
        });

    }

}