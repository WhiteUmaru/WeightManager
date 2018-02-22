package com.gank.mybodymanage;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gank.mybodymanage.entry.Body;
import com.gank.mybodymanage.sql.DBImp;
import com.gank.mybodymanage.util.Util;

import java.util.ArrayList;

/**
 * @author Ly
 */
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private DBImp imp;
    private EditText editText;
    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_main);
        imp = DBImp.getInstance(this);
        editText = findViewById(R.id.editText);
        title = findViewById(R.id.main_title);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        101);
            }
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        String viewTitle = Util.getString(this, Util.USER_NAME, "小土豆");
        title.setText(viewTitle + "的体重记录仪");
    }

    /**
     * 记录体重
     *
     * @param view 界面
     */
    public void addWeight(View view) {
        String weight = editText.getText().toString();
        if (TextUtils.isEmpty(weight)) {
            Toast.makeText(this, "请填写体重", Toast.LENGTH_SHORT).show();
            return;
        }
        Body body = new Body();
        body.setWeight(getWeight(weight));
        body.setHeight(Util.getInt(this, Util.USER_HEIGHT, 180));
        int now = (int) (System.currentTimeMillis() / 1000);
        body.setDate(now);
        body.setName(Util.getString(this, Util.USER_NAME, "Ly"));
        imp.add(body);
    }

    /**
     * 查看体重
     *
     * @param view 界面
     */
    public void lookWeight(View view) {
        Intent intent = new Intent(this, LookActivity.class);
        startActivity(intent);
    }

    /**
     * 个人信息
     *
     * @param view 界面
     */
    public void userMsg(View view) {
        Intent intent = new Intent(this, UserActivity.class);
        startActivity(intent);
    }

    private int getWeight(String weight) {
        double tz = Double.valueOf(weight);
        return (int) (tz * 100);
    }

}
