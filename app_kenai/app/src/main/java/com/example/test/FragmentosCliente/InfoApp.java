package com.example.test.FragmentosCliente;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.test.R;


public class InfoApp extends Fragment {

    ImageView insta , face, you ;
    String url_inta = "https://www.instagram.com/kenai_latam";
    String url_face = "https://www.facebook.com/kenai.latam";
    String url_you = "https://www.youtube.com/channel/UCtisppKtmhhcBKv0R03h_Ug";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_info_app, container, false);

        insta  = view.findViewById(R.id.insta_noso);
        face = view . findViewById(R.id.face_noso);
        you = view.findViewById(R.id.you_noso);

        insta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri ins_lin = Uri.parse(url_inta);
                Intent i = new Intent(Intent.ACTION_VIEW, ins_lin);
                startActivity(i);
            }
        });
        face.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri ins_lin = Uri.parse(url_face);
                Intent i = new Intent(Intent.ACTION_VIEW, ins_lin);
                startActivity(i);
            }
        });
        you.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri ins_lin = Uri.parse(url_you);
                Intent i = new Intent(Intent.ACTION_VIEW, ins_lin);
                startActivity(i);
            }
        });
        return view;
    }
}