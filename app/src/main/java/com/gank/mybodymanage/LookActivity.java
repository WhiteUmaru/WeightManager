package com.gank.mybodymanage;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.gank.mybodymanage.entry.Body;
import com.gank.mybodymanage.entry.MyAdapter;
import com.gank.mybodymanage.sql.DBImp;
import com.gank.mybodymanage.util.Util;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tech.linjiang.suitlines.SuitLines;
import tech.linjiang.suitlines.Unit;

import static com.gank.mybodymanage.util.Util.getDate;

/**
 * @author Ly
 */
public class LookActivity extends AppCompatActivity {
    private static final String TAG = "LookActivity";
    SuitLines suitlines;
    private DBImp imp;
    private ListView lv;
    private MyAdapter adapter;
    ArrayList<Body> data = new ArrayList<>();
    List<Unit> lines = new ArrayList<>();
    private int[] color = new int[]{
            R.color.colorAccent1,
            R.color.colorAccent2,
            R.color.colorAccent3,
            R.color.colorAccent4,
            R.color.colorAccent5,
            R.color.colorAccent,
    };

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
        setContentView(R.layout.activity_look);
        suitlines = findViewById(R.id.suitlines);
        suitlines.setLineStyle(SuitLines.SOLID);
        suitlines.setLineType(SuitLines.SEGMENT);
        suitlines.setLineForm(true);
        suitlines.setHintColor(R.color.colorAccent4);
        suitlines.setCoverLine(true);
        imp = DBImp.getInstance(this);
        adapter = new MyAdapter(this, data);
        lv = findViewById(R.id.lv);
        lv.setAdapter(adapter);
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(LookActivity.this)
                        .setTitle("删除数据")
                        .setMessage("是否删除该条体重信息")
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                imp.deleteMsg(data.get(position));
                                data.remove(position);
                                lines.remove(position);
                                suitlines.feedWithAnim(lines);
                                adapter.notifyDataSetChanged();
                                dialog.dismiss();
                            }
                        });
                builder.create().show();
                return false;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        refresh();
    }

    private void refresh() {
        data.clear();
        lines.clear();
        ArrayList<Body> bodies = imp.getAllDate(false);
        if (bodies == null || bodies.isEmpty()) {
            Toast.makeText(this, "无数据！", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        data.addAll(bodies);
        adapter.notifyDataSetChanged();
        for (Body datum : data) {
            float weight = datum.getWeight();
            lines.add(new Unit(weight / 100, getDate(datum.getDate())));
        }
        suitlines.feedWithAnim(lines);
    }

    private void buildAll() {
        ArrayList<Body> bodies = imp.getAllDate(true);
        Map<String, ArrayList<Body>> map = filterArrays(bodies);
        SuitLines.LineBuilder builder = new SuitLines.LineBuilder();
        for (String key : map.keySet()) {
            ArrayList<Body> bodyArrayList = map.get(key);
            List<Unit> line = new ArrayList<>();
            for (Body datum : bodyArrayList) {
                float weight = datum.getWeight();
                line.add(new Unit(weight / 100, getDate(datum.getDate())));
            }
            builder.add(line, color);
        }
        builder.build(suitlines, true);
    }

    public Map<String, ArrayList<Body>> filterArrays(ArrayList<Body> arrayList) {
        Map<String, ArrayList<Body>> map = new HashMap<>();
        for (Body body : arrayList) {
            String name = body.getName();
            if (map.containsKey(name)) {
                map.get(name).add(body);
            } else {
                ArrayList<Body> bodyArrayList = new ArrayList<>();
                bodyArrayList.add(body);
                map.put(name, bodyArrayList);
            }
        }
        return map;
    }


}
