package com.example.doan_detai6_appbanhangonline;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.doan_detai6_appbanhangonline.Adapter.CategoryAdapter;
import com.example.doan_detai6_appbanhangonline.Adapter.OrderAdapter;
import com.example.doan_detai6_appbanhangonline.Adapter.ProductAdapter;
import com.example.doan_detai6_appbanhangonline.Extend.FirebaseFirestoreAuth;
import com.example.doan_detai6_appbanhangonline.Model.Order;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WaitForConfirmationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WaitForConfirmationFragment extends Fragment implements OrderAdapter.Listener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public WaitForConfirmationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WaitForConfirmationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WaitForConfirmationFragment newInstance(String param1, String param2) {
        WaitForConfirmationFragment fragment = new WaitForConfirmationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    RecyclerView rvWCF;
    OrderAdapter orderAdapter;
    ArrayList<Order> orders;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_wait_for_confirmation, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initUI(view);
        initData();
        initListener();
    }

    // ánh xạ view
    private void initUI(View view) {
        rvWCF = view.findViewById(R.id.rvWCF);
    }

    // khởi tạo
    private void initData() {
        orders = new ArrayList<>();
        orderAdapter = new OrderAdapter(orders, WaitForConfirmationFragment.this);
        FirebaseFirestoreAuth.getOrders(orders, 0, orderAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rvWCF.setLayoutManager(linearLayoutManager);
        rvWCF.setAdapter(orderAdapter);
    }

    // xử lý sự kiện
    private void initListener() {

    }

    @Override
    public void setOnClickOrderListener(Order order, int pos) {
        Intent intent = new Intent(getContext(), DetailsOrderActivity.class);
        intent.putExtra("detailsOrder", order);
        startActivity(intent);
    }
}