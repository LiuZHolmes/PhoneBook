package com.example.retr0.phonebook;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by mhy on 2018/6/21.
 */

public class MyContactsAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<Contacts> mDatas;

    public MyContactsAdapter(Context context, List<Contacts> datas) {
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
        MyContactsAdapter.ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.contact_listview, parent, false); //加载布局
            holder = new  MyContactsAdapter.ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.contact_name);

            convertView.setTag(holder);
        } else {   //else里面说明，convertView已经被复用了，说明convertView中已经设置过tag了，即holder
            holder = (MyContactsAdapter.ViewHolder) convertView.getTag();
        }

        Contacts Con = mDatas.get(position);
        holder.name.setText(Con.getName());


        return convertView;
    }

    //这个ViewHolder只能服务于当前这个特定的adapter，因为ViewHolder里会指定item的控件，不同的ListView，item可能不同，所以ViewHolder写成一个私有的类
    private class ViewHolder {
        TextView name;

    }
}
