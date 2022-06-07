package com.example.test.DetalleCliente;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.test.CategoriasCliente.ParamoCli;
import com.example.test.FragmentosCliente.Inicio;
import com.example.test.MainActivityCliente;
import com.example.test.R;
import com.squareup.picasso.Picasso;

public class NoticiCliente extends AppCompatActivity {

    ImageView ImagenDetalle;
    TextView NombreAnimalDetalle, Descrip1;

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(NoticiCliente.this, Inicio.class);
        startActivity(intent);
        finish();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notici_cliente);

        //BOTON DE REGRESO
        Toolbar mytoolbarNoti = (Toolbar) findViewById(R.id.toolbarNotici);
        setSupportActionBar(mytoolbarNoti);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");

        ImagenDetalle = findViewById(R.id.imgNotici);
        NombreAnimalDetalle = findViewById(R.id.nomNoti);
        Descrip1 = findViewById(R.id.desNoti);

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


    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}