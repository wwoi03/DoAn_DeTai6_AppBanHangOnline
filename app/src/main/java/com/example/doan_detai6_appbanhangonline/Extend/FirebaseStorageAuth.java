package com.example.doan_detai6_appbanhangonline.Extend;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.example.doan_detai6_appbanhangonline.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class FirebaseStorageAuth {
    static FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();

    public FirebaseStorageAuth() {

    }

    public static void loadImage(String folder, String image, ImageView imageView) {
        StorageReference storageRef = firebaseStorage.getReference();
        String bucketName = storageRef.getBucket();
        String location = "gs://" + bucketName + "/" + folder + "/";

        // Tham chiếu đến tệp ảnh trên firebase storage
        StorageReference imageRef = firebaseStorage.getReferenceFromUrl(location + image);
        imageRef.getBytes(1024 * 1024)
                .addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        imageView.setImageBitmap(bitmap);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        imageView.setImageResource(R.drawable.baseline_error_outline_24);
                    }
                });
    }
}
