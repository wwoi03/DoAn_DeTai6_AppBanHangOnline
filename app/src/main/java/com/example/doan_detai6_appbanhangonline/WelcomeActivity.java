package com.example.doan_detai6_appbanhangonline;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.doan_detai6_appbanhangonline.Extend.Config;
import com.example.doan_detai6_appbanhangonline.Extend.FirebaseFirestoreAuth;

import io.grpc.okhttp.internal.framed.FrameReader;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();


        Config config;
        config = new Config(WelcomeActivity.this);
        if (config.getSharedPreferences() != null) {
            FirebaseFirestoreAuth firebaseFirestoreAuth = new FirebaseFirestoreAuth(config);
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Trường hợp thứ nhất explicit: truyền màn hình đi mà không có gửi dữ liệu gì về
                Intent intent = new Intent(WelcomeActivity.this, SignInActivity.class);
                startActivity(intent);
            }
        }, 2000);
    }
}