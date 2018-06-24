package com.example.retr0.phonebook;


import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


public class MyFragment extends Fragment {

    private String content;

    private int[] s = new int[100];
    private ListView recordListView, contactListView;
    private SearchView searchView;
    private List<CallRecord> records;
    private List<Contacts> Contact,findList;
    private MyContactsAdapter mAdapter;
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
        int size=pref.getInt("size",0);

        for(int i=0;i<size;i++) {
            s[i] = pref.getInt("sort", i);
        }
        readContacts();
        if(content == getResources().getString(R.string.contact)) {
            view = inflater.inflate(R.layout.contact_content,container,false);
            readContacts();
            findList=new ArrayList<Contacts>();
            searchView=view.findViewById(R.id.search_contact);

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    if(TextUtils.isEmpty(newText))
                    {
                        contactListView.setFilterText(newText);
                    }
                    else {
                        findList.clear();
                        for(int i = 0; i < Contact.size(); i++)
                        {
                            Contacts contact=Contact.get(i);
                            if(contact.getName().toLowerCase().contains(newText.toLowerCase()))
                            {
                                findList.add(contact);
                            }
                            else if(contact.getHomeNumber().toLowerCase().contains(newText.toLowerCase()))
                            {
                                findList.add(contact);
                            }
                            else if(contact.getPhoneNumber().toLowerCase().contains(newText.toLowerCase()))
                            {
                                findList.add(contact);
                            }
                        }
                        mAdapter=new MyContactsAdapter(getActivity(),findList);
                        mAdapter.notifyDataSetChanged();
                        contactListView.setAdapter(mAdapter);
                    }
                    return false;
                }
            });

            searchView.setOnCloseListener(new SearchView.OnCloseListener() {
                @Override
                public boolean onClose() {
                    contactListView.setAdapter(new MyContactsAdapter(getActivity(),Contact));
                    return false;
                }
            });

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
                    int num = contact.getNum();

                    editor=pref.edit();
                    editor.putString("contactName",name);
                    editor.putString("contactHome",homeNum);
                    editor.putString("contactMobile",phoneNum);
                    editor.putString("contactAdd",address);
                    editor.putString("contactBirth",birthday);
                    editor.putInt("contactSize", num);
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



        if(UserInformation.isAdd || UserInformation2.isEdit) {

            for(int i = 0; i < size; i++) {
                for(int j = i+1; j < size; j++) {
                    int com = pref.getString("contact_name"+i, "NULL").compareTo(pref.getString("contact_name"+j, "NULL"));
                    if(com < 0) {
                        int temp = s[j];
                        s[j] = s[i];
                        s[i] = temp;
                    }
                    else if(com == 0) {
                        int com1 = pref.getString("contact_mobile"+i, "NULL").compareTo(pref.getString("contact_mobile"+j, "NULL"));
                        if(com1 < 0) {
                            int temp = s[j];
                            s[j] = s[i];
                            s[i] = temp;
                        }
                    }
                }
            }
            UserInformation.isAdd = false;
            UserInformation2.isEdit = false;
        }

        editor = pref.edit();
        for(int i = 0; i < size; i++) {
            editor.putInt("size" + i, s[i]);
        }
        editor.apply();


            for (int i = 0; i < size; i++) {
                for(int j = 0; j < size; j++) {
                    if(s[j] != size - i - 1)
                        continue;
                    else {
                        String temp1 = pref.getString("contact_name" + j, "NULL");
                        String temp2 = pref.getString("contact_home" + j, "NULL");
                        String temp3 = pref.getString("contact_mobile" + j, "NULL");
                        String temp4 = pref.getString("contact_add" + j, "NULL");
                        String temp5 = pref.getString("contact_birth" + j, "NULL");
                        int temp6 = j;
                        Contacts tmpContact = new Contacts(temp1, temp2, temp3, temp4, temp5, temp6);
                        Contact.add(tmpContact);
                    }
                }
            }

    }

}
