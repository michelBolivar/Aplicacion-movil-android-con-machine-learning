package com.example.test.FragmentosCliente;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.widget.ImageViewCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.test.Catego.Anim_dispo.AnimalD;
import com.example.test.Catego.Anim_dispo.ViewHolderAD;
import com.example.test.Catego.Cat_dispo.CategoriaD;
import com.example.test.Catego.Cat_dispo.ViewHolderDC;
import com.example.test.Catego.ControladorCD;
import com.example.test.DetalleCliente.NoticiCliente;
import com.example.test.DetectorActivity;
import com.example.test.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Inicio extends Fragment {


    RecyclerView recyclerViewEcosisD;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference referenceD;
    LinearLayoutManager linearLayoutManagerD;


    RecyclerView recyclerViewAnimales;
    DatabaseReference referenceA;
    LinearLayoutManager linearLayoutManagerA;

    ImageView insta , face, you ;
    String url_inta = "https://www.instagram.com/kenai_latam";
    String url_face = "https://www.facebook.com/kenai.latam";
    String url_you = "https://www.youtube.com/channel/UCtisppKtmhhcBKv0R03h_Ug";
    String url_pagina = "https://www.kenailatam.com";

    Button info;
    Button btn_entendido;
    View camara;
    //LottieAnimationView camara;


    FirebaseRecyclerAdapter<CategoriaD, ViewHolderDC> firebaseRecyclerAdapterD;
    FirebaseRecyclerOptions<CategoriaD> optionD;


    FirebaseRecyclerAdapter<AnimalD, ViewHolderAD> firebaseRecyclerAdapterA;
    FirebaseRecyclerOptions<AnimalD> optionA;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_inicio, container, false);



        firebaseDatabase = FirebaseDatabase.getInstance();
        referenceD = firebaseDatabase.getReference("ECOSIS_DISPO");
        linearLayoutManagerD = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);

        recyclerViewEcosisD = view.findViewById(R.id.recyclerViewEcosisD);
        recyclerViewEcosisD.setHasFixedSize(true);
        recyclerViewEcosisD.setLayoutManager(linearLayoutManagerD);

        referenceA = firebaseDatabase.getReference("ANIMALES_INICIO");
        linearLayoutManagerA = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewAnimales = view.findViewById(R.id.recyclerViewAnimales);
        recyclerViewAnimales.setHasFixedSize(true);
        recyclerViewAnimales.setLayoutManager(linearLayoutManagerA);

        info = view.findViewById(R.id.info);


        camara = view.findViewById(R.id.camara);

        camara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), DetectorActivity.class));
            }
        });



        insta  = view.findViewById(R.id.insta);
        face = view . findViewById(R.id.face);
        you = view.findViewById(R.id.youtube);

        insta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri ins_lin = Uri.parse(url_inta);
                Intent i = new Intent(Intent.ACTION_VIEW, ins_lin);
                startActivity(i);
            }
        });
        face.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri ins_lin = Uri.parse(url_face);
                Intent i = new Intent(Intent.ACTION_VIEW, ins_lin);
                startActivity(i);
            }
        });
        you.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri ins_lin = Uri.parse(url_you);
                Intent i = new Intent(Intent.ACTION_VIEW, ins_lin);
                startActivity(i);
            }
        });


        VerCategoriasD();
        verSalidas();
        return view;


    }



    private void VerCategoriasD() {
        optionD = new FirebaseRecyclerOptions.Builder<CategoriaD>().setQuery(referenceD, CategoriaD.class).build();


//        ECOSISTEMA
        firebaseRecyclerAdapterD = new FirebaseRecyclerAdapter<CategoriaD, ViewHolderDC>(optionD) {
            @Override
            protected void onBindViewHolder(@NonNull ViewHolderDC viewHolderDC, int i, @NonNull CategoriaD categoriaD) {
                viewHolderDC.SeteoCategrias(
                        getActivity(),
                        categoriaD.getEcosis(),
                        categoriaD.getImagen()
                );
            }

            @NonNull
            @Override
            public ViewHolderDC onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.ecosistemas_dispositvo, parent, false);
                ViewHolderDC viewHolderDC = new ViewHolderDC(itemView);


                viewHolderDC.setOnClickListener(new ViewHolderDC.ClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

//                        OBTENEMOS EL NOMBRE DEL ECOSISTEMA
                        String ecosis = getItem(position).getEcosis();
                        Intent intent = new Intent(view.getContext(), ControladorCD.class);
                        intent.putExtra("Ecosistema", ecosis);
                        startActivity(intent);
                        Toast.makeText(getActivity(), ecosis, Toast.LENGTH_SHORT).show();
                    }
                });
                return viewHolderDC;
            }
        };



        recyclerViewEcosisD.setAdapter(firebaseRecyclerAdapterD);

    }

    private  void verSalidas(){
        optionA = new FirebaseRecyclerOptions.Builder<AnimalD>().setQuery(referenceA, AnimalD.class).build();

        //ANIMAL
        firebaseRecyclerAdapterA = new FirebaseRecyclerAdapter<AnimalD, ViewHolderAD>(optionA) {
            @Override
            protected void onBindViewHolder(@NonNull ViewHolderAD viewHolderAD, int i, @NonNull AnimalD animalD) {

                viewHolderAD.SeteoCategrias(
                        getActivity(),
                        animalD.getNombre(),
                        animalD.getImagen()
                );

            }

            @NonNull
            @Override
            public ViewHolderAD onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.noticias_dispositivo, parent, false);
                ViewHolderAD viewHolderA = new ViewHolderAD(itemView);

                viewHolderA.setOnClickListener(new ViewHolderAD.ClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        String Imagen = getItem(position).getImagen();
                        String Nombre = getItem(position).getNombre();
                        String Descrip = getItem(position).getDescrip();
//         String descrip = getItem(position).getDescripcion1();

                        //PASAMOS A LA ACTIVIDAD DETALLE CLIENTE
                        Intent intent = new Intent(getActivity(), NoticiCliente.class);
                        intent.putExtra("Imagen", Imagen);
                        intent.putExtra("Nombre", Nombre);
                        intent.putExtra("descrip",Descrip);
//         intent.putExtra("descrip", descrip);

                        startActivity(intent);
                    }
                });

                return viewHolderA;
            }
        };

        recyclerViewAnimales.setAdapter(firebaseRecyclerAdapterA);
    }

    @Override
    public Context getContext() {
        firebaseRecyclerAdapterD.startListening();
        firebaseRecyclerAdapterA.startListening();
        return super.getContext();
    }

    @Override
    public void onStart() {
        super.onStart();
        firebaseRecyclerAdapterD.startListening();
        firebaseRecyclerAdapterA.startListening();
    }


}