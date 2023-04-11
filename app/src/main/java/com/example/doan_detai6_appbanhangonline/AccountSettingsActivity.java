package com.example.doan_detai6_appbanhangonline;

import static com.example.doan_detai6_appbanhangonline.R.id.btnSignOut;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.doan_detai6_appbanhangonline.Extend.Config;

public class AccountSettingsActivity extends AppCompatActivity {
    Button btnSignOut;
    LinearLayout llEditAccount, llAddress, llInterfaceSetting;
    Config config;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_settings);

        config = new Config(AccountSettingsActivity.this);

        initUI();
        initListener();
    }

    // ánh xạ view
    private void initUI() {
        btnSignOut = findViewById(R.id.btnSignOut);
        llEditAccount = findViewById(R.id.llEditAccount);
        llAddress = findViewById(R.id.llAddress);
        llInterfaceSetting = findViewById(R.id.llInterfaceSetting);
    }

    // xử lý sự kiện
    private void initListener() {
        // xử lý bấm vào địa chỉ người nhận
        llAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccountSettingsActivity.this, AddressActivity.class);
                startActivity(intent);
            }
        });

        // xử lý đăng xuất
        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                config.getEditor().clear();
                config.getEditor().commit();
                Intent intent = new Intent(AccountSettingsActivity.this,SignInActivity.class);
                startActivity(intent);
            }
        });
    }
}
