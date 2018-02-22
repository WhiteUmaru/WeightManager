package com.gank.mybodymanage;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.gank.mybodymanage.entry.Body;
import com.gank.mybodymanage.sql.DBImp;

import java.util.ArrayList;

/**
 * @author Ly
 */
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private DBImp imp;
    private EditText editText;

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
        imp = new DBImp(this);
        editText = findViewById(R.id.editText);
    }

    /**
     * 记录体重
     *
     * @param view 界面
     */
    public void addWeight(View view) {
        Body body = new Body();
        body.setWeight(getWeight(editText.getText().toString()));
        body.setHeight(180);
        int now = (int) (System.currentTimeMillis() / 1000);
        Log.e(TAG, "addWeight: " + now);
        body.setDate(now);
        body.setName("Ly");
        imp.add(body);
    }

    /**
     * 查看体重
     *
     * @param view 界面
     */
    public void lookWeight(View view) {
        ArrayList<Body> data = imp.getAllDate();
        Log.e(TAG, "lookWeight: ");
        if (data == null || data.isEmpty()) {
            return;
        }
        Log.e(TAG, "lookWeight: " + data.toString());
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
