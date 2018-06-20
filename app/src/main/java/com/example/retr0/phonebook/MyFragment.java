package com.example.retr0.phonebook;


import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;



public class MyFragment extends Fragment {

    private String content;


    public MyFragment() {
    }


    // 自定义fragment的绘制，根据点击事件传入的消息，绘制不同的页面
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = null;
        this.content = (String) getArguments().get("str");
        if(content == getResources().getString(R.string.contact))
        {
            view = inflater.inflate(R.layout.contact_content,container,false);
        }
        else if(content == getResources().getString(R.string.record))
        {
            view = inflater.inflate(R.layout.record_content,container,false);
        }
        else if(content == getResources().getString(R.string.statistic))
        {
            view = inflater.inflate(R.layout.statistic_content,container,false);
        }
        Log.d("content",content);
        return view;
    }

}
