package com.example.doan_detai6_appbanhangonline;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    EditText etEmail, etPassword;
    Button btRegister;
    TextView tvSignIn;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        db = FirebaseFirestore.getInstance();

        initUI();
        initListener();
    }

    // ánh xạ view
    private void initUI() {
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btRegister = findViewById(R.id.btRegister);
        tvSignIn = findViewById(R.id.tvSignIn);
    }

    // xử lý sự kiện
    private void initListener() {
        // xử lý sự kiện khi bấm vào "Đăng ký"
        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                if (isEmpty(email, password)) {
                    Query account = db.collection("Account").whereEqualTo("Email", email);
                    account.get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    // Kiểm tra trong task có truy vấn thành công hay không
                                    if (task.isSuccessful()) {
                                        int count = 0;
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            count++;
                                        }

                                        // kiểm tra tài khoản đã tồn tại trong hệ thống
                                        if (count > 0)
                                            Toast.makeText(RegisterActivity.this, "Tài khoản đã tồn tại", Toast.LENGTH_SHORT).show();
                                        else {
                                            Map<String, Object> account = new HashMap<>();
                                            putAccount(account);
                                            register(account);
                                        }
                                    }
                                }
                            });
                }
            }
        });

        // xử lý sự kiện chuyển trang "Đăng nhập"
        tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
    }

    // kiểm tra chuỗi
    private boolean isEmpty(String email, String password) {
        if (email.isEmpty()) {
            Toast.makeText(RegisterActivity.this, "Email không được để trống", Toast.LENGTH_SHORT).show();
        } else if (password.isEmpty()) {
            Toast.makeText(RegisterActivity.this, "Mật khẩu không được để trống", Toast.LENGTH_SHORT).show();
        } else {
            return true;
        }
        return false;
    }

    // put Account
    private void putAccount(Map<String, Object> account) {
        account.put("Email", etEmail.getText().toString());
        account.put("Password", etPassword.getText().toString());
        account.put("Address", "");
        account.put("Birthday", "");
        account.put("Gender", "");
        account.put("Name", "");
        account.put("Phone", "");
        account.put("ImageAccount", "");
    }

    // Thêm account vào database
    private void register(Map<String, Object> account) {
        db.collection("Account")
                .document()
                .set(account)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        signIn();
                        Toast.makeText(RegisterActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // chuyển sang "Đăng nhập"
    private void signIn() {
        Intent intent = new Intent(RegisterActivity.this, SignInActivity.class);
        startActivity(intent);
    }
}