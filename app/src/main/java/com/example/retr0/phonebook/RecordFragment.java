package com.example.retr0.phonebook;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2018/6/18.
 */

public class RecordFragment extends Fragment{
    private String content;

    public RecordFragment() {
    }

    // 自定义fragment的绘制，根据点击事件传入的消息，绘制不同的页面
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = null;
        this.content = (String) getArguments().get("str");
        if(content == "所有通话")
        {
            view = inflater.inflate(R.layout.all_record,container,false);
        }
        else if(content == "未接来电")
        {
            view = inflater.inflate(R.layout.unpick_record,container,false);
        }

        Log.d("content",content);
        return view;
    }
}
