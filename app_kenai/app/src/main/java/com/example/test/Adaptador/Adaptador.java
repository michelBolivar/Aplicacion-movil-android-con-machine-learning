package com.example.test.Adaptador;

import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.Detalle.Detalle_Administrador;
import com.example.test.Modelo.Administrador;
import com.example.test.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Adaptador extends RecyclerView.Adapter<Adaptador.MyHolder> {

    private Context context;
    private List<Administrador> administradores;

    public Adaptador(Context context, List<Administrador> administradores) {
        this.context = context;
        this.administradores = administradores;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //INFLAR EL ADMIN_LAYOUT
        View view = LayoutInflater.from(context).inflate(R.layout.admin_item, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        //OBETENEMOS LOS DATOS DEL MODELO
        String UID = administradores.get(position).getUID();
        String IMAGEN = administradores.get(position).getIMAGEN();
        String NOMBRES = administradores.get(position).getNOMBRES();
        String CORREO = administradores.get(position).getCORREO();
        String ROL = administradores.get(position).getROL();

        //SETEO DE DATOS
        holder.NombresAdmin.setText(NOMBRES);
        holder.RolAdmin.setText(ROL);

        try {

            //SI EXUIXTE LA IMAGEN
            Picasso.get().load(IMAGEN).placeholder(R.drawable.bear_admin).into(holder.PerfilAdmin);

        } catch (Exception e) {

            Picasso.get().load(R.drawable.bear_admin).into(holder.PerfilAdmin);

        }

        //AL HACER CLIC EN UN ADMINISTRADOR
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Detalle_Administrador.class);

                //PASAR LOS DATOS A LA SIGUIENTE ACTIVIDAD
                intent.putExtra("UID", UID);
                intent.putExtra("ROL", ROL);
                intent.putExtra("NOMBRES", NOMBRES);
                intent.putExtra("CORREO", CORREO);
                intent.putExtra("IMAGEN", IMAGEN);

                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return administradores.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        //DECLARAMOS LAS VISTAS

        CircleImageView PerfilAdmin;
        TextView NombresAdmin, RolAdmin;


        public MyHolder(@NonNull View itemView) {
            super(itemView);

            PerfilAdmin = itemView.findViewById(R.id.PerfilAdmin);
            NombresAdmin = itemView.findViewById(R.id.NombresAdmin);
            RolAdmin = itemView.findViewById(R.id.RolAdmin);
        }
    }
}
