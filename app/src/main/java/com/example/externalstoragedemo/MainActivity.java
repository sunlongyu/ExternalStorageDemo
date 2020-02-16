package com.example.externalstoragedemo;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText infoEditText;
    private Button saveButton;
    private Button readButton;
    private TextView contentTextview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int permission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }

        infoEditText = findViewById(R.id.info_edt);
        saveButton = findViewById(R.id.save_btn);
        readButton = findViewById(R.id.read_btn);
        contentTextview = findViewById(R.id.content_tv);

        saveButton.setOnClickListener(this);
        readButton.setOnClickListener(this);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            //xxxxxxxxxxxxxxxxxx
        }
    }

    @Override
    public void onClick(View v) {


        String path = MainActivity.this.getExternalFilesDir("Download") +"/imooc.txt";
        Log.d("tag", "onClick: " + path);
//        if (Environment.getExternalStorageState().equals("mounted")) {

        switch (v.getId()) {
            case R.id.save_btn:
                File file = new File(path);

                try {
                    if (!file.exists()) {
                        file.createNewFile();
                    }
                    //參數一：文件路徑 參數二：是否可追加
                    FileOutputStream fileOutputStream = new FileOutputStream(path, true);
                    String info = infoEditText.getText().toString();
                    fileOutputStream.write(info.getBytes());
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                break;
            case R.id.read_btn:
                try {
                    FileInputStream fis = new FileInputStream(path);
                    byte[] b = new byte[1024];
                    int len = fis.read(b);
                    String str2 = new String(b, 0, len);
                    contentTextview.setText(str2);
                    fis.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}
//}
