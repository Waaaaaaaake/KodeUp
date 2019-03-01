package com.example.stanislavlukanov.kodeup.Auth;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;

import com.example.stanislavlukanov.kodeup.R;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.squareup.picasso.Picasso;

public class ImageFragment extends Fragment {

    SimpleDraweeView draweeView;
    ImageView imageView;
    Switch mySwitch;
    Uri uri;
    private String url_fresco  = "https://hh.ru/employer-logo/1569503.jpeg";
    private String url_picasso = "https://bx-cert.ru/upload/iblock/b5c/b5c6e6c5b835d2f2f4dfe43cdb924283.jpg";

    public static ImageFragment newInstance(){
        Bundle args = new Bundle();
        ImageFragment fragment = new ImageFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Fresco.initialize(getContext());
        View v = inflater.inflate(R.layout.fr_image, container, false);

        mySwitch = (Switch) v.findViewById(R.id.my_switch);
        imageView = (ImageView) v.findViewById(R.id.imageView);
        draweeView = (SimpleDraweeView) v.findViewById(R.id.SimpleDraweeView);

        uri = Uri.parse(url_fresco);
        draweeView.setImageURI(uri);

        imageView.setVisibility(View.INVISIBLE);
        Picasso.with(getContext()).load(url_picasso).placeholder(R.drawable.ic_cloud_download)
                .error(R.drawable.my_drawable)
                .into(imageView);;

        return v;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    imageView.setVisibility(View.INVISIBLE);
                    draweeView.setVisibility(View.VISIBLE);
                } else {
                    draweeView.setVisibility(View.INVISIBLE);
                    imageView.setVisibility(View.VISIBLE);
                }
            }
        });

    }
}
