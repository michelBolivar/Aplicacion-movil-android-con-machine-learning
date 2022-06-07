package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class Carga extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carga);

        final int DURACION = 5000;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //cuando termine la animacion se cambie de pantalla a la principal
                Intent intent = new Intent(Carga.this,MainActivityAdministrador.class);
                intent.putExtra("inicio","inicio");
                startActivity(intent);


                //al regresar salga de la aplicacion
                finish();

            }
        },DURACION);
    }
}