package com.example.doan_detai6_appbanhangonline;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.doan_detai6_appbanhangonline.Adapter.OrderAdapter;
import com.example.doan_detai6_appbanhangonline.Extend.FirebaseFirestoreAuth;
import com.example.doan_detai6_appbanhangonline.Model.Order;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ConfirmedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ConfirmedFragment extends Fragment implements OrderAdapter.Listener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ConfirmedFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ConfirmedFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ConfirmedFragment newInstance(String param1, String param2) {
        ConfirmedFragment fragment = new ConfirmedFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    RecyclerView rvConfirmed;
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
        return inflater.inflate(R.layout.fragment_confirmed, container, false);
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
        rvConfirmed = view.findViewById(R.id.rvConfirmed);
    }

    // khởi tạo
    private void initData() {
        orders = new ArrayList<>();
        orderAdapter = new OrderAdapter(orders, ConfirmedFragment.this);
        FirebaseFirestoreAuth.getOrder(orders, 1, orderAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rvConfirmed.setLayoutManager(linearLayoutManager);
        rvConfirmed.setAdapter(orderAdapter);
    }

    // xử lý sự kiện
    private void initListener() {

    }
}