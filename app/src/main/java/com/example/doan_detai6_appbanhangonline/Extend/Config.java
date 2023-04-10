package com.example.doan_detai6_appbanhangonline.Extend;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

public class Config extends AppCompatActivity {
    String fileName = "config";
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public Config(Context context) {
        sharedPreferences = context.getSharedPreferences(fileName, context.MODE_PRIVATE);
    }

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    public void setSharedPreferences(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public SharedPreferences.Editor getEditor() {
        return editor;
    }

    public String getIdAccount() {
        return sharedPreferences.getString("id", "");
    }

    /*public String getName() {
        return
    }*/
}
