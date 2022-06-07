package com.example.test.Detalle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.TextView;

import com.example.test.R;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class Detalle_Administrador extends AppCompatActivity {

    CircleImageView ImagenDetalleAdmin;
    TextView RolDetalleAdmin, NombreDetalleAdmin, CorreoDetalleAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_administrador);

        //BOTON DE REGRESO
        Toolbar mytoolbar = (Toolbar) findViewById(R.id.toolbarPerfil);
        setSupportActionBar(mytoolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");

        ImagenDetalleAdmin = findViewById(R.id.ImagenDetalleAdmin);
        RolDetalleAdmin = findViewById(R.id.RolDetalleAdmin);
        NombreDetalleAdmin = findViewById(R.id.NombreDetalleAdmin);
       // CorreoDetalleAdmin = findViewById(R.id.CorreoDetalleAdmin);


        String UIDetalle = getIntent().getStringExtra("UID");
        String NombresDetalle = getIntent().getStringExtra("NOMBRES");
        String RolDetalle = getIntent().getStringExtra("ROL");
      //  String CorreoDetalle = getIntent().getStringExtra("CORREO");
        String ImagenDetalle = getIntent().getStringExtra("IMAGEN");


        //SETEO
        RolDetalleAdmin.setText( RolDetalle);
        NombreDetalleAdmin.setText(  NombresDetalle);
        //CorreoDetalleAdmin.setText("Correo: " + CorreoDetalle);

        try {
            Picasso.get().load(ImagenDetalle).placeholder(R.drawable.bear_admin).into(ImagenDetalleAdmin);

        } catch (Exception e) {

            Picasso.get().load(R.drawable.bear_admin).into(ImagenDetalleAdmin);

        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}