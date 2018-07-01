package com.example.retr0.phonebook;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.List;
import static android.content.Context.MODE_PRIVATE;

/**
 * Created by john on 2018/6/26.
 */

public class MyCountAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<CallRecord> mDatas,mTimes;
    private Context mContext;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private List<String> mlists ;


    //MyAdapter需要一个Context，通过Context获得Layout.inflater，然后通过inflater加载item的布局
    public MyCountAdapter(Context context, List<CallRecord> datas,List<String> mlist, List<CallRecord> time) {
        mInflater = LayoutInflater.from(context);
        mDatas = datas;
        mTimes=time;
        mlists = mlist;
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
            convertView = mInflater.inflate(R.layout.statistic_listview, parent, false); //加载布局
            holder = new ViewHolder();
            holder.number = (TextView) convertView.findViewById(R.id.number);
            holder.type = (TextView) convertView.findViewById(R.id.type);
            convertView.setTag(holder);
        } else {   //else里面说明，convertView已经被复用了，说明convertView中已经设置过tag了，即holder
            holder = (ViewHolder) convertView.getTag();
        }


        CallRecord cr = mDatas.get(position);
        if (cr.getLocation().equals("0")){
            //表示通话时长
            long temp=0;
            for (int j=0;j<mTimes.size();j++) {

                if (cr.getNumber().equals(mTimes.get(j).getNumber())){
                    if (!mTimes.get(j).getDuration().equals("0")){
                        temp= temp+ Integer.parseInt(mTimes.get(j).getDuration().substring(0,mTimes.get(j).getDuration().indexOf(":")))*60+Integer.parseInt(mTimes.get(j).getDuration().substring(mTimes.get(j).getDuration().indexOf(":")+1,mTimes.get(j).getDuration().length()));
                    }


                }
                holder.type.setText(temp/60+"分"+temp%60+"秒");
            }

        }else {
            //表示通话次数
            if (mlists.size()>0){
                int index=0;
                for (int i=0;i<mlists.size();i++) {

                    if (cr.getNumber().equals(mlists.get(i))){
                        index++;

                    }

                }
                holder.type.setText(index+"次");
            }


        }

        holder.number.setText(cr.getNumber());

        int contactIndex;
        if((contactIndex = findMyContact(holder.number.getText().toString())) != -1)
        {
            holder.number.setText(pref.getString("contact_name"+contactIndex,""));
        }

        return convertView;
    }

    //这个ViewHolder只能服务于当前这个特定的adapter，因为ViewHolder里会指定item的控件，不同的ListView，item可能不同，所以ViewHolder写成一个私有的类
    private class ViewHolder {
        TextView number;
        TextView type;

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
