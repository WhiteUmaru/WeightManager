package com.gank.mybodymanage.entry;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gank.mybodymanage.R;

import java.util.ArrayList;

import static com.gank.mybodymanage.util.Util.getDate;
import static com.gank.mybodymanage.util.Util.getDateForYear;

/**
 * 适配器
 *
 * @author Ly
 * @date 2018/2/22
 */

public class MyAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Body> data;
    private LayoutInflater inflater;

    public MyAdapter(Context context, ArrayList<Body> data) {
        this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.e("", "getView: 111111111");
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item, null);
            holder = new ViewHolder();
            holder.weight = convertView.findViewById(R.id.weight);
            holder.date = convertView.findViewById(R.id.date);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Body body = data.get(position);
        float weight = body.getWeight();
        holder.weight.setText(weight / 100 + "");
        holder.date.setText(getDateForYear(body.getDate()));
        return convertView;
    }

    class ViewHolder {
        TextView weight, date;
    }
}
