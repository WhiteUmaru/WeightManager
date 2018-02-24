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

import static com.gank.mybodymanage.util.Util.getDateForYear;

/**
 * 适配器
 *
 * @author Ly
 * @date 2018/2/22
 */

public class DialogAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<User> data;
    private LayoutInflater inflater;

    public DialogAdapter(Context context, ArrayList<User> data) {
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
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_dialog, null);
            holder = new ViewHolder();
            holder.name = convertView.findViewById(R.id.name);
            holder.height = convertView.findViewById(R.id.height);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        User body = data.get(position);
        holder.name.setText(body.getName());
        holder.height.setText(body.getHeight() + "");
        return convertView;
    }

    class ViewHolder {
        TextView name, height;
    }
}
