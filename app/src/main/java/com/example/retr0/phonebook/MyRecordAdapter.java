package com.example.retr0.phonebook;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Mirai on 2018/6/21.
 */

public class MyRecordAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<CallRecord> mDatas;
    private Context mContext;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    //MyAdapter需要一个Context，通过Context获得Layout.inflater，然后通过inflater加载item的布局
    public MyRecordAdapter(Context context, List<CallRecord> datas) {
        mInflater = LayoutInflater.from(context);
        mDatas = datas;
        this.mContext = context;
        pref = mContext.getSharedPreferences("ContactData", MODE_PRIVATE);
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
        if(holder.type.getText().equals("未接来电"))
        {
            number.setTextColor(Color.RED);
        }
        else
        {
            number.setTextColor(Color.BLACK);
        }
        int contactIndex;
        final String newNumber = holder.number.getText().toString();
        if((contactIndex = findMyContact(holder.number.getText().toString())) != -1)
        {
            number.setText(pref.getString("contact_name"+contactIndex,""));
        }
        else
        {
            //number.setClickable(true);
            //number.setFocusable(true);
            convertView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Intent intent =  new Intent(mContext,UserInformation.class);
                    intent.putExtra("new_number", newNumber);
                    mContext.startActivity(intent);
                }
            });
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

    private int findMyContact(String number)
    {
        int size =pref.getInt("size",0);
        String tmp;
        for(int i = 0;i < size;i++)
        {
            tmp = pref.getString("contact_mobile"+i,"");
            if(tmp.equals(number))
                return i;
        }
        return -1;
    }


}
