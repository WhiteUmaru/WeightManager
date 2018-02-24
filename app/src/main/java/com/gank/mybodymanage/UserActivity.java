package com.gank.mybodymanage;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.gank.mybodymanage.entry.Body;
import com.gank.mybodymanage.entry.User;
import com.gank.mybodymanage.sql.DBImp;
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

        FloatingActionButton bt = findViewById(R.id.fab);
        bt.setOnClickListener((view) -> {
            String userName = name.getText().toString();
            int userHeight = Integer.valueOf(height.getText().toString());
            saveDate(addUser(userName, userHeight));
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        String userName = Util.getString(this, Util.USER_NAME, "姓名");
        name.setText(userName);
        height.setText(String.valueOf(Util.getInt(this, Util.USER_HEIGHT, 180)));
    }

    public void save(View view) {
        //获取存储的对象值
        String userString = Util.getString(this, Util.USER, null);
        User oldUser = User.getUser(userString);

        //获取用户输入值
        String userName = name.getText().toString();
        int userHeight = Integer.valueOf(height.getText().toString());
        //查询是否有这条数据
        DBImp imp = DBImp.getInstance(this);
        User user;
        if (oldUser.getId() < 1) {
            user = imp.getUser(Util.getString(this, Util.USER_NAME, "Ly"));
        } else {
            user = imp.getUser(oldUser.getId());
        }

        if (user != null) {
            user.setName(userName);
            user.setHeight(userHeight);
            imp.updateUser(user);
        } else {
            addUser(userName, userHeight);
        }
        saveDate(user);
        finish();
    }

    public User addUser(String userName, int userHeight) {
        User user = new User();
        user.setName(userName);
        user.setHeight(userHeight);
        int res = DBImp.getInstance(this).addUser(user);
        Log.e("----->", "addUser: " + res);
        user.setId(res);
        if (res < 0) {
            Toast.makeText(this, "保存失败", Toast.LENGTH_SHORT).show();
        }
        return user;
    }

    private void saveDate(User user) {
        Util.saveString(this, Util.USER_NAME, user.getName());
        Util.saveInt(this, Util.USER_HEIGHT, user.getHeight());
        Util.saveString(this, Util.USER, user.toString());
        Util.saveInt(this, Util.USER_ID, user.getId());
        Toast.makeText(this, "已保存", Toast.LENGTH_SHORT).show();
    }

}
