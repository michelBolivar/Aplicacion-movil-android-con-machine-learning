package com.example.test.FragmentosAdministrador;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.test.Ecosistemas.OrinoquiaA.OrinoquiaA;
import com.example.test.Ecosistemas.ParamoA.ParamoA;
import com.example.test.R;

public class InicioAdmin extends Fragment {

    Button paramo , orinoquia;
    View im_paramo, im_orino;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_inicio_admin, container, false);

        paramo = view.findViewById(R.id.paramo);
        orinoquia = view.findViewById(R.id.orinoquia);
        im_paramo = view.findViewById(R.id.imagenParamoAdmin);
        im_orino = view.findViewById(R.id.imgAdminOrino);

        // PARAMO
        im_paramo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ParamoA.class));
            }
        });
        paramo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ParamoA.class));

            }
        });

//        ORINOQUIA
        orinoquia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), OrinoquiaA.class));

            }
        });
        im_orino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), OrinoquiaA.class));
            }
        });
        return  view;
    }
}