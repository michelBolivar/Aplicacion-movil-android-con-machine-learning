package com.example.test.Ecosistemas.OrinoquiaA;


import static com.google.firebase.storage.FirebaseStorage.getInstance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import androidx.appcompat.widget.Toolbar;

import androidx.core.widget.ImageViewCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;


import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;


import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


public class OrinoquiaA extends AppCompatActivity {

    RecyclerView recyclerViewOrinoquia;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mRef;

    FirebaseRecyclerAdapter<Orinoquia, ViewHolderOrinoquia> firebaseRecyclerAdapter;
    FirebaseRecyclerOptions<Orinoquia> options;

    View agregar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orinoquia);

        //BOTON DE REGRESO
        Toolbar mytoolbar = (Toolbar) findViewById(R.id.toolbarAdmin1);
        setSupportActionBar(mytoolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");


        agregar = findViewById(R.id.AgregarOrinoco);

        recyclerViewOrinoquia = findViewById(R.id.recyclerViewOrinoquia);
        recyclerViewOrinoquia.setHasFixedSize(true);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mRef = mFirebaseDatabase.getReference("ORINOQUIA");

        ListarImagenesOrinoquia();

        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  Toast.makeText(OrinoquiaA.this, "Agregar :/", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(OrinoquiaA.this, AgregarOrinoquia.class));

            }
        });
    }

    private void ListarImagenesOrinoquia() {
        options = new FirebaseRecyclerOptions.Builder<Orinoquia>().setQuery(mRef,Orinoquia.class).build();
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Orinoquia, ViewHolderOrinoquia>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ViewHolderOrinoquia viewHolderOrinoquia, int i, @NonNull Orinoquia orinoquia) {
                viewHolderOrinoquia.SeteoPeliculas(
                        getApplicationContext(),
                        orinoquia.getNombre(),
                        orinoquia.getDescripcion1(),
                        orinoquia.getDescripcion2(),
                        orinoquia.getDescripcion3(),
                        orinoquia.getImagen(),
                        orinoquia.getImagen2()
                );
            }

            @NonNull
            @Override
            public ViewHolderOrinoquia onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_orinoquia,parent,false);
                ViewHolderOrinoquia viewHolderParamo = new ViewHolderOrinoquia(itemView);
                viewHolderParamo.setOnClickListener(new ViewHolderOrinoquia.ClickListener() {
                    @Override
                    public void OnItemLongClick(View view, int position) {
                        Toast.makeText(OrinoquiaA.this, "Animal", Toast.LENGTH_SHORT).show();
                    }



                    @Override
                    public void onItemClick(View view, int position) {
                        String nombre = getItem(position).getNombre();   //Traemos el nombre
                        String imagen = getItem(position).getImagen();
                        String imagen2 = getItem(position).getImagen2();
                        String descrip1 = getItem(position).getDescripcion1();
                        String descrip2= getItem(position).getDescripcion2();
                        String descrip3 = getItem(position).getDescripcion3();




                        AlertDialog.Builder builder = new AlertDialog.Builder(OrinoquiaA.this);

                        String [] opciones = {"Actualizar", "Eliminar"};
                        builder.setItems(opciones, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                if( i == 0){
                                    Intent intent = new Intent(OrinoquiaA.this, AgregarOrinoquia.class);
                                    intent.putExtra("NombreEnviado",nombre);
                                    intent.putExtra("ImagenEnviada",imagen);
                                    intent.putExtra("ImagenEnviada2",imagen2);
                                    intent.putExtra("Descrip1",descrip1);
                                    intent.putExtra("Descrip2",descrip2);
                                    intent.putExtra("Descrip3",descrip3);

                                    startActivity(intent);
                                }

                                if(i == 1){
                                    EliminarDatos(nombre,imagen);
                                }

                            }
                        });

                        builder.create().show();
                    }
                });
                return viewHolderParamo;
            }
        };
        recyclerViewOrinoquia.setLayoutManager(new GridLayoutManager(OrinoquiaA.this,2 ));
        firebaseRecyclerAdapter.startListening();
        recyclerViewOrinoquia.setAdapter(firebaseRecyclerAdapter);



    }

    private void  EliminarDatos(final String NombreActual , final String ImagenActual){
        AlertDialog.Builder builder = new AlertDialog.Builder(OrinoquiaA.this);
        builder.setTitle("Eliminar");
        builder.setMessage("¿Desea eliminar el animal?");

        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


                //Recorre uno por uno el nombre de los naimlaes en la base de datos
                Query query = mRef.orderByChild("nombre").equalTo(NombreActual);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        for(DataSnapshot ds : snapshot.getChildren()){
                            ds.getRef().removeValue();
                        }

                        Toast.makeText(OrinoquiaA.this, "El animal ha sido eliminado", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                        Toast.makeText(OrinoquiaA.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

                StorageReference ImagenSeleccionada  = getInstance().getReferenceFromUrl(ImagenActual);
                ImagenSeleccionada.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(OrinoquiaA.this, "Eiliminado", Toast.LENGTH_SHORT).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(OrinoquiaA.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                Toast.makeText(OrinoquiaA.this, "Cancelado ", Toast.LENGTH_SHORT).show();

            }
        });

        builder.create().show();   //Para mostrar el cuadro de dialogo
    }

    protected void onStart(){
        super.onStart();

        if(firebaseRecyclerAdapter!=null){
            firebaseRecyclerAdapter.startListening();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_agregar, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.Agregar:
                startActivity(new Intent(OrinoquiaA.this, AgregarOrinoquia.class));
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

}