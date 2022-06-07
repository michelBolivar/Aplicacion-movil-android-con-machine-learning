package com.example.test.Ecosistemas.OrinoquiaA;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.R;
import com.squareup.picasso.Picasso;

public class ViewHolderOrinoquia extends RecyclerView.ViewHolder {

    View mview;

    private ViewHolderOrinoquia.ClickListener mClickListener;

    public interface  ClickListener{
        //ADMIN PRESIONA NORMAL EL ITEM
        void OnItemLongClick(View view , int position); //ADMIN MANTIENE PRESIONADO EL ITEM

        void onItemClick(View view, int position);
    }

    //METODO PARA PODER PRESIONAR O MATENER PRESIONADO UN ITEM
    public void setOnClickListener(ViewHolderOrinoquia.ClickListener clickListener){
        mClickListener = clickListener;

    }
    public ViewHolderOrinoquia(@NonNull View itemView) {
        super(itemView);
        mview = itemView;

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClickListener.onItemClick(view,getBindingAdapterPosition());
            }
        });

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                mClickListener.OnItemLongClick(view,getBindingAdapterPosition());
                return true;
            }
        });
    }


    public void SeteoPeliculas(Context context , String nombre , String descripcion1 ,
                               String descripcion2 , String descripcion3 , String imagen , String imagen2){

        ImageView ImagenOrinoquia;
        TextView NombreImagenOrinoquia;

        //CONEXION CON EL ITEM

        ImagenOrinoquia = mview.findViewById(R.id.ImagenOrinoquia);
        NombreImagenOrinoquia = mview.findViewById(R.id.NombreImagenOrinoquia);

        //SETEAR LOS DATOS

        NombreImagenOrinoquia.setText(nombre);


        //CONTROLAR POSIBLES ERRORES
        try {
            Picasso.get().load(imagen).placeholder(R.drawable.oso_def).into(ImagenOrinoquia);
        }
        catch (Exception e){

            Picasso.get().load(R.drawable.oso_def).into(ImagenOrinoquia);

        }

    }
}
