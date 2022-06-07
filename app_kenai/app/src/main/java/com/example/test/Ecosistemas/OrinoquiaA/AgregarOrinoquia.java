package com.example.test.Ecosistemas.OrinoquiaA;

import static com.google.firebase.storage.FirebaseStorage.getInstance;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.test.FragmentosAdministrador.Cambio_contra;
import com.example.test.MainActivityAdministrador;
import com.example.test.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import static com.google.firebase.storage.FirebaseStorage.getInstance;

import java.io.ByteArrayOutputStream;

public class AgregarOrinoquia extends AppCompatActivity {

    EditText NombreAnimal, DescripcionAnimal1;
    ImageView ImagenAgregarParamo, ImagenAgregarParamo2;
    Button PublicarAnimal , botoncito;

    String RutaDeAlmacenamiento = "Orinoquia_Subida/";
    String RutaDeBaseDatos = "ORINOQUIA";
    Uri RutaArchivoUri;
    Uri RutaArchivoUri2;

    StorageReference mStorageReference;
    DatabaseReference dataBaseReference;

    ProgressDialog progressDialog;

    String rNombre, rImagen, rDescrip1, rDescrip2, rDescrip3, rImagen2;

    int CODIGO_SOLICITUD_IMAGEN = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_orinoquia);



        NombreAnimal = findViewById(R.id.NombreAnimal);
        DescripcionAnimal1 = findViewById(R.id.DescripcionAnimal);
       // DescripcionAnimal2 = findViewById(R.id.DescripcionAnimal2);
       // DescripcionAnimal3 = findViewById(R.id.DescripcionAnimal3);
        ImagenAgregarParamo = findViewById(R.id.ImagenAgregarOrino);
       // ImagenAgregarParamo2 = findViewById(R.id.ImagenAgregarOrino2);
        PublicarAnimal = findViewById(R.id.PublicarAnimal);

        mStorageReference = FirebaseStorage.getInstance().getReference();
        dataBaseReference = FirebaseDatabase.getInstance().getReference(RutaDeBaseDatos);

        progressDialog = new ProgressDialog(AgregarOrinoquia.this);

        botoncito = findViewById(R.id.botoncito);



        Bundle intent = getIntent().getExtras();
        if (intent != null) {
            //RECUPERAR LOS DATOS DE LA ACTIVIDAD ANTERIOR
            rNombre = intent.getString("NombreEnviado");
            rImagen = intent.getString("ImagenEnviada");
            rDescrip1 = intent.getString("Descrip1");
            //rDescrip2 = intent.getString("Descrip2");
           // rDescrip3 = intent.getString("Descrip3");
           // rImagen2 = intent.getString("ImagenEnviada2");

            //SETEAR

            NombreAnimal.setText(rNombre);
            DescripcionAnimal1.setText(rDescrip1);
            //DescripcionAnimal2.setText(rDescrip2);
           // DescripcionAnimal3.setText(rDescrip3);


            Picasso.get().load(rImagen).into(ImagenAgregarParamo);
           // Picasso.get().load(rImagen2).into(ImagenAgregarParamo2);

            //CAMBIAR NOMBRE EN ACTION BAR

//            getActionBar().setTitle("Actualizar");
            String actualizar = "Actualizar";

            //CAMBIAR EL NOMBRE DEL BOTON

            PublicarAnimal.setText(actualizar);

        }

        ImagenAgregarParamo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Seleccionar imagen"), CODIGO_SOLICITUD_IMAGEN);
            }
        });

        PublicarAnimal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (PublicarAnimal.getText().equals("Publicar")) {
                    SubirImagen();
                } else {
                    EmpezarActualizacion();

                }

            }
        });

        botoncito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AgregarOrinoquia.this, MainActivityAdministrador.class));
            }
        });


    }

    private void EmpezarActualizacion() {
        progressDialog.setTitle("Actualizando");
        progressDialog.setMessage("Espere por favor ...");
        progressDialog.show();
        progressDialog.setCancelable(false);
        EliminarImagenAnterior();
    }

    private void EliminarImagenAnterior() {

        StorageReference Imagen = getInstance().getReferenceFromUrl(rImagen);
        //StorageReference Imagen2 = getInstance().getReferenceFromUrl(rImagen2);

        Imagen.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                //Toast.makeText(AgregarOrinoquia.this, "La imagen anterior a sido eliminada", Toast.LENGTH_SHORT).show();
                SubirNuevaImagen();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(AgregarOrinoquia.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    private void SubirNuevaImagen() {
        String nuevaImagen = System.currentTimeMillis() + ".png";
        StorageReference mStorageReference2 = mStorageReference.child(RutaDeAlmacenamiento + nuevaImagen);

        Bitmap bitmap = ((BitmapDrawable) ImagenAgregarParamo.getDrawable()).getBitmap();
        ByteArrayOutputStream byteArrayInputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayInputStream);
        byte[] data = byteArrayInputStream.toByteArray();
        UploadTask uploadTask = mStorageReference2.putBytes(data);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(AgregarOrinoquia.this, "Nueva Imagen Cargada", Toast.LENGTH_SHORT).show();
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isSuccessful()) ;
                Uri downloaduri = uriTask.getResult();
                ActualizarImagenBd(downloaduri.toString());


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AgregarOrinoquia.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });

    }

    private void ActualizarImagenBd(String NuevaImagen) {
        final String nombreActualizar = NombreAnimal.getText().toString();
        final String dsc1Actualizar = DescripcionAnimal1.getText().toString();
       // final String dsc2Actualizar = DescripcionAnimal2.getText().toString();
       // final String dsc3Actualizar = DescripcionAnimal3.getText().toString();

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("ORINOQUIA");

        Query query = databaseReference.orderByChild("nombre").equalTo(rNombre);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //DATOS PARA ACTUALIZAR
                for (DataSnapshot ds : snapshot.getChildren()) {
                    ds.getRef().child("nombre").setValue(nombreActualizar);
                    ds.getRef().child("imagen").setValue(NuevaImagen);
                    ds.getRef().child("descripcion1").setValue(dsc1Actualizar);
                    //ds.getRef().child("descripcion2").setValue(dsc2Actualizar);
                  //  ds.getRef().child("descripcion3").setValue(dsc3Actualizar);
                }
                progressDialog.dismiss();
                Toast.makeText(AgregarOrinoquia.this, "Actualizado correctamente", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(AgregarOrinoquia.this, OrinoquiaA.class));
                finish();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void SubirImagen() {
        if (RutaArchivoUri != null && RutaArchivoUri2 != null) {
            progressDialog.setTitle("Espere por favor");
            progressDialog.setMessage("Subiendo imagen del Animal ...");
            progressDialog.show();
            progressDialog.setCancelable(false);
            StorageReference storageReference2 = mStorageReference.child(RutaDeAlmacenamiento + System.currentTimeMillis() + "."
                    + ObtenerExtensionDelArchivo(RutaArchivoUri));

            StorageReference storageReference3 = mStorageReference.child(RutaDeAlmacenamiento + System.currentTimeMillis() + "."
                    + ObtenerExtensionDelArchivo(RutaArchivoUri2));

            storageReference2.putFile(RutaArchivoUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();

                            while (!uriTask.isSuccessful()) ;
                            Uri downloadURI = uriTask.getResult();

                            String mNombre = NombreAnimal.getText().toString();
                            String mDescripcion = DescripcionAnimal1.getText().toString();
                          //  String mDescripcion2 = DescripcionAnimal2.getText().toString();
                            //String mDescripcion3 = DescripcionAnimal3.getText().toString();

                            Orinoquia orinoquia = new Orinoquia(downloadURI.toString(), "", mNombre, mDescripcion, "", "");

                            String ID_IMAGEN;
                            ID_IMAGEN = dataBaseReference.push().getKey();

                            dataBaseReference.child(ID_IMAGEN).setValue(orinoquia);

                            progressDialog.dismiss();
                            Toast.makeText(AgregarOrinoquia.this, "Subido Exitosamente", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(AgregarOrinoquia.this, OrinoquiaA.class));
                            finish();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(AgregarOrinoquia.this, "No se pudo"+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

                    progressDialog.setTitle("Publicando ...");
                    progressDialog.setCancelable(false);
                }
            });
        } else {
            Toast.makeText(this, "Debe asignar una imagen", Toast.LENGTH_SHORT).show();
        }
    }

    private String ObtenerExtensionDelArchivo(Uri uri) {

        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CODIGO_SOLICITUD_IMAGEN
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {

            RutaArchivoUri = data.getData();
            RutaArchivoUri2 = data.getData();
            try {

                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), RutaArchivoUri);
                ImagenAgregarParamo.setImageBitmap(bitmap);



            } catch (Exception e) {

                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }
    }
}