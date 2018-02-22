package com.gank.mybodymanage;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.gank.mybodymanage.util.Util;

/**
 * @author Ly
 */
public class UserActivity extends AppCompatActivity {

    private EditText name, height;


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
        setContentView(R.layout.activity_user);
        name = findViewById(R.id.userName);
        height = findViewById(R.id.userHeight);
    }

    @Override
    protected void onStart() {
        super.onStart();
        name.setText(Util.getString(this,Util.USER_NAME,"姓名"));
        height.setText(Util.getInt(this,Util.USER_HEIGHT,180)+"");
    }

    public void save(View view) {
        Util.saveString(this, Util.USER_NAME, name.getText().toString());
        Util.saveInt(this, Util.USER_HEIGHT, Integer.valueOf(height.getText().toString()));
        Toast.makeText(this, "已保存", Toast.LENGTH_SHORT).show();
        finish();
    }


}
