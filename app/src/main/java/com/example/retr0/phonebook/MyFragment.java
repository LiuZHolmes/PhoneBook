package com.example.retr0.phonebook;


import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


public class MyFragment extends Fragment {

    private String content;

    //call record issues
    private ListView recordListView;
    private List<CallRecord> records;
    private MyRecordAdapter mAdapter;


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
            readRecords();
            Log.d("mmznb","readOK");
            recordListView = (ListView) view.findViewById(R.id.recordListView);
            recordListView.setAdapter(new MyRecordAdapter(getActivity(), records));

        }
        else if(content == getResources().getString(R.string.statistic))
        {
            view = inflater.inflate(R.layout.statistic_content,container,false);
        }
        Log.d("content",content);
        return view;
    }


    public void readRecords()
    {
        records = new ArrayList<CallRecord>();
        InputStream inputStream = getResources().openRawResource(R.raw.rawrecords);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader reader = new BufferedReader(inputStreamReader);
        String line;
        try {
            while ((line = reader.readLine()) != null)
            {
                String [] arr = line.split("\\s+");
                CallRecord tmpRecord = new CallRecord(arr[0], arr[1], arr[2], arr[3], arr[4]);
                records.add(tmpRecord);
            }
            inputStream.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }


}
