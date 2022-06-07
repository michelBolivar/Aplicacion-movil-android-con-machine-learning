package com.example.test.DetalleCliente;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test.R;
import com.github.clans.fab.FloatingActionButton;
import com.squareup.picasso.Picasso;

public class DetalleCliente extends AppCompatActivity {

    ImageView ImagenDetalle;
    TextView NombreAnimalDetalle, Descrip1;

    FloatingActionButton fabCompartir, fabInstagram, fabFacebook, fabPaginaWeb;

    Button compra;

    String url_inta = "https://www.instagram.com/kenai_latam";
    String url_face = "https://www.facebook.com/kenai.latam";
    String url_you = "https://www.youtube.com/channel/UCtisppKtmhhcBKv0R03h_Ug";
    String url_pagina = "https://www.kenailatam.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_cliente);

        //BOTON DE REGRESO
        Toolbar mytoolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(mytoolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");

        ImagenDetalle = findViewById(R.id.ImagenDetalle);
        NombreAnimalDetalle = findViewById(R.id.NombreAnimalDetalle);
        Descrip1 = findViewById(R.id.Descrip1);

      //  fabCompartir = findViewById(R.id.fabCompartir);
        fabInstagram = findViewById(R.id.fabInstagram);
        fabFacebook = findViewById(R.id.fabFacebook);
        fabPaginaWeb = findViewById(R.id.fabPaginaWeb);

        compra = findViewById(R.id.compra);


        String imagen = getIntent().getStringExtra("Imagen");
        String nombre = getIntent().getStringExtra("Nombre");
        String descrp = getIntent().getStringExtra("descrip");

        try {
            Picasso.get().load(imagen).placeholder(R.drawable.oso_def).into(ImagenDetalle);
        } catch (Exception e) {
            Picasso.get().load(R.drawable.oso_def).into(ImagenDetalle);
        }

        NombreAnimalDetalle.setText(nombre);
        Descrip1.setText(descrp);

        /*REDES SOCIALES */

        compra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Uri ins_lin = Uri.parse(url_pagina);
                Intent i = new Intent(Intent.ACTION_VIEW, ins_lin);
                startActivity(i);
            }
        });
        /*fabCompartir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CompartirImagen();
                //Toast.makeText(DetalleCliente.this, "Compartir", Toast.LENGTH_SHORT).show();
            }
        });*/

        fabInstagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri ins_lin = Uri.parse(url_inta);
                Intent i = new Intent(Intent.ACTION_VIEW, ins_lin);
                startActivity(i);
                // Toast.makeText(DetalleCliente.this, "Insta", Toast.LENGTH_SHORT).show();

            }
        });

        fabFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Uri ins_lin = Uri.parse(url_face);
                Intent i = new Intent(Intent.ACTION_VIEW, ins_lin);
                startActivity(i);
                // Toast.makeText(DetalleCliente.this, "Face", Toast.LENGTH_SHORT).show();
            }
        });

        fabPaginaWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Uri ins_lin = Uri.parse(url_you);
                Intent i = new Intent(Intent.ACTION_VIEW, ins_lin);
                startActivity(i);
                //Toast.makeText(DetalleCliente.this, "Pagina ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void CompartirImagen(){
        try{

            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT,getResources().getString(R.string.app_name));
            String aux = "Descarga la app\n";
            aux = aux + "https://www.kenailatam.com";
            i.putExtra(Intent.EXTRA_TEXT,aux);
            startActivity(i);
        }
        catch (Exception e){

            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}