package com.example.test.CategoriasCliente;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


import com.example.test.DetalleCliente.DetalleCliente;
import com.example.test.Ecosistemas.ParamoA.Paramo;

import com.example.test.Ecosistemas.ParamoA.ViewHolderParamo;
import com.example.test.MainActivityCliente;
import com.example.test.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class ParamoCli extends AppCompatActivity {

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ParamoCli.this, MainActivityCliente.class);
        startActivity(intent);
        finish();
    }

    RecyclerView recyclerViewParamoC;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mRef;

    FirebaseRecyclerAdapter<Paramo, ViewHolderParamo> firebaseRecyclerAdapter;
    FirebaseRecyclerOptions<Paramo> options;

    ImageView insta , face, you ,regreso ;
    String url_inta = "https://www.instagram.com/kenai_latam";
    String url_face = "https://www.facebook.com";
    String url_you = "https://www.youtube.com";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paramo_cli);

        //BOTON DE REGRESO
        Toolbar mytoolbar = (Toolbar) findViewById(R.id.toolbarPara);
        setSupportActionBar(mytoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");


        recyclerViewParamoC = findViewById(R.id.recyclerViewParamoC);
        recyclerViewParamoC.setHasFixedSize(true);


        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mRef = mFirebaseDatabase.getReference("PARAMO");

        ListarImagenesParamoC();

        /*REDES SOCIALES*/
        insta  = findViewById(R.id.insta);
        face = findViewById(R.id.face);
        you = findViewById(R.id.youtube);


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

    }


    private void ListarImagenesParamoC() {

        options = new FirebaseRecyclerOptions.Builder<Paramo>().setQuery(mRef, Paramo.class).build();
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Paramo, ViewHolderParamo>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ViewHolderParamo viewHolderParamo, int i, @NonNull Paramo paramo) {
                viewHolderParamo.SeteoPeliculas(
                        getApplicationContext(),
                        paramo.getNombre(),
                        paramo.getDescripcion1(),
                        paramo.getDescripcion2(),
                        paramo.getDescripcion3(),
                        paramo.getImagen(),
                        paramo.getImagen2()
                );
            }

            @NonNull
            @Override
            public ViewHolderParamo onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_paramo, parent, false);
                ViewHolderParamo viewHolderParamo = new ViewHolderParamo(itemView);
                viewHolderParamo.setOnClickListener(new ViewHolderParamo.ClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

//                        Toast.makeText(ParamoCli.this, "ITEM CLICK", Toast.LENGTH_SHORT).show();
//                        startActivity(new Intent(ParamoCli.this, DetalleCliente.class));

                        //OBTENER LOS DATOS DE LA IMAGEN
                        String Imagen = getItem(position).getImagen();
                        String Nombre = getItem(position).getNombre();
                        String descrip = getItem(position).getDescripcion1();

                        //PASAMOS A LA ACTIVIDAD DETALLE CLIENTE
                        Intent intent = new Intent(ParamoCli.this, DetalleCliente.class);
                        intent.putExtra("Imagen", Imagen);
                        intent.putExtra("Nombre", Nombre);
                        intent.putExtra("descrip", descrip);

                        startActivity(intent);

                    }

                    @Override
                    public void OnItemLongClick(View view, int position) {

                    }
                });
                return viewHolderParamo;
            }
        };
        recyclerViewParamoC.setLayoutManager(new GridLayoutManager(ParamoCli.this, 2));
        firebaseRecyclerAdapter.startListening();
        recyclerViewParamoC.setAdapter(firebaseRecyclerAdapter);
    }


    protected void onStart() {
        super.onStart();

        if (firebaseRecyclerAdapter != null) {
            firebaseRecyclerAdapter.startListening();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}