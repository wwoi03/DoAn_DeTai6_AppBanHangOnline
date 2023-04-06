package com.example.doan_detai6_appbanhangonline.Extend;

import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

public class Config extends AppCompatActivity {
    String fileName = "config";
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public Config() {
        sharedPreferences = getSharedPreferences(fileName, MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public SharedPreferences.Editor getEditor() {
        return editor;
    }

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    /*public String getName() {
        return
    }*/
}
