package com.example.test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class InicioSesion extends AppCompatActivity {

    EditText cor, password;
    Button acce;


    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_sesion);

        //BOTON DE REGRESO
        Toolbar mytoolbar = (Toolbar) findViewById(R.id.toolbarIngresa);
        setSupportActionBar(mytoolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");


        cor = findViewById(R.id.Correo);
        password = findViewById(R.id.contra);

        acce = findViewById(R.id.acceAdmnin);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(InicioSesion.this);
        progressDialog.setMessage("Ingresando espere por favor");
        progressDialog.setCancelable(false);


        acce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Convertimos  a string EDITTEXT correo y contra
                String corre = cor.getText().toString();
                String pass = password.getText().toString();

                //VALIDACION  DEL CORREO
                if (!Patterns.EMAIL_ADDRESS.matcher(corre).matches()) {
                    cor.setError("Correo Invalido");
                    cor.setFocusable(true);
                } else if (pass.length() < 6) {
                    password.setError("Contraseña debe tener al menos 6 caracteres");
                    password.setFocusable(true);
                } else {
                      LogeoAdministradores(corre, pass);
                }

            }
        });
    }

    private void LogeoAdministradores(String corre, String pass) {
        progressDialog.show();
        progressDialog.setCancelable(false);
        firebaseAuth.signInWithEmailAndPassword(corre,pass)
                .addOnCompleteListener(InicioSesion.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        //SI EL INICIO DE SESION FUE EXITOSO
                        if(task.isSuccessful()){
                            progressDialog.dismiss();
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            startActivity(new Intent(InicioSesion.this, MainActivityAdministrador.class));
                            assert user !=null;
                            Toast.makeText(InicioSesion.this, "Bienvenid@ "+user.getEmail(), Toast.LENGTH_SHORT).show();
                            finish();
                        }else{
                            progressDialog.dismiss();
                            UsuarioInvalido();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                UsuarioInvalido();
            }
        });
    }

    private void UsuarioInvalido() {

        AlertDialog.Builder builder = new AlertDialog.Builder(InicioSesion.this);
        builder.setCancelable(false);
        builder.setTitle("ups! tenemos un error ");
        builder.setMessage("Verifique si el correo o contraseña son los correctos")
                .setPositiveButton("Entendido", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();
    }

   @Override
   public boolean onSupportNavigateUp() {
       onBackPressed();
       return super.onSupportNavigateUp();
   }
}



