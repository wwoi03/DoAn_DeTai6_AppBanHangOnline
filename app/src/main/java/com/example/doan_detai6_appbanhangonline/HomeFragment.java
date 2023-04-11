package com.example.doan_detai6_appbanhangonline;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.doan_detai6_appbanhangonline.Adapter.CategoryAdapter;
import com.example.doan_detai6_appbanhangonline.Adapter.ProductAdapter;
import com.example.doan_detai6_appbanhangonline.Extend.FirebaseFirestoreAuth;
import com.example.doan_detai6_appbanhangonline.Model.Category;
import com.example.doan_detai6_appbanhangonline.Model.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.errorprone.annotations.Var;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements CategoryAdapter.Listener, ProductAdapter.Listener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    // KHAI BÁO CÁC BIẾN TOÀN CỤC
    RecyclerView rvCategories, rvProducts;
    ArrayList<Category> categories;
    CategoryAdapter categoryAdapter;
    ProductAdapter productAdapter;
    ArrayList<Product> products;
    LinearLayout llCategoryAll;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ActivityResultLauncher<Intent> launcher =registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {

                }
            }
    );

    @Override
    // HÀM CẦN QUAN TÂM: RENDER GIAO DIỆN
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    // hàm quan trọng 2: giống hàm oncreate
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initUI(view);
        initData();
        initListener();

        loadCategories(view);
    }

    // ánh xạ view
    private void initUI(View view) {
        rvProducts = view.findViewById(R.id.rvProducts);
        rvCategories = view.findViewById(R.id.rvCategories);
        llCategoryAll = view.findViewById(R.id.llCategoryAll);
    }

    // khởi tạo
    private void initData() {
        // product
        products = new ArrayList<>();
        productAdapter = new ProductAdapter(products, HomeFragment.this);
        FirebaseFirestoreAuth.getProducts(products, productAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        rvProducts.setLayoutManager(gridLayoutManager);
        rvProducts.setAdapter(productAdapter);

        // category
        categories = new ArrayList<>();
        categoryAdapter = new CategoryAdapter(HomeFragment.this, categories);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rvCategories.setLayoutManager(linearLayoutManager);
        rvCategories.setAdapter(categoryAdapter);
    }

    // xử lý sự kiện
    private void initListener() {
        // xử lý sự kiện khi bấm vào danh mục "Tất cả"
        llCategoryAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                products.clear();
                FirebaseFirestoreAuth.getProducts(products, productAdapter);
            }
        });
    }

    // Load danh mục sản phẩm (Category)
    private void loadCategories(View view) {
        db.collection("Category")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String id = document.getId();
                            String name = document.get("Name").toString();
                            String imageCategory = document.get("Image").toString();
                            Category category = new Category(id, name, imageCategory);
                            categories.add(category);
                        }

                        categoryAdapter.notifyDataSetChanged();
                    }
                });
    }



    @Override
    // xử lý khi bấm vào một thể loại bất kỳ
    public void setOnClickItemCategoryListener(String id) {
        products.clear();
        FirebaseFirestoreAuth.getProductCategory(products, id, productAdapter);
    }

    @Override
    // xử lý khi bấm vào một sản phẩm bất kỳ bất kỳ
    public void setOnClickItemListener(Product product, String id) {
        Intent intent = new Intent(getContext(), DetailsProductActivity.class);
        intent.putExtra("detailsProduct", product);
        intent.putExtra("idProduct", id);
        launcher.launch(intent);
    }
}