package com.gank.mybodymanage;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.gank.mybodymanage.entry.Body;
import com.gank.mybodymanage.entry.MyAdapter;
import com.gank.mybodymanage.sql.DBImp;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
        imp = DBImp.getInstance(this);
        adapter = new MyAdapter(this, data);
        lv = findViewById(R.id.lv);
        lv.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        refresh();
    }

    private void refresh() {
        data.clear();
        List<Unit> lines = new ArrayList<>();
        ArrayList<Body> bodies = imp.getAllDate();
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


}
