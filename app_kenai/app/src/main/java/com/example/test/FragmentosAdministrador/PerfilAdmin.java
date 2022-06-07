package com.example.test.FragmentosAdministrador;


import static com.google.firebase.storage.FirebaseStorage.getInstance;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.renderscript.ScriptGroup;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test.MainActivityAdministrador;
import com.example.test.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Objects;


public class PerfilAdmin extends Fragment {

    FirebaseAuth firebaseAuth;
    FirebaseUser user;

    //para leer informacion de la base de datos

    DatabaseReference BASE_DE_DATOS_ADMINISTRADORES;

    StorageReference storageReference;
    String RutaDeAlmacenamiento = "Fotos_Perfil_Administradores/*";

    //Solicitudes camara

    private static final int CODIGO_DE_SOLICITUD_CAMARA = 100;
    private static final int CODIGO_CAMARA_SELECCION_DE_IMAGENES = 200;

    private static final int RESULT_OK = -1;


    //Solicitudes galeria
    private static final int CODIGO_DE_SOLICITUD_DE_ALMACENAMIENTO = 300;
    private static final int CODIGO_GALERIA_SELECCION_IMAGEN = 400;


    //Permisos a solicitar

    private String[] permisos_de_camara;
    private String[] permisos_de_almacenamiento;

    private Uri imagen_uri;
    private String imagen_perfil;
    private ProgressDialog progressDialog;


    //Vistas
    ImageView FotoPefilImg;
    TextView RolPerfil, NombrePerfil, CorreoPerfil;
    Button ActualizarContra, ActualizarDatos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_perfil_admin, container, false);

        FotoPefilImg = view.findViewById(R.id.FotoPefilImg);
        RolPerfil = view.findViewById(R.id.RolPerfil);
        NombrePerfil = view.findViewById(R.id.NombrePerfil);

        CorreoPerfil = view.findViewById(R.id.CorreoPerfil);
       // ContraPerfill = view.findViewById(R.id.ContraPerfil);

        ActualizarContra = view.findViewById(R.id.ActualizarContra);
        ActualizarDatos = view.findViewById(R.id.ActualizarDatos);

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        storageReference = getInstance().getReference();

        //INICIALIZAR LOS PERMISOS

        permisos_de_camara = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};

        permisos_de_almacenamiento = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        progressDialog = new ProgressDialog(getActivity());

        BASE_DE_DATOS_ADMINISTRADORES = FirebaseDatabase.getInstance().getReference("BASE DE DATOS ADMINISTRADORES");
        BASE_DE_DATOS_ADMINISTRADORES.child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {
                    //OBTENER DATOS

//                    String uid = ""+snapshot.child("UID").getValue();
                    String nombre = "" + snapshot.child("NOMBRES").getValue();
                    String correo = "" + snapshot.child("CORREO").getValue();
                    String contra = "" + snapshot.child("PASSWORD").getValue();
                    String rol = "" + snapshot.child("ROL").getValue();
                    String img = "" + snapshot.child("IMAGEN").getValue();


                    //Seteamos
//                    UIDPerfil.setText(uid);
                    RolPerfil.setText(rol);
                    NombrePerfil.setText(nombre);
                    CorreoPerfil.setText(correo);
                   // ContraPerfill.setText(contra);

                    try {
                        //Si existe la imagen
                        Picasso.get().load(img).placeholder(R.drawable.perfil_ico).into(FotoPefilImg);
                    } catch (Exception e) {

//                        Picasso.get().load(R.drawable.perfil_ico).into(FotoPefilImg);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        //Nos dirige a la actividad  cambiar contra
        ActualizarContra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), Cambio_contra.class));
                getActivity().finish();
            }
        });

        ActualizarDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditarDatos();
            }
        });

        FotoPefilImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CambiarImagenPerfilAdministrador();
            }
        });


        return view;
    }

    private void EditarDatos() {

        String[] opciones = {"Editar Nombres", "Editar Rol", "Editar Correo"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Elegir opción: ");
        builder.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i == 0) {
                    //EDITAR NOMBRES
                    EditarNombres();
                } else if (i == 1) {
                    //Editar Rol
                    EditarRol();
                } else if (i == 2) {

                    //EDITAR CORREO
                    EditarCorreo();

                }
            }
        });
        builder.create().show();
    }

    private void EditarCorreo() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Actualizar Información: ");
        LinearLayoutCompat linearLayoutCompat = new LinearLayoutCompat(getActivity());
        linearLayoutCompat.setOrientation(LinearLayoutCompat.VERTICAL);
        linearLayoutCompat.setPadding(10, 10, 10, 10);
        EditText editText = new EditText(getActivity());
        editText.setHint("Ingrese nuevo dato ...");
        editText.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS | InputType.TYPE_TEXT_FLAG_AUTO_CORRECT);
        linearLayoutCompat.addView(editText);
        builder.setView(linearLayoutCompat);
        builder.setPositiveButton("Actualizar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String nuevoDato = editText.getText().toString().trim();
                if (!nuevoDato.equals("")) {
                    HashMap<String, Object> resultado = new HashMap<>();
                    resultado.put("CORREO", nuevoDato);
                    BASE_DE_DATOS_ADMINISTRADORES.child(user.getUid()).updateChildren(resultado)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(getActivity(), "Dato Actualizado", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                } else {
                    Toast.makeText(getActivity(), "Campo Vacio", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getActivity(), "Cancelado por el Usuario", Toast.LENGTH_SHORT).show();
            }
        });
        builder.create().show();
    }

    private void EditarRol() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Actualizar Información: ");
        LinearLayoutCompat linearLayoutCompat = new LinearLayoutCompat(getActivity());
        linearLayoutCompat.setOrientation(LinearLayoutCompat.VERTICAL);
        linearLayoutCompat.setPadding(10, 10, 10, 10);
        EditText editText = new EditText(getActivity());
        editText.setHint("Ingrese nuevo dato ...");
        editText.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS | InputType.TYPE_TEXT_FLAG_AUTO_CORRECT);
        linearLayoutCompat.addView(editText);
        builder.setView(linearLayoutCompat);
        builder.setPositiveButton("Actualizar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String nuevoDato = editText.getText().toString().trim();
                if (!nuevoDato.equals("")) {
                    HashMap<String, Object> resultado = new HashMap<>();
                    resultado.put("ROL", nuevoDato);
                    BASE_DE_DATOS_ADMINISTRADORES.child(user.getUid()).updateChildren(resultado)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(getActivity(), "Dato Actualizado", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                } else {
                    Toast.makeText(getActivity(), "Campo Vacio", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getActivity(), "Cancelado por el Usuario", Toast.LENGTH_SHORT).show();
            }
        });
        builder.create().show();
    }

    private void EditarNombres() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Actualizar Información: ");
        LinearLayoutCompat linearLayoutCompat = new LinearLayoutCompat(getActivity());
        linearLayoutCompat.setOrientation(LinearLayoutCompat.VERTICAL);
        linearLayoutCompat.setPadding(10, 10, 10, 10);
        EditText editText = new EditText(getActivity());
        editText.setHint("Ingrese nuevo dato ...");
        editText.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS | InputType.TYPE_TEXT_FLAG_AUTO_CORRECT);
        linearLayoutCompat.addView(editText);
        builder.setView(linearLayoutCompat);
        builder.setPositiveButton("Actualizar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String nuevoDato = editText.getText().toString().trim();
                if (!nuevoDato.equals("")) {
                    HashMap<String, Object> resultado = new HashMap<>();
                    resultado.put("NOMBRES", nuevoDato);
                    BASE_DE_DATOS_ADMINISTRADORES.child(user.getUid()).updateChildren(resultado)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(getActivity(), "Dato Actualizado", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                } else {
                    Toast.makeText(getActivity(), "Campo Vacio", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getActivity(), "Cancelado por el Usuario", Toast.LENGTH_SHORT).show();
            }
        });
        builder.create().show();
    }

    //Alter dialog para  editar imagen
    private void CambiarImagenPerfilAdministrador() {

        String[] opcion = {"Cambiar foto de Perfil"};

        //Crear un alertdialogo
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //Asignamos un titulo

        builder.setTitle("Elegir una opcion");
        builder.setItems(opcion, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if (i == 0) {
                    imagen_perfil = "IMAGEN";
                    ElegirFoto();

                }
            }
        });
        builder.create().show();


    }

    //Comprobar si los permisos de almacenamiento estan habiltados
    private boolean Comprobar_permisos_almacenamiento() {
        boolean resultado_uno = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == (PackageManager.PERMISSION_GRANTED);
        return resultado_uno;
    }

    private void Solicitar_peermisos_almacenamiento() {
        requestPermissions(permisos_de_almacenamiento, CODIGO_DE_SOLICITUD_DE_ALMACENAMIENTO);
    }

    //Comprobar si los permisos de camara estan habilitados
    private boolean comprobar_los_permisos_de_la_camara() {
        boolean resultado_uno = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                == (PackageManager.PERMISSION_GRANTED);

        boolean resultado_dos = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == (PackageManager.PERMISSION_GRANTED);

        return resultado_uno && resultado_dos;

    }

    private void Solicitar_permisos_de_camara() {
        requestPermissions(permisos_de_camara, CODIGO_DE_SOLICITUD_CAMARA);
    }

    //Elegir de donde procede la imagen
    private void ElegirFoto() {
        String[] opciones = {"Cámara", "Galeria"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Seleccionar imagen de : ");
        builder.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i == 0) {
                    //Se va a verificar si el permiso ya esta habilitado
                    if (!comprobar_los_permisos_de_la_camara()) {
                        Solicitar_permisos_de_camara();
                    } else {
                        Elegir_de_camara();
                    }
                } else if (i == 1) {
                    if (!Comprobar_permisos_almacenamiento()) {
                        Solicitar_peermisos_almacenamiento();
                    } else {
                        Elegir_de_galeria();
                    }
                }
            }
        });

        //Prara mostrar el alert dialogo
        builder.create().show();
    }

    private void Elegir_de_galeria() {
        Intent GaleriaIntent = new Intent(Intent.ACTION_PICK);
        GaleriaIntent.setType("image/*");
        startActivityForResult(GaleriaIntent, CODIGO_GALERIA_SELECCION_IMAGEN);
    }

    //Metodo abrir camara
    private void Elegir_de_camara() {

        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "Foto temporal");
        values.put(MediaStore.Images.Media.DESCRIPTION, "Descripcion temporal");
        imagen_uri = (Objects.requireNonNull(getActivity())).getContentResolver()
                .insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        //ACTIVIDAD PARA ABRIR LA CAMARA

        Intent camaraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        camaraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imagen_uri);
        startActivityForResult(camaraIntent, CODIGO_CAMARA_SELECCION_DE_IMAGENES);
    }

    private void ActualizarIMagenEnBd(Uri uri) {
        String Ruta_de_archivo_y_nombre = RutaDeAlmacenamiento + "" + imagen_perfil + "_" + user.getUid();
        StorageReference storageReference2 = storageReference.child(Ruta_de_archivo_y_nombre);
        storageReference2.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isSuccessful()) ;
                Uri downloadUri = uriTask.getResult();

                if (uriTask.isSuccessful()) {
                    //Enviar datos
                    HashMap<String, Object> results = new HashMap<>();

                    results.put(imagen_perfil, downloadUri.toString());
                    BASE_DE_DATOS_ADMINISTRADORES.child(user.getUid()).updateChildren(results)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    startActivity(new Intent(getActivity(), MainActivityAdministrador.class));
                                    getActivity().finish();
                                    Toast.makeText(getActivity(), "Imagen cambiada con exito", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getActivity(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                } else {
                    Toast.makeText(getActivity(), "Ha ocriido un error ", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Metodo se llamara despues de elegir la imagen de la camara de la galeria
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == CODIGO_CAMARA_SELECCION_DE_IMAGENES) {
                ActualizarIMagenEnBd(imagen_uri);
                progressDialog.setTitle("Procesando");
                progressDialog.setMessage("La imagen se esta cambiando , espere porfavor ...");
                progressDialog.setCancelable(false);
                progressDialog.show();
            }
            if (requestCode == CODIGO_GALERIA_SELECCION_IMAGEN) {
                imagen_uri = data.getData();

                ActualizarIMagenEnBd(imagen_uri);
                progressDialog.setTitle("Procesando");
                progressDialog.setMessage("La imagen se esta cambiando , espere porfavor ...");
                progressDialog.setCancelable(false);
                progressDialog.show();
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    //Resultados de los permisos de solicitud
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case CODIGO_DE_SOLICITUD_CAMARA: {
                if (grantResults.length > 0) {
                    boolean CamaraAceptada = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean EscriirAlmacenamiento_Aceptada = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if (CamaraAceptada && EscriirAlmacenamiento_Aceptada) {
                        Elegir_de_camara();
                    } else {
                        Toast.makeText(getActivity(), "Por favor acepte los permisos ", Toast.LENGTH_SHORT).show();
                    }
                }

            }
            break;

            case CODIGO_DE_SOLICITUD_DE_ALMACENAMIENTO: {
                if (grantResults.length > 0) {
                    boolean EscriirAlmacenamiento_Aceptada = grantResults[0] == PackageManager.PERMISSION_GRANTED;

                    if (EscriirAlmacenamiento_Aceptada) {
                        Elegir_de_galeria();
                    } else {
                        Toast.makeText(getActivity(), "Por favor acepte los permisos ", Toast.LENGTH_SHORT).show();
                    }

                }

            }
            break;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}