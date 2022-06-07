package com.example.test.Catego.Cat_dispo;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.test.CategoriasCliente.ParamoCli;
import com.example.test.MainActivityCliente;
import com.example.test.R;
import com.squareup.picasso.Picasso;

public class ViewHolderDC  extends RecyclerView.ViewHolder {

    View mview;



    private ViewHolderDC.ClickListener mClickListener;

    public interface  ClickListener{
        //ADMIN PRESIONA NORMAL EL ITEM

        void onItemClick(View view, int position);
    }

    //METODO PARA PODER PRESIONAR O MATENER PRESIONADO UN ITEM
    public void setOnClickListener(ViewHolderDC.ClickListener clickListener){
        mClickListener = clickListener;

    }
    public ViewHolderDC(@NonNull View itemView) {
        super(itemView);
        mview = itemView;

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClickListener.onItemClick(view,getBindingAdapterPosition());
            }
        });

    }


    public void SeteoCategrias(Context context , String categoria , String imagen ){

        ImageView ImagenEcosistemaC;
        TextView NombreAnimalC;

        //CONEXION CON EL ITEM

        ImagenEcosistemaC = mview.findViewById(R.id.ImagenEcosistemaC);
        NombreAnimalC = mview.findViewById(R.id.NombreAnimalC);

        //SETEAR LOS DATOS
        NombreAnimalC.setText(categoria);


        //imagen por defecto
        //CONTROLAR POSIBLES ERRORES
        try {
            Picasso.get().load(imagen).placeholder(R.drawable.oso_def).into(ImagenEcosistemaC);
        }
        catch (Exception e){

         Picasso.get().load(R.drawable.oso_def).into(ImagenEcosistemaC);

        }


    }

}
