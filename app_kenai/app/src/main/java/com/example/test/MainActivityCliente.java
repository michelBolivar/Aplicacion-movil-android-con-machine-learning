package com.example.test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.test.CategoriasCliente.ParamoCli;
import com.example.test.FragmentosAdministrador.InicioAdmin;
import com.example.test.FragmentosAdministrador.ListaAdmin;
import com.example.test.FragmentosAdministrador.PerfilAdmin;
import com.example.test.FragmentosAdministrador.RegistroAdmin;
import com.example.test.FragmentosCliente.Acceder;
import com.example.test.FragmentosCliente.Compartir;
import com.example.test.FragmentosCliente.Contactenos;
import com.example.test.FragmentosCliente.InfoApp;
import com.example.test.FragmentosCliente.Inicio;
import com.example.test.FragmentosCliente.SobreNosotros;
import com.example.test.FragmentosCliente.infoUso;
import com.google.android.material.navigation.NavigationView;

public class MainActivityCliente extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {

    DrawerLayout drawerLayout;

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(MainActivityCliente.this, MainActivityCliente.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_cliente);


        //BOTON DE REGRESO
        Toolbar mytoolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mytoolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        getSupportActionBar().setTitle("");


        drawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout , mytoolbar ,
                R.string.navigation_drawer_open , R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        //FRAGMENTO POR DEFECTO

        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new Inicio()).commit();
            navigationView.setCheckedItem(R.id.InicioCliente);
        }

        try{
            Retorno();
        }catch (Exception e){
            //Toast.makeText(this, ""+e, Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.InicioCliente:
               /* Intent intent2 = new Intent(MainActivityCliente.this,MainActivityCliente.class);
                intent2.putExtra("inicio","Reinicio");
                startActivity(intent2);
                finish();*/
               /* getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Inicio()).commit();*/



                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Inicio()).commit();


                break;
            case R.id.sobreNosotros:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new SobreNosotros()).commit();
                break;

            case R.id.infoUso:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new infoUso()).commit();
                break;


            case R.id.contacto:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Contactenos()).commit();
                break;
            case R.id.infoApp:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new InfoApp()).commit();
                break;

            case R.id.Admin:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Acceder()).commit();
                break;

            case R.id.Escaner:
                Intent intent = new Intent(MainActivityCliente.this,DetectorActivity.class);
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                //intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                break;

        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public  void Retorno(){
        String inicio = getIntent().getExtras().getString("inicio");
       /*if(inicio.equals("Reinicio")){
            Intent intent = new Intent(MainActivityCliente.this, MainActivityCliente.class);
            startActivity(intent);
            finish();
        }else{

        }*/
       // Toast.makeText(this, "esto es :"+ inicio, Toast.LENGTH_SHORT).show();
    }
}