package com.example.doan_detai6_appbanhangonline;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.doan_detai6_appbanhangonline.Adapter.NotificationAdapter;
import com.example.doan_detai6_appbanhangonline.Extend.FirebaseFirestoreAuth;
import com.example.doan_detai6_appbanhangonline.Extend.FirebaseStorageAuth;
import com.example.doan_detai6_appbanhangonline.Model.Category;
import com.example.doan_detai6_appbanhangonline.Model.Notification;
import com.example.doan_detai6_appbanhangonline.Model.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NotificationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotificationFragment extends Fragment implements NotificationAdapter.Listener {

    //Khai bao bien toan cuc
    RecyclerView rcvNotification;
    ArrayList<Notification>notifications;
    ArrayList<Product>products;
    NotificationAdapter notificationAdapter;
    FirebaseFirestore db = FirebaseFirestore.getInstance();



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NotificationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NotificationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NotificationFragment newInstance(String param1, String param2) {
        NotificationFragment fragment = new NotificationFragment();
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


    //Ham can quan tam 1: Render giao dien
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notification, container, false);
    }

    //Ham can quan tam 2: onCreate
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initUI(view);
        initData();

        loadNotification();
    }

    public void initUI(View view){

        rcvNotification=view.findViewById(R.id.rcvNotification);
    }
    public  void initData(){
        products=new ArrayList<>();
        notifications=new ArrayList<>();
        notificationAdapter=new NotificationAdapter(notifications,NotificationFragment.this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rcvNotification.setLayoutManager(linearLayoutManager);
        rcvNotification.setAdapter(notificationAdapter);
    }



    // Load thong bao (Notification)
    private void loadNotification() {
        loadProductsAll();
        db.collection("Notification")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String id = document.getId();
                            String title=document.get("Title").toString();
                            String idproduct=document.get("IdProduct").toString();
                            String description=document.get("Description").toString();

                            Product product = new Product();
                            for (int i = 0; i < products.size(); i++) {
                                if (idproduct.equals(products.get(i).getId())) {
                                    product = products.get(i);
                                    break;
                                }
                            }
                            Notification notification=new Notification(id,idproduct,title,description,product);
                            notifications.add(notification);
                        }

                        notificationAdapter.notifyDataSetChanged();
                    }
                });
    }

    private void loadProductsAll() {
        FirebaseFirestoreAuth.db.collection("Product")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String id = document.getId();
                            String name = document.get("Name").toString();
                            double price = document.get("Price", Double.class);
                            int sold = document.get("Sold", Integer.class);
                            String description = document.get("Description").toString();
                            String updateDay = document.get("UpdateDay").toString(); // Ngày cập nhật
                            String imageProduct = document.get("ImageProduct").toString();
                            String idSupplier = document.get("IdSupplier").toString();
                            String idCategory = document.get("IdCategory").toString();
                            Product product = new Product(id, name, price, sold, description, updateDay, imageProduct, idSupplier, idCategory);
                            products.add(product);
                        }
                    }
                });
    }
}