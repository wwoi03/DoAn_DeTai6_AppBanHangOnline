package com.example.doan_detai6_appbanhangonline;

import static com.example.doan_detai6_appbanhangonline.R.id.btnSignOut;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AccountSettingsActivity extends AppCompatActivity {
    Button btnSignOut;
    String filename = "config";
    SharedPreferences sharedPreferences;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_settings);
        sharedPreferences = getSharedPreferences(filename,MODE_PRIVATE);

        //đăng xuất
        btnSignOut = findViewById(R.id.btnSignOut);
        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();
                Intent intent = new Intent(AccountSettingsActivity.this,SignInActivity.class);
                startActivity(intent);
            }
        });
    }
}
