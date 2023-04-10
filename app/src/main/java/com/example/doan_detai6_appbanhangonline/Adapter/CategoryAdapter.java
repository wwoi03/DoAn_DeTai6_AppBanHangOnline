package com.example.doan_detai6_appbanhangonline.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan_detai6_appbanhangonline.Extend.FirebaseStorageAuth;
import com.example.doan_detai6_appbanhangonline.Model.Category;
import com.example.doan_detai6_appbanhangonline.R;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryVH>   {
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

        FirebaseStorageAuth.loadImage("Categories", category.getImageCategory(), holder.ivCategory);

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
