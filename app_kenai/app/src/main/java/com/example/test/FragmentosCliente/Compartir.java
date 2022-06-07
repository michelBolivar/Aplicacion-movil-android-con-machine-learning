package com.example.test.FragmentosCliente;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.test.R;

public class Compartir extends Fragment {


    AppCompatButton btn_entendido;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_compartir, container, false);


        btn_entendido = view.findViewById(R.id.btn_entendi);

        btn_entendido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(getActivity(), Inicio.class);
                startActivity(intent2);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        //onCreateView();
        super.onResume();

    }
}