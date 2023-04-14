package com.example.doan_detai6_appbanhangonline;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doan_detai6_appbanhangonline.Extend.Config;
import com.example.doan_detai6_appbanhangonline.Extend.FirebaseFirestoreAuth;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class SignInActivity extends AppCompatActivity {
    EditText etEmail, etPassword;
    CheckBox cbConfirm;
    Button btSignIn;
    TextView tvRegister;
    FirebaseFirestore db;
    String fileName = "config"; // dùng để lưu shared_preferences
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        db = FirebaseFirestore.getInstance();
        sharedPreferences = getSharedPreferences(fileName, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        initUI();
        initListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (sharedPreferences != null) {
            if (sharedPreferences.getString("email", "") != "") {
                mainActivity();
            }
        }
    }

    private void initUI() {
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        cbConfirm = findViewById(R.id.cbConfirm);
        btSignIn = findViewById(R.id.btSignIn);
        tvRegister = findViewById(R.id.tvRegister);
    }

    private void initListener() {
        // xứ lý khi bấm nút đăng nhập
        btSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEmpty(etEmail.getText().toString(), etPassword.getText().toString())) {
                    if (cbConfirm.isChecked()) {
                        String email = etEmail.getText().toString().trim();
                        String password = etPassword.getText().toString().trim();
                        CheckAccount(email, password);
                    }
                }
            }
        });

        // xử lý khi bấm vào chuyển qua đăng ký
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    // chuyển sang MainActivity
    private void mainActivity() {
        Intent intent = new Intent(SignInActivity.this, MainActivity.class);
        startActivity(intent);
    }

    // Kiểm tra chuỗi
    private boolean isEmpty(String email, String password) {
        if (email.isEmpty()) {
            toastMessage("Email không được để trống");
        } else if (password.isEmpty()) {
            toastMessage("Mật khẩu không được để trống");
        } else if (cbConfirm.isChecked() == false) {
            toastMessage("Chưa xác nhận đăng nhập");
        } else {
            return true;
        }
        return false;
    }

    // Toast
    private void toastMessage(String text) {
        Toast.makeText(SignInActivity.this, text, Toast.LENGTH_SHORT).show();
    }

    // Kiểm tra tài khoản có tồn tại trong hệ thống không
    private void CheckAccount(String email, String password) {
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
                                if (document.exists()) {
                                    // Kiểm tra password
                                    String docPass = document.get("Password").toString().trim();
                                    if (docPass.equals(password)) {
                                        toastMessage("Đăng nhập thành công");

                                        /*
                                         * Cơ chế: getSharedPreferences(fileName, <MODE>)
                                         *   1: MODE_PRIVATE: cho phép ghi đè
                                         *   2: MODE_APPEND: cho phép ghi thêm
                                         * */
                                        editor.putString("id", document.getId().toString());
                                        editor.commit();

                                        // chuyển qua trang chủ
                                        mainActivity();
                                    } else {
                                        toastMessage("Thông tin tài khoản hoặc mật khẩu không chính xác");
                                    }
                                }
                            }

                            // Kiểm tra tài khoản tồn tại trong hệ thống
                            if (count == 0)
                                toastMessage("Tài khoản không tồn tại");
                        }
                    }
                });
    }
}