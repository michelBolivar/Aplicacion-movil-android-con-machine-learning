package com.example.test.Catego.Anim_dispo;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.Catego.Cat_dispo.ViewHolderDC;
import com.example.test.R;
import com.squareup.picasso.Picasso;

public class ViewHolderAD  extends RecyclerView.ViewHolder {

    View mview;

    private ViewHolderAD.ClickListener mClickListener;

    public interface  ClickListener{
        //ADMIN PRESIONA NORMAL EL ITEM

        void onItemClick(View view, int position);
    }

    //METODO PARA PODER PRESIONAR O MATENER PRESIONADO UN ITEM
    public void setOnClickListener(ViewHolderAD.ClickListener clickListener){
        mClickListener = clickListener;

    }
    public ViewHolderAD(@NonNull View itemView) {
        super(itemView);
        mview = itemView;

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClickListener.onItemClick(view,getBindingAdapterPosition());
            }
        });

    }


    public void SeteoCategrias(Context context , String nombre , String imagen ){

        ImageView ImagenEcosistemaC;
        TextView NombreAnimalC;

        //CONEXION CON EL ITEM

        ImagenEcosistemaC = mview.findViewById(R.id.ImagenEcosistemaC);
        NombreAnimalC = mview.findViewById(R.id.NombreAnimalC);

        //SETEAR LOS DATOS
        NombreAnimalC.setText(nombre);

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
