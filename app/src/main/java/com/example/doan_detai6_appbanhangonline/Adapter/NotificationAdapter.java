package com.example.doan_detai6_appbanhangonline.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan_detai6_appbanhangonline.Extend.FirebaseStorageAuth;
import com.example.doan_detai6_appbanhangonline.Model.Notification;
import com.example.doan_detai6_appbanhangonline.Model.Product;
import com.example.doan_detai6_appbanhangonline.R;

import java.util.ArrayList;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationVH> {
    ArrayList<Notification> notifications;
    Listener listener;

    public NotificationAdapter(ArrayList<Notification>notifications, Listener listener){
        this.notifications = notifications;
        this.listener = listener;
    }

    @NonNull
    @Override
    public NotificationVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_row,parent,false);
        return new NotificationVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationVH holder, int position) {
        Notification notification = notifications.get(position);

        //Anh thong bao
        notification.getProduct().loadImage(holder.ivProduct);
        //Tieu de thong bao
        holder.tvTitle.setText(notification.getTitle());
        //Noi dung thong bao
        holder.tvDescription.setText(notification.getDescription());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.setOnClickNotificationListener(notification.getProduct(), notification.getIdProduct());
            }
        });
    }


    @Override
    public int getItemCount() {
        return notifications.size();
    }

    public class NotificationVH extends RecyclerView.ViewHolder {
        ImageView ivProduct;
        TextView tvTitle, tvDescription;
        public NotificationVH(@NonNull View itemView) {
            super(itemView);
            ivProduct = itemView.findViewById(R.id.ivProduct);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDescription = itemView.findViewById(R.id.tvDescription);
        }
    }

    public interface Listener{
        void setOnClickNotificationListener(Product product, String id);
    }
}
