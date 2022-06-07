package com.example.test.FragmentosAdministrador;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.test.InicioSesion;
import com.example.test.MainActivityAdministrador;
import com.example.test.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Cambio_contra extends AppCompatActivity {

    EditText ActualContraET, NuevaContraEt;
    Button CambiarContraBtn, IrInicioBtn;
    DatabaseReference BASE_DE_DATOS_ADMINISTRADORES;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambio_contra);

        ActualContraET = findViewById(R.id.ActualContraET);
        NuevaContraEt = findViewById(R.id.NuevaContraEt);
        CambiarContraBtn = findViewById(R.id.CambiarContraBtn);
        IrInicioBtn = findViewById(R.id.IrInicioBtn);

        BASE_DE_DATOS_ADMINISTRADORES = FirebaseDatabase.getInstance().getReference("BASE DE DATOS ADMINISTRADORES");
        firebaseAuth = FirebaseAuth.getInstance();

        user = firebaseAuth.getCurrentUser();

        progressDialog = new ProgressDialog(Cambio_contra.this);

        CambiarContraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Actual_contra = ActualContraET.getText().toString().trim();
                String Nuevo_contra = NuevaContraEt.getText().toString().trim();

                //Condiciones
                if (TextUtils.isEmpty(Actual_contra)) {
                    Toast.makeText(Cambio_contra.this, "El campo contraseña actual esta vacio", Toast.LENGTH_SHORT).show();
                }
                if (TextUtils.isEmpty(Nuevo_contra)) {
                    Toast.makeText(Cambio_contra.this, "El campo contraseña nueva esta vacio", Toast.LENGTH_SHORT).show();
                }

                if (!Actual_contra.equals("") && !Nuevo_contra.equals("") && Nuevo_contra.length() >= 6) {

                    Cambio_Contra(Actual_contra, Nuevo_contra);
                } else {
                    NuevaContraEt.setError("La contraseña debe tener minimo seis caracteres");
                    NuevaContraEt.setFocusable(true);
                }
            }
        });

        IrInicioBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Cambio_contra.this, MainActivityAdministrador.class));
            }
        });

    }

    private void Cambio_Contra(String contra_actual, String nueva_contra) {

        progressDialog.show();
        progressDialog.setTitle("Actualizando");
        progressDialog.setMessage("Espere por favor");


        AuthCredential authCredential = EmailAuthProvider.getCredential(user.getEmail(), contra_actual);
        user.reauthenticate(authCredential)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        user.updatePassword(nueva_contra)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        progressDialog.dismiss();
                                        String NUEVO_PASS = NuevaContraEt.getText().toString().trim();
                                        //Enviar datos que despues son recuperados
                                        HashMap<String, Object> resultado = new HashMap<>();

                                        resultado.put("PASSWORD", NUEVO_PASS);

                                        //Acctualizar la contra en la bd

                                        BASE_DE_DATOS_ADMINISTRADORES.child(user.getUid()).updateChildren(resultado)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void unused) {
                                                        Toast.makeText(Cambio_contra.this, "Contraseña cambiada", Toast.LENGTH_SHORT).show();
                                                        //Cerrar sesion
                                                        firebaseAuth.signOut();
                                                        startActivity(new Intent(Cambio_contra.this, InicioSesion.class));
                                                        finish();
                                                    }
                                                }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(Cambio_contra.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                                progressDialog.dismiss();
                                            }
                                        });


                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Cambio_contra.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Cambio_contra.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });

    }

    @Override
    public void onBackPressed() {

    }
}