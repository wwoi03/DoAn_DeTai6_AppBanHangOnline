package com.example.doan_detai6_appbanhangonline;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.doan_detai6_appbanhangonline.Extend.Config;
import com.example.doan_detai6_appbanhangonline.Extend.FirebaseFirestoreAuth;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bnvMenu;
    HomeFragment homeFragment = new HomeFragment();
    NotificationFragment notificationFragment = new NotificationFragment();
    UserFragment userFragment = new UserFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bnvMenu = findViewById(R.id.bnvMenu);

        loadFragment(homeFragment); // load giao diện mặc định
        getSupportActionBar().setTitle("Trang chủ");

        // Xử lý khi bấm vào item bất kì
        bnvMenu.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.mnuHome:
                        getSupportActionBar().setTitle("Trang chủ");
                        loadFragment(homeFragment);
                        break;

                    case R.id.mnuNotification:
                        getSupportActionBar().setTitle("Thông báo");
                        loadFragment(notificationFragment);
                        break;

                    case R.id.mnuUser:
                        getSupportActionBar().setTitle("Tài khoản người dùng");
                        loadFragment(userFragment);
                        break;
                }
                return true;
            }
        });
    }

    // Load Fragment
    public void loadFragment(Fragment fragment) {
        // giúp thế Fragment vào FrameLayout trên giao diện
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.flContainer, fragment);
        transaction.commit();
    }

    @Override
    // khởi tạo menu
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_header, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    // tương tác các item trong menu
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mnuCart:
                Intent cart = new Intent(MainActivity.this, CartActivity.class);
                startActivity(cart);
                break;
            case R.id.mnuSearch:
                Intent search = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(search);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}