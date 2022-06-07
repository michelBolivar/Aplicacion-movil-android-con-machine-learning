package com.example.test.FragmentosAdministrador;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.google.firebase.storage.FirebaseStorage.getInstance;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.test.Adaptador.Adaptador;
import com.example.test.Modelo.Administrador;
import com.example.test.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class ListaAdmin extends Fragment {


    RecyclerView administradores_recyclerView;
    Adaptador adaptador;
    List<Administrador> administradorList;
    FirebaseAuth firebaseAuth;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lista_admin, container, false);

        administradores_recyclerView = view.findViewById(R.id.administradores_recyclerView);
        administradores_recyclerView.setHasFixedSize(true);

        administradores_recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        administradorList = new ArrayList<>();

        firebaseAuth = FirebaseAuth.getInstance();

        ObtenerLista();
        return view;
    }

    private void ObtenerLista() {
        FirebaseUser user = firebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("BASE DE DATOS ADMINISTRADORES");
        reference.orderByChild("NOMBRES").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                administradorList.clear();

                for (DataSnapshot ds : snapshot.getChildren()) {
                    Administrador administrador = ds.getValue(Administrador.class);

//                    if (!administrador.getUID().equals(user.getUid())) {
//                        administradorList.add(administrador);
//                    }

                    administradorList.add(administrador);
                    adaptador = new Adaptador(getActivity(), administradorList);
                    administradores_recyclerView.setAdapter(adaptador);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}