package com.example.doan_detai6_appbanhangonline.Adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.GnssAntennaInfo;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.LongDef;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.doan_detai6_appbanhangonline.Extend.FirebaseStorageExtend;
import com.example.doan_detai6_appbanhangonline.Model.Category;
import com.example.doan_detai6_appbanhangonline.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryVH>   {
    FirebaseStorageExtend firebaseStorageExtend = new FirebaseStorageExtend();
    ArrayList<Category> categories;
    Listener listener;

    public CategoryAdapter(Listener listener,ArrayList<Category>categories){
        this.categories=categories;
        this.listener=listener;
    }

    @NonNull
    @Override
    public CategoryVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.category_cell,parent,false);
        CategoryVH categoryVH = new CategoryVH(view);
        return categoryVH;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.CategoryVH holder, int position) {
        Category category=categories.get(position);

        holder.tvNameCategory.setText(category.getName());

        firebaseStorageExtend.loadImage("Categories", category.getImageCategory(), holder.ivCategory);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.setOnClickItemCategoryListener(category.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class CategoryVH extends RecyclerView.ViewHolder {
        ImageView ivCategory;
        TextView tvNameCategory;
        public CategoryVH(@NonNull View itemView) {
            super(itemView);

            ivCategory=itemView.findViewById(R.id.ivCategory);
            tvNameCategory=itemView.findViewById(R.id.tvNameCategory);
        }
    }
    public interface Listener {
        void setOnClickItemCategoryListener(String id);
    }
}
