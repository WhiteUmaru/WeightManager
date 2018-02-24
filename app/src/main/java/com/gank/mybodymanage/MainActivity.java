package com.gank.mybodymanage;

import android.Manifest;
import android.app.Dialog;
import android.app.SearchManager;
import android.content.ComponentName;
import android.content.DialogInterface;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.gank.mybodymanage.entry.Body;
import com.gank.mybodymanage.entry.DialogAdapter;
import com.gank.mybodymanage.entry.User;
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
        title.setText(viewTitle + "\n体重记录仪");
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

        imp.add(getWeight(view, weight));
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
        final ArrayList<User> users = imp.getAllUser();
        Log.e(TAG, "chooseUser: " + users);

        if (users == null || users.isEmpty()) {
            Toast.makeText(this, "无用户，请创建", Toast.LENGTH_SHORT).show();
            userMsg(view);
            return;
        }
        //创建弹窗
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final Dialog dialog = builder.create();
        LayoutInflater inflater = LayoutInflater.from(this);
        //创建界面
        View v = inflater.inflate(R.layout.dialog_view, null);
        //初始化组件
        Button bt = v.findViewById(R.id.dialog_bt);
        bt.setOnClickListener(v1 -> dialog.dismiss());

        ListView listView = v.findViewById(R.id.select_dialog_listview);
        listView.setAdapter(new DialogAdapter(this, users));
        listView.setOnItemClickListener((parent, view12, position, id) -> {
            chooseUser(users, position);
            dialog.dismiss();
        });
        listView.setOnItemLongClickListener((parent, view1, position, id) -> {
            Util.createDialog(MainActivity.this, "删除用户", "是否删除该条用户信息",
                    (log, which) -> {
                        int res = imp.deleteUser(users.get(position));
                        Log.e(TAG, "chooseUser: " + res);
                        users.remove(position);
                        if (users.size() > 0) {
                            chooseUser(users, 0);
                        }
                        log.dismiss();
                        dialog.dismiss();
                    }).show();
            return false;
        });

        dialog.show();
        dialog.getWindow().setContentView(v);
    }

    private Body getWeight(View view, String weight) {
        double tz = Double.valueOf(weight);
        int height = Util.getInt(this, Util.USER_HEIGHT, -1);
        if (height == -1) {
            userMsg(view);
        }
        Body body = new Body();
        body.setWeight((int) (tz * 100));
        body.setHeight(height);
        body.setUserId(Util.getInt(this, Util.USER_ID, 1));
        int now = (int) (System.currentTimeMillis() / 1000);
        body.setDate(now);
        body.setName(Util.getString(this, Util.USER_NAME, "Ly"));
        double bmi = (tz / (height * height)) * 100000;
        Log.e(TAG, "bmi: --" + bmi);
        body.setBMI((int) bmi);
        return body;
    }

    private void chooseUser(ArrayList<User> users, int position) {
        User userBody = users.get(position);
        Util.saveString(MainActivity.this, Util.USER_NAME, userBody.getName());
        Util.saveInt(MainActivity.this, Util.USER_HEIGHT, userBody.getHeight());
        Util.saveString(MainActivity.this, Util.USER, userBody.toString());
        title.setText(String.valueOf(userBody.getName() + "\n体重记录仪"));

    }

    public void playGame(View view) {
        ///新功能预留代码 下版本上线
        PackageManager manager = getPackageManager();
        List<PackageInfo> packages = manager.getInstalledPackages(0);
        ArrayList<String> gameNames = new ArrayList<>();
        ArrayList<PackageInfo> gameInfo = new ArrayList<>();
        for (PackageInfo pi : packages) {
            // 列出普通应用
            if ((pi.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) <= 0) {
                String appName = manager.getApplicationLabel(pi.applicationInfo).toString();
                gameNames.add(appName);
                gameInfo.add(pi);
            }
        }
        ListView listView = new ListView(this);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1, gameNames);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener((AdapterView<?> parent, View v, int position, long id) -> {
            try {
                startActivity(getPackageManager().getLaunchIntentForPackage(gameInfo.get(position).packageName));
            } catch (Exception e) {
                Toast.makeText(this, "No app", Toast.LENGTH_SHORT).show();
            }
        });
        new AlertDialog.Builder(this)
                .setView(listView)
                .setTitle("玩个游戏吧")
                .setNegativeButton("取消", null)
                .create().show();

    }

    private final String[] gameList = new String[]{"消消乐", "地主", "荣耀", "师", "麻将"};

    private boolean isGame(String game) {
        for (String s : gameList) {
            if (game.contains(s)) {
                return true;
            }
        }
        return false;
    }
}
