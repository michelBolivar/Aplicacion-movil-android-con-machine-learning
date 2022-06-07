package com.example.test.FragmentosCliente;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.test.InicioSesion;
import com.example.test.R;


public class Acceder extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Button acceder;

        View view = inflater.inflate(R.layout.fragment_acceder, container, false);
        // Inflate the layout for this fragment

        acceder = view.findViewById(R.id.AccederAdmi);

       acceder.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               startActivity(new Intent(getActivity(), InicioSesion.class));
           }
       });
        return  view;
    }
}