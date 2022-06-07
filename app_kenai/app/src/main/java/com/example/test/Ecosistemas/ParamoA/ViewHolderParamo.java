package com.example.test.Ecosistemas.ParamoA;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.R;
import com.squareup.picasso.Picasso;

public class ViewHolderParamo extends RecyclerView.ViewHolder {

    View mview;

    private ViewHolderParamo.ClickListener mClickListener;

    public interface ClickListener {
        //ADMIN PRESIONA NORMAL EL ITEM
        void OnItemLongClick(View view, int position); //ADMIN MANTIENE PRESIONADO EL ITEM

        void onItemClick(View view, int position);
    }

    //METODO PARA PODER PRESIONAR O MATENER PRESIONADO UN ITEM
    public void setOnClickListener(ViewHolderParamo.ClickListener clickListener) {
        mClickListener = clickListener;

    }

    public ViewHolderParamo(@NonNull View itemView) {
        super(itemView);
        mview = itemView;

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClickListener.onItemClick(view, getBindingAdapterPosition());
            }
        });

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                mClickListener.OnItemLongClick(view, getBindingAdapterPosition());
                return true;
            }
        });
    }


    public void SeteoPeliculas(Context context, String nombre, String descripcion1,
                               String descripcion2, String descripcion3, String imagen, String imagen2) {

        ImageView ImagenParamo;
        TextView NombreImagenPelicula;

        //CONEXION CON EL ITEM

        ImagenParamo = mview.findViewById(R.id.ImagenParamo);
        NombreImagenPelicula = mview.findViewById(R.id.NombreImagenPelicula);

        //SETEAR LOS DATOS

        NombreImagenPelicula.setText(nombre);


        //CONTROLAR POSIBLES ERRORES
        try {
            Picasso.get().load(imagen).placeholder(R.drawable.oso_def).into(ImagenParamo);
        } catch (Exception e) {

            Picasso.get().load(R.drawable.oso_def).into(ImagenParamo);

        }

    }


}
