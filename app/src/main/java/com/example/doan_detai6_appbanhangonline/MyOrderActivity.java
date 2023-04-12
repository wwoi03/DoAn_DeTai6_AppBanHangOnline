package com.example.doan_detai6_appbanhangonline;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.MenuItem;

import com.example.doan_detai6_appbanhangonline.Model.Order;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MyOrderActivity extends AppCompatActivity {
    BottomNavigationView bnvMyOrder;
    WaitForConfirmationFragment waitForConfirmationFragment = new WaitForConfirmationFragment();
    ConfirmedFragment confirmedFragment = new ConfirmedFragment();
    CancelledFragment cancelledFragment = new CancelledFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);

        initUI();
        initListener();

        settingActionBar();
    }

    private void settingActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.baseline_arrow_back_24);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFFFF")));
        actionBar.setTitle("Đơn mua");
        Spannable text = new SpannableString(actionBar.getTitle());
        text.setSpan(new ForegroundColorSpan(Color.parseColor("#000000")), 0, text.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        actionBar.setTitle(text);
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    // ánh xạ view
    private  void initUI() {
        bnvMyOrder = findViewById(R.id.bnvMyOrder);
    }

    // xử lý sự kiện
    private void initListener() {
        loadFragment(waitForConfirmationFragment);
        // xử lý khi bấm chọn một menu bất kỳ
        bnvMyOrder.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.mnuWaitForConfirmation:
                        loadFragment(waitForConfirmationFragment);
                        break;
                    case R.id.mnuConfirmed:
                        loadFragment(confirmedFragment);
                        break;
                    case R.id.mnuCancelled:
                        loadFragment(cancelledFragment);
                        break;
                }
                return true;
            }
        });
    }

    // Load Fragment
    private void loadFragment(Fragment fragment) {
        // giúp thế Fragment vào FrameLayout trên giao diện
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.flMyOrder, fragment);
        transaction.commit();
    }

    /*public void detailsOrder(Order order, ) {
        Intent intent = new Intent(, DetailsOrderActivity.class);
        intent.putExtra("detailsOrder", order);
        startActivity(intent);
    }*/
}