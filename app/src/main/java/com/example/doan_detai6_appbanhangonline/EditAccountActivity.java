package com.example.doan_detai6_appbanhangonline;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doan_detai6_appbanhangonline.Adapter.DeliveryAddressAdapter;
import com.example.doan_detai6_appbanhangonline.Extend.Config;
import com.example.doan_detai6_appbanhangonline.Extend.FirebaseFirestoreAuth;
import com.example.doan_detai6_appbanhangonline.Extend.FirebaseStorageAuth;
import com.example.doan_detai6_appbanhangonline.Model.Account;
import com.example.doan_detai6_appbanhangonline.Model.DeliveryAddress;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.net.URI;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class EditAccountActivity extends AppCompatActivity {

    Config config;
    Intent getDataIntent;
    Account account;
    EditText etName,etAddress,etPhone;
    ImageView ivAvt;
    TextView tvUserName,tvEmail;
    RadioButton rbMale,rbFemale;
    RadioGroup rgGender;
    FirebaseStorage storage;
    StorageReference storageReference;
    private Uri imageUri;
    String urlImage;
    boolean flagImage = false;
    public static int REQUEST_EDIT_ACCOUNT = 9999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);

        config = new Config(EditAccountActivity.this);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        initUI();
        initData();
        initListener();

        loadAccount();
        settingActionBar();
    }

    private void settingActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Sửa tài khoản");
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    public void initUI(){
        etName = findViewById(R.id.etName);
        etAddress = findViewById(R.id.etAddress);
        etPhone = findViewById(R.id.etPhone);
        tvEmail = findViewById(R.id.tvEmail);
        rbFemale = findViewById(R.id.rbFemale);
        rbMale = findViewById(R.id.rbMale);
        tvUserName = findViewById(R.id.tvUserName);
        ivAvt = findViewById(R.id.ivAccount);
        rgGender = findViewById(R.id.rgGender);
    }

    private void initData() {
        getDataIntent = getIntent();
        account = (Account) getDataIntent.getSerializableExtra("account");
    }

    private void initListener() {
        ivAvt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null){
            imageUri = data.getData();
            ivAvt.setImageURI(imageUri);
            flagImage = true;
        }
    }

    // load account
    private void loadAccount() {
        etName.setText(account.getName());
        etAddress.setText(account.getAddress());
        etPhone.setText(account.getPhone());
        tvEmail.setText(account.getEmail());
        tvUserName.setText(account.getName());
        account.loadImage(ivAvt);
        checkGender();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.save_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.mnSave: {
                // lấy dữ liệu mới
                String name = etName.getText().toString();
                String address = etAddress.getText().toString();
                String phone = etPhone.getText().toString();
                String gender = CheckGender();

                // nếu người dùng có bấm vào edit ảnh
                if (flagImage == true)
                    updateImageProfile();
                else
                    urlImage = account.getImageAccount();

                Account editAccount = new Account(account.getId(), account.getEmail(), phone, account.getPassword(), name, account.getBirthday(), gender, address, urlImage);
                Intent intent = getIntent();
                intent.putExtra("editAccount", editAccount);
                setResult(REQUEST_EDIT_ACCOUNT, intent);

                finish();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public String CheckGender(){
        String tmp = "";
        if (rbMale.isChecked())
            tmp= "Nam";
        else if (rbFemale.isChecked())
            tmp= "Nữ";
        return tmp;
    }

    //Check gender
    public void checkGender(){
        if(account.getGender().trim().equals("Nam")) {
            rgGender.check(R.id.rbMale);
        } else if (account.getGender().trim().equals("Nữ")) {
            rgGender.check(R.id.rbFemale);
        } else {

        }
    }

    //Thay anh dai dien
    public void updateImageProfile(){
        final String randomKey= UUID.randomUUID().toString();

        // Create a reference to 'images/mountains.jpg'
        StorageReference mountainImagesRef = storageReference.child("Accounts/"+randomKey);

        mountainImagesRef.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(EditAccountActivity.this,"Cập nhật thành công",Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EditAccountActivity.this,"Cập nhật thất bại",Toast.LENGTH_SHORT).show();
                    }
                });
        urlImage = randomKey;
    }
}