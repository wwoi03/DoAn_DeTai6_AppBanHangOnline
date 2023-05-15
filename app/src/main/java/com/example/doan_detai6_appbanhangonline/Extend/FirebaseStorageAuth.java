package com.example.doan_detai6_appbanhangonline.Extend;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.doan_detai6_appbanhangonline.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.net.URI;
import java.net.URISyntaxException;

public class FirebaseStorageAuth {
    static FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();

    public FirebaseStorageAuth() {

    }

    public static void loadImage(String folder, String image, ImageView imageView) {
        FirebaseStorage storage = FirebaseStorage.getInstance(); // khởi tạo đối tượng FirebaseStorage
        StorageReference storageRef = storage.getReference(); // tham chiếu đến thư mục gốc của Firebase Storage
        StorageReference imageRef = storageRef.child(folder + "/" + image); // đường dẫn tới file hình ảnh trên Firebase Storage

        imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                String imageURL = uri.toString(); // địa chỉ URL của hình ảnh trên Firebase Storage
                // sử dụng thư viện Glide
                Glide.with(imageView.getContext())
                        .load(imageURL)
                        .into(imageView);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Xử lý nếu không lấy được địa chỉ URL của hình ảnh
            }
        });

        /*StorageReference storageRef = firebaseStorage.getReference();
        String bucketName = storageRef.getBucket(); // lấy đường dẫn đến Storage trên firebase
        String location = "gs://" + bucketName + "/" + folder + "/"; // nối đường dẫn đến địa chỉ folder cụ thể

        // Tham chiếu đến tệp ảnh trên firebase storage
        StorageReference imageRef = firebaseStorage.getReferenceFromUrl(location + image);
        // sử dụng phương thức getBytes() để tải xuống dữ liệu hình ảnh  dưới dạng một mảng byte
        imageRef.getBytes(1024 * 1024)
                .addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        // nếu tải xuống thành công, sử dụng BitmapFactory để chuyển đổi mảng byte thành đối tượng bitmap
                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        imageView.setImageBitmap(bitmap); // sử dụng setImageBitMap() để hiện thị hình ảnh
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // nếu không thành công thì để ảnh mặc định : ảnh lỗi
                        imageView.setImageResource(R.drawable.baseline_error_outline_24);
                    }
                });*/
    }
}
