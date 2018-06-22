package com.example.retr0.phonebook;


import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
    private ListView recordListView, contactListView;
    private List<CallRecord> records;
    private List<Contacts> Contact;
    private MyRecordAdapter mAdapter;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    public MyFragment() {
    }


    // 自定义fragment的绘制，根据点击事件传入的消息，绘制不同的页面
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = null;
        pref = getActivity().getSharedPreferences("ContactData", Context.MODE_PRIVATE);
        this.content = (String) getArguments().get("str");
        if(content == getResources().getString(R.string.contact))
        {
            view = inflater.inflate(R.layout.contact_content,container,false);
            readContacts();
            contactListView = (ListView)view.findViewById(R.id.contactListView);
            contactListView.setAdapter(new MyContactsAdapter(getActivity(),Contact));
            contactListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Contacts contact = Contact.get(position);
                    String name = contact.getName();
                    String homeNum = contact.getHomeNumber();
                    String phoneNum = contact.getPhoneNumber();
                    String address = contact.getAddress();
                    String birthday = contact.getBirthday();

                    editor=pref.edit();
                    editor.putString("contactName",name);
                    editor.putString("contactHome",homeNum);
                    editor.putString("contactMobile",phoneNum);
                    editor.putString("contactAdd",address);
                    editor.putString("contactBirth",birthday);
                    editor.apply();


                    Intent intent=new Intent(getActivity(),UserShowInformation.class);
                    startActivity(intent);

                }
            });
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
    public void readContacts()
    {
        Contact = new ArrayList<Contacts>();
        int size=pref.getInt("size",0);

        for(int i=0;i<size;i++)
        {
            String temp1 = pref.getString("contact_name"+i, "NULL");
            String temp2 = pref.getString("contact_home"+i, "NULL");
            String temp3 = pref.getString("contact_mobile"+i, "NULL");
            String temp4 = pref.getString("contact_add"+i, "NULL");
            String temp5 = pref.getString("contact_birth"+i, "NULL");
            Contacts tmpContact = new Contacts(temp1, temp2, temp3, temp4, temp5);
            Contact.add(tmpContact);
        }
    }


}
