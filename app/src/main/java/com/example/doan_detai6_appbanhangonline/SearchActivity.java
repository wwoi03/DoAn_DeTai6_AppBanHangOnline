package com.example.doan_detai6_appbanhangonline;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.doan_detai6_appbanhangonline.Adapter.ProductAdapter;
import com.example.doan_detai6_appbanhangonline.Extend.FirebaseFirestoreAuth;
import com.example.doan_detai6_appbanhangonline.Model.Product;

import java.util.ArrayList;
import java.util.Locale;

public class SearchActivity extends AppCompatActivity implements Filterable, ProductAdapter.Listener {
    // Render
    ArrayList<Product> products;
    RecyclerView rvSearch;
    ProductAdapter productAdapter;

    // Search
    SearchView searchView;
    String tmpSearch;

    // View
    TextView tvPriceInDecrease, tvSelling, tvPriceDecrease;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initUI();
        initData();
        initListener();

        settingActionBar();
    }

    // setActionBar
    private void settingActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Tìm kiếm sản phẩm...");
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    // ánh xạ View
    private void initUI() {
        rvSearch = findViewById(R.id.rvSearch);
        tvPriceInDecrease = findViewById(R.id.tvPriceInDecrease);
        tvSelling = findViewById(R.id.tvSelling);
        tvPriceDecrease = findViewById(R.id.tvPriceDecrease);
    }

    // lấy dữ liệu
    private void initData() {
        // product
        products = new ArrayList<>();
        productAdapter = new ProductAdapter(products, SearchActivity.this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(SearchActivity.this, 2);
        rvSearch.setLayoutManager(gridLayoutManager);
        rvSearch.setAdapter(productAdapter);
    }

    // xử lý sự kiện
    private void initListener() {
        // xử lý khi bấm vào "Giá tăng dần"
        tvPriceInDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                products.clear();
                FirebaseFirestoreAuth.getSearchProducts(products, productAdapter, tmpSearch, 1);
            }
        });

        // xử lý khi bấm vào "Bán chạy"
        tvSelling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                products.clear();
                FirebaseFirestoreAuth.getSearchProducts(products, productAdapter, tmpSearch, 3);
            }
        });

        // xử lý khi bấm vào "Giá giảm dần"
        tvPriceDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                products.clear();
                FirebaseFirestoreAuth.getSearchProducts(products, productAdapter, tmpSearch, 2);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.search_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView =  (SearchView) menu.findItem(R.id.mnuSearch).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            // tìm kiếm chính xác
            public boolean onQueryTextSubmit(String query) {
                getFilter().filter(query);
                return false;
            }

            @Override
            // tìm kiếm gần đúng
            public boolean onQueryTextChange(String newText) {
                /*getFilter().filter(newText);*/
                return false;
            }
        });

        return true;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            // Thực hiện công việc tìm kiếm
            protected FilterResults performFiltering(CharSequence constraint) {
                String searchString  = constraint.toString().toLowerCase();
                tmpSearch = searchString;
                if (searchString.isEmpty()) {
                    products.clear();
                } else {
                    products.clear();
                    FirebaseFirestoreAuth.getSearchProducts(products, productAdapter, searchString, 0);
                }

                // vì hàm này trả về một đối tượng FilterResults nên ta khởi tạo đối tượng FilterResults filterResults
                FilterResults filterResults = new FilterResults();
                // sau đó gán giá trị list vừa nhận được cho nó thông qua thuộc tính values có trong đối tượng
                filterResults.values = products;
                return filterResults;
            }

            @Override
            // đổ kết quả tìm kiếm lên giao diện
            protected void publishResults(CharSequence constraint, FilterResults results) {
                products = (ArrayList<Product>) results.values;
                productAdapter.notifyDataSetChanged();
            }
        };
    }

    @Override
    // xử lý bấm vào một sản phẩm bất kỳ
    public void setOnClickItemListener(Product product, String id) {
        Intent intent = new Intent(SearchActivity.this, DetailsProductActivity.class);
        intent.putExtra("detailsProduct", product);
        intent.putExtra("idProduct", id);
        startActivity(intent);
    }
}