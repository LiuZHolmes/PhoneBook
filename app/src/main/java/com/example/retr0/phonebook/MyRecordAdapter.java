package com.example.retr0.phonebook;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Mirai on 2018/6/21.
 */

public class MyRecordAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<CallRecord> mDatas;

    //MyAdapter需要一个Context，通过Context获得Layout.inflater，然后通过inflater加载item的布局
    public MyRecordAdapter(Context context, List<CallRecord> datas) {
        mInflater = LayoutInflater.from(context);
        mDatas = datas;
    }

    //返回数据集的长度
    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    //这个方法才是重点，我们要为它编写一个ViewHolder
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.record_listview, parent, false); //加载布局
            holder = new ViewHolder();
            holder.number = (TextView) convertView.findViewById(R.id.number);
            holder.date = (TextView) convertView.findViewById(R.id.date);
            holder.time = (TextView) convertView.findViewById(R.id.time);
            holder.type = (TextView) convertView.findViewById(R.id.type);
            holder.location = (TextView) convertView.findViewById(R.id.location);
            convertView.setTag(holder);
        } else {   //else里面说明，convertView已经被复用了，说明convertView中已经设置过tag了，即holder
            holder = (ViewHolder) convertView.getTag();
        }

        CallRecord cr = mDatas.get(position);
        holder.number.setText(cr.getNumber());
        holder.date.setText(cr.getDate());
        holder.time.setText(cr.getTime());
        holder.type.setText(cr.getType());
        holder.location.setText(cr.getLocation());

        TextView number = (TextView) convertView.findViewById(R.id.number);
        if(holder.type.getText().equals("Missed"))
        {
            number.setTextColor(Color.RED);
        }
        else
        {
            number.setTextColor(Color.BLACK);
        }

        return convertView;
    }

    //这个ViewHolder只能服务于当前这个特定的adapter，因为ViewHolder里会指定item的控件，不同的ListView，item可能不同，所以ViewHolder写成一个私有的类
    private class ViewHolder {
        TextView number;
        TextView date;
        TextView time;
        TextView type;
        TextView location;
    }
}
