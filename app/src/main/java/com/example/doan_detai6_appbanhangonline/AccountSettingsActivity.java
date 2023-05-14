package com.example.doan_detai6_appbanhangonline;

import static com.example.doan_detai6_appbanhangonline.R.id.btnSignOut;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.doan_detai6_appbanhangonline.Extend.Config;
import com.example.doan_detai6_appbanhangonline.Extend.FirebaseFirestoreAuth;
import com.example.doan_detai6_appbanhangonline.Model.Account;
import com.example.doan_detai6_appbanhangonline.Model.DeliveryAddress;

public class AccountSettingsActivity extends AppCompatActivity {
    Button btnSignOut;
    LinearLayout llEditAccount, llAddress, llInterfaceSetting;
    Config config;
    Account account;

    ActivityResultLauncher<Intent> launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == EditAccountActivity.REQUEST_EDIT_ACCOUNT) {
                        Account editAccount = (Account) result.getData().getSerializableExtra("editAccount");
                        FirebaseFirestoreAuth.updateAccount(editAccount);
                        Toast.makeText(AccountSettingsActivity.this,"Lưu thành công",Toast.LENGTH_SHORT).show();
                        account = FirebaseFirestoreAuth.getAccount(); // Cập nhật lại account ở trang hiện tại
                    }
                }
            }
    );

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_settings);

        config = new Config(AccountSettingsActivity.this);

        initUI();
        initData();
        initListener();

        settingActionBar();
    }

    private void settingActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Thiết lập tài khoản");
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    // ánh xạ view
    private void initUI() {
        btnSignOut = findViewById(R.id.btnSignOut);
        llEditAccount = findViewById(R.id.llEditAccount);
        llAddress = findViewById(R.id.llAddress);
        llInterfaceSetting = findViewById(R.id.llInterfaceSetting);
    }

    private void initData() {
        account = FirebaseFirestoreAuth.getAccount();
    }

    // xử lý sự kiện
    private void initListener() {
        // xử lý bấm vào địa chỉ người nhận
        llAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccountSettingsActivity.this, AddressActivity.class);
                intent.putExtra("flag", 1);
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

        //xử lí khi búm zào sửa tèi khoẻn hehehehe
        llEditAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AccountSettingsActivity.this,EditAccountActivity.class);
                intent.putExtra("account", account);
                launcher.launch(intent);
            }
        });
    }
}
