package com.example.test.Catego;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import static java.util.concurrent.TimeUnit.SECONDS;

import android.widget.Toast;

import com.example.test.CategoriasCliente.OrinoquiaCli;
import com.example.test.CategoriasCliente.ParamoCli;
import com.example.test.FragmentosCliente.InfoApp;
import com.example.test.FragmentosCliente.Inicio;
import com.example.test.FragmentosCliente.SobreNosotros;
import com.example.test.R;

import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class ControladorCD extends AppCompatActivity {

    private final int TIEMPO =5000;
    private Object string;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controlador_cd);


        String CategoriaRecuperada = getIntent().getStringExtra("Ecosistema");

        if(CategoriaRecuperada.equals("paramo")){
            startActivity(new Intent(ControladorCD.this, ParamoCli.class));
            finish();
        }
        if(CategoriaRecuperada.equals("orinoquia")){
            startActivity(new Intent(ControladorCD.this, OrinoquiaCli.class));
            finish();
        }
    }

    public  void ejecutar(String text , Context context ) {
      // Toast.makeText(context, text +"metodo", Toast.LENGTH_SHORT).show();
        switch (text ){
            case ("Oso Andino") :
               // Toast.makeText(context, "oso", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, ParamoCli.class);
                context.startActivity(intent);
                //context.stopService(intent);
                ((Activity) context).finish();
                break;

            case ("Venado"):
               // Toast.makeText(context, "Venado", Toast.LENGTH_SHORT).show();
                Intent intent2 = new Intent(context, ParamoCli.class);
                context.startActivity(intent2);
                //context.stopService(intent2);
                ((Activity) context).finish();
                break;

            case ("Condor Andino"):
                //Toast.makeText(context, "Condor Andino", Toast.LENGTH_SHORT).show();
                Intent intent4 = new Intent(context, ParamoCli.class);
                context.startActivity(intent4);
                ((Activity) context).finish();
                break;


            case ("Puma"):
                //Toast.makeText(context, "Puma", Toast.LENGTH_SHORT).show();
                Intent intent5 = new Intent(context, ParamoCli.class);
                context.startActivity(intent5);
                ((Activity) context).finish();
                break;


            case ("Caimán"):
                //Toast.makeText(context, "Caimán", Toast.LENGTH_SHORT).show();
                Intent intent6 = new Intent(context, OrinoquiaCli.class);
                context.startActivity(intent6);
                //context.stopService(intent3);
                ((Activity) context).finish();
                break;

            case ("Capibara"):
                //Toast.makeText(context, "Capibara", Toast.LENGTH_SHORT).show();
                Intent intent3 = new Intent(context, OrinoquiaCli.class);
                context.startActivity(intent3);

                ((Activity) context).finish();
                break;

            default:

               // Toast.makeText(context, "Escaneando", Toast.LENGTH_SHORT).show();
                break;
    }




    }
}


