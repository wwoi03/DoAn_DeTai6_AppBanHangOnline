package com.example.doan_detai6_appbanhangonline;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.doan_detai6_appbanhangonline.Model.Order;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MyOrderActivity extends AppCompatActivity {
    BottomNavigationView bnvMyOrder;
    WaitForConfirmationFragment waitForConfirmationFragment = new WaitForConfirmationFragment();
    ConfirmedFragment confirmedFragment = new ConfirmedFragment();
    CancelledFragment cancelledFragment = new CancelledFragment();
    /*ActivityResultLauncher<Intent> launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        if (result.getResultCode() == 111) {

                        }
                        if (result.getResultCode() == 222) {
                            Log.d("ABC", "DSADS");
                        }
                    }
                }
            }
    );*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);

        initUI();
        initListener();

        String fragmentToShow = (String) getIntent().getSerializableExtra("fragment_to_show");
        if (fragmentToShow != null && "WFCF".equals(fragmentToShow)) {
            bnvMyOrder.setSelectedItemId(R.id.mnuWaitForConfirmation);
            Toast.makeText(this, "Đặt hàng thành công", Toast.LENGTH_SHORT).show();
        }
        if (fragmentToShow != null && "CF".equals(fragmentToShow)) {
            bnvMyOrder.setSelectedItemId(R.id.mnuCancelled);
            Toast.makeText(this, "Hủy đơn hàng thành công", Toast.LENGTH_SHORT).show();
        }

        settingActionBar();
    }

    private void settingActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Đơn mua");
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
    public void loadFragment(Fragment fragment) {
        // giúp thế Fragment vào FrameLayout trên giao diện
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.flMyOrder, fragment);
        transaction.commit();
    }
}