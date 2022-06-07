package com.example.test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.test.FragmentosAdministrador.InicioAdmin;
import com.example.test.FragmentosAdministrador.ListaAdmin;
import com.example.test.FragmentosAdministrador.PerfilAdmin;
import com.example.test.FragmentosAdministrador.RegistroAdmin;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivityAdministrador extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
   FirebaseAuth firebaseAuth;
   FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_administrador);

        Toolbar toolbar  = findViewById(R.id.toolbarA);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");



        drawerLayout = findViewById(R.id.drawer_layout_A);

        NavigationView navigationView = findViewById(R.id.nav_view_A);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout , toolbar ,
                R.string.navigation_drawer_open , R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        firebaseAuth = FirebaseAuth.getInstance();
        //OBTENER EL ADMINISTRADOR ACTUAL
        user = firebaseAuth.getCurrentUser();

        //METODO PARA IDENTIFICAR EL INCIO DE SESION


        //FRAGMENTO POR DEFECTO

        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_A,
                    new InicioAdmin()).commit();
            navigationView.setCheckedItem(R.id.InicioAdmin);
        }


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.InicioAdmin:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_A,
                        new InicioAdmin()).commit();
                break;

            case R.id.PerfilAdmin:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_A,
                        new PerfilAdmin()).commit();
                break;

            case R.id.RegistrarAdmin:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_A,
                        new RegistroAdmin()).commit();
                break;

            case R.id.ListarAdmin:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_A,
                        new ListaAdmin()).commit();
                break;

            case R.id.SalirAdmin:
               CerrarSesion();
                break;

        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    //METODO PARA IDENTIFICAR EL INCIO DE SESION

    private void ComprobandoInicioSesion(){
        if(user!=null){
            //SI EL ADMINISTRADOR HA INICIADO SESION 
            Toast.makeText(this, "Se ha iniciado sesion ", Toast.LENGTH_SHORT).show();
        }else{
            //Si no se ha iciado sesion
            startActivity(new Intent(MainActivityAdministrador.this, MainActivityCliente.class));
            finish();
        }
    }

    private  void CerrarSesion(){
        firebaseAuth.signOut();
        startActivity(new Intent(MainActivityAdministrador.this,MainActivityCliente.class));
        Toast.makeText(this, "Cerraste sesion exitosamente", Toast.LENGTH_SHORT).show();
    }


    //APENAS INICIE SESION SE EJECUATARA EL METODO COMPROBANDO...
    @Override
    protected void onStart() {
        ComprobandoInicioSesion();
        super.onStart();
    }
}



