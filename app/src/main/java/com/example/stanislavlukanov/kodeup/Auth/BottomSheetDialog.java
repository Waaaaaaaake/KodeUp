package com.example.stanislavlukanov.kodeup.Auth;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.stanislavlukanov.kodeup.R;

public class BottomSheetDialog extends BottomSheetDialogFragment {
    String user;
    String mail;
    String phone;

    TextView tv_phone;
    TextView tv_mail;
    TextView tv_user;
    ImageView iv_phone;
    ImageView iv_mail;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.bottom_sheet_layout, container, false);
        tv_user = (TextView) v.findViewById(R.id.bs_name_user);
        tv_mail = (TextView) v.findViewById(R.id.bs_name_email);
        tv_phone = (TextView) v.findViewById(R.id.bs_name_phone);
        iv_mail = (ImageView) v.findViewById(R.id.bs_iv_email);
        iv_phone = (ImageView) v.findViewById(R.id.bs_iv_phone);


        tv_user.setText(user);
        tv_mail.setText(mail);
        tv_phone.setText(phone);



        tv_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyCall();
            }
        });
        tv_mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyMail();
            }
        });

        iv_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyCall();
            }
        });

        iv_mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyMail();
            }
        });

        return v;
    }
    public void setResources(String user, String mail, String phone) {
        this.user = user;
        this.mail = mail;
        this.phone = phone;
    }

    public void MyCall() {
        String dial = "tel:" + phone;
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(dial)));
    }

    public void MyMail() {
        Intent email = new Intent(Intent.ACTION_SEND);
        email.putExtra(Intent.EXTRA_EMAIL, new String[]{mail});
        email.setType("message/rfc822");
        startActivity(Intent.createChooser(email, "Выберите email клиент:"));
    }
}
