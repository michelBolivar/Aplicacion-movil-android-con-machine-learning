package com.example.test.FragmentosCliente;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.test.R;

import org.w3c.dom.Text;


public class Contactenos extends Fragment {

   String direccion ="kenailatam@gmail.com";
    AppCompatEditText asunto, mensaje;
    AppCompatButton enviar_info;


    String what_direc = "https://wa.me/3028344753?text=Hola%20%2C%20estoy%20interesad%40";

    AppCompatImageButton what;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_contactenos, container, false);

        asunto = view.findViewById(R.id.asunto);
        mensaje = view.findViewById(R.id.mensaje);
        enviar_info = view.findViewById(R.id.enviar_info);
        what = view.findViewById(R.id.what);

        enviar_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enviar();
            }
        });

        what.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Uri ins_lin = Uri.parse(what_direc);
                Intent i = new Intent(Intent.ACTION_VIEW, ins_lin);
                startActivity(i);
            }
        });



        return view;
    }

    public void  enviar(){
        Intent i  = new Intent(Intent.ACTION_SENDTO);
        i.setData(Uri.parse("mailto:"));
        i.putExtra(Intent.EXTRA_EMAIL, new String[]{direccion});
        i.putExtra(Intent.EXTRA_SUBJECT,asunto.getText()).toString();
        i.putExtra(Intent.EXTRA_TEXT,mensaje.getText()).toString();
        startActivity(i);


    }


}