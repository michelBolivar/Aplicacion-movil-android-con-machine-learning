package com.example.test.FragmentosAdministrador;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test.MainActivityAdministrador;
import com.example.test.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.regex.Pattern;


public class RegistroAdmin extends Fragment {
         TextView fechaRegistro;
         EditText correo , password, nombres , rol , codiVerifi;
         Button registrar;

         FirebaseAuth auth;

         ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=  inflater.inflate(R.layout.fragment_registro_admin, container, false);

        //fechaRegistro = view.findViewById(R.id.FechaRegistro);
        correo = view.findViewById(R.id.Correo);
        password = view.findViewById(R.id.contra);
        nombres = view.findViewById(R.id.Nombre);

        rol =  view.findViewById(R.id.rol);
        codiVerifi = view.findViewById(R.id.codigoVerifica);

        registrar = view.findViewById(R.id.RegistrarUsu);

        auth = FirebaseAuth.getInstance(); //INICIALIZANDO FIREBASE AUTENTICATION

        Date date = new Date();
        SimpleDateFormat fecha  = new SimpleDateFormat("d 'de' MMM 'del' yyyy");
        String SFecha = fecha.format(date); //convertimos la fecha a string
        //fechaRegistro.setText(SFecha);

        //Al DARLE CLIC EN REGISTRAR

        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Convertimos  a string EDITTEXT correo y contra
                String corre= correo.getText().toString();
                String pass = password.getText().toString();
                String nom = nombres.getText().toString();

                String ro = rol.getText().toString();
                String codVer = codiVerifi.getText().toString();

                if(correo.equals("") || password.equals("") || nom.equals("") ||
                         ro.equals("") || codVer.equals(""))
                {
                    Toast.makeText(getActivity(), "Por favor llenar todos los campos", Toast.LENGTH_SHORT).show();
                }else{

                    //VALIDACION  DEL CORREO
                    if(!Patterns.EMAIL_ADDRESS.matcher(corre).matches()){
                        correo.setError("Correo Invalido");
                        correo.setFocusable(true);
                    }
                    else if (pass.length()<6){
                        password.setError("Contraseña debe tener al menos 6 caracteres");
                        password.setFocusable(true);
                    }
                    if(codVer.equals("kenai")) RegistroAadministradores(corre, pass);
                    else{
                        Toast.makeText(getActivity(), "El codigo es invalido", Toast.LENGTH_SHORT).show();
                    }
//                else{
//                    RegistroAadministradores(corre,pass);
//                }
                }
            }
        });

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Registando , espere por favor");
        progressDialog.setCancelable(false);

        return view;
    }

    //METODO PARA REGISTRAR ADMINISTRADORES
    private void RegistroAadministradores(String correito, String pass) {

        progressDialog.show();
        auth.createUserWithEmailAndPassword(correito,pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //EL ADMINISRADOR FUE CREADO CORRECTAMENTE
                        if(task.isSuccessful()){
                             progressDialog.dismiss();
                            FirebaseUser user = auth.getCurrentUser();
                            assert user != null; //AFIRMAR QUE EL ADMIN NO ES NULO
                            //CONVERTIR A CADENA LOS DATOS DE LOS ADMINISTRADORES

                            String UID = user.getUid();
                            String cor = correo.getText().toString();
                            String pas = password.getText().toString();
                            String nom = nombres.getText().toString();

                            String ro = rol.getText().toString();

                            HashMap<Object , Object> Administradores = new HashMap<>();

                            Administradores.put("UID",UID);
                            Administradores.put("CORREO", cor);
                            Administradores.put("PASSWORD",pas);
                            Administradores.put("NOMBRES", nom);

                            Administradores.put("ROL",ro);
                            Administradores.put("IMAGEN","");

                            //INICIALIZAR FIREBASEDATABASE
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference reference = database.getReference("BASE DE DATOS ADMINISTRADORES");
                            reference.child(UID).setValue(Administradores);
                            startActivity(new Intent(getActivity(), MainActivityAdministrador.class));
                            Toast.makeText(getActivity(), "Registro Exitoso", Toast.LENGTH_SHORT).show();
                            getActivity().finish();
                        }else{
                            progressDialog.dismiss();
                            Toast.makeText(getActivity(), "Ha ocurrido un error", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}