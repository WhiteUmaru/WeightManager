package com.gank.mybodymanage;

import android.Manifest;
import android.app.Dialog;
import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.gank.mybodymanage.entry.Body;
import com.gank.mybodymanage.entry.DialogAdapter;
import com.gank.mybodymanage.sql.DBImp;
import com.gank.mybodymanage.util.Util;

import java.util.ArrayList;
import java.util.List;

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

    /**
     * 选择用户
     *
     * @param view 界面
     */
    public void chooseUser(View view) {
        final ArrayList<Body> user = imp.getAllUser();
        ///新功能预留代码 下版本上线
//        PackageManager manager = getPackageManager();
//        List<PackageInfo> packages = manager.getInstalledPackages(0);
//        for (PackageInfo pi : packages) {
//            // 列出普通应用
//            if ((pi.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) <= 0) {
//                Log.e(TAG, "普通应用: " + manager.getApplicationLabel(pi.applicationInfo));
//            }
//            // 列出系统应用，总是感觉这里设计的有问题，希望高手指点
//            if ((pi.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) > 0) {
//                Log.e(TAG, "系统应用: " + pi.toString());
//            }
//        }
//
//
//        try {
//            startActivity(getPackageManager().getLaunchIntentForPackage("com.jianshu.haruki"));
//        } catch (Exception e) {
//            Toast.makeText(this, "No app", Toast.LENGTH_SHORT).show();
//        }


        if (user == null || user.isEmpty()) {
            Toast.makeText(this, "无用户，请创建", Toast.LENGTH_SHORT).show();
            userMsg(view);
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final Dialog dialog = builder.create();
        LayoutInflater inflater = LayoutInflater.from(this);
        View v = inflater.inflate(R.layout.dialog_view, null);
        Button bt = v.findViewById(R.id.dialog_bt);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        ListView listView = v.findViewById(R.id.select_dialog_listview);
        listView.setAdapter(new DialogAdapter(this, user));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Body body = user.get(position);
                Util.saveString(MainActivity.this, Util.USER_NAME, body.getName());
                Util.saveInt(MainActivity.this, Util.USER_HEIGHT, body.getHeight());
                title.setText(body.getName() + "的体重记录仪");
                dialog.dismiss();
            }
        });
        dialog.show();
        dialog.getWindow().setContentView(v);
    }

    private int getWeight(String weight) {
        double tz = Double.valueOf(weight);
        return (int) (tz * 100);
    }

}
