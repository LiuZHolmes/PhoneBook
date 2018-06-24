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
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class MyFragment extends Fragment {

    private final static Comparator<Object> CHINA_COMPARE = Collator.getInstance(java.util.Locale.CHINA);


    private String content;

    private int[] s = new int[100];
    //call record issues
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
            s[i] = pref.getInt("sort"+ i, i);
        }
        if(UserShowInformation.isdelete && UserShowInformation.notzero) {
            size=UserShowInformation.nowSize;
            editor = pref.edit();
            editor.putInt("size", size);
            for(int i = 0; i < size; i++) {
                editor.putString("contact_name"+i,UserShowInformation.name.get(i));
                editor.putString("contact_home"+i,UserShowInformation.home.get(i));
                editor.putString("contact_mobile"+i,UserShowInformation.mobile.get(i));
                editor.putString("contact_add"+i,UserShowInformation.add.get(i));
                editor.putString("contact_birth"+i,UserShowInformation.birth.get(i));
                editor.putInt("contact_size"+ i,i);
            }

            editor.apply();
        }
        //readContacts();
        if(content == getResources().getString(R.string.contact))
        {
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


        List<String> n = new ArrayList<String>();
        for(int i = 0; i < size; i++) {
            n.add(pref.getString("contact_name"+i, "NULL"));
        }

        if(UserInformation.isAdd || UserInformation2.isEdit || UserShowInformation.isdelete) {
            Collections.sort(n, CHINA_COMPARE);
            for(int i = 0; i <size; i++) {
                for(int j = 0; j < size; j++) {
                    if(pref.getString("contact_name" + i, "NULL").equals(n.get(j))) {
                        s[j] = i;
                        break;
                    }
                }
            }
            UserInformation.isAdd = false;
            UserInformation2.isEdit = false;
            UserShowInformation.isdelete = false;
            System.out.println(n);
        }


        editor = pref.edit();
        for(int i = 0; i < size; i++) {
            editor.putInt("sort" + i, s[i]);
            Log.d("", String.valueOf(s[i]));
        }
        editor.apply();


            for (int i = 0; i < size; i++) {

                        String temp1 = pref.getString("contact_name" + s[i], "NULL");
                        String temp2 = pref.getString("contact_home" + s[i], "NULL");
                        String temp3 = pref.getString("contact_mobile" + s[i], "NULL");
                        String temp4 = pref.getString("contact_add" + s[i], "NULL");
                        String temp5 = pref.getString("contact_birth" + s[i], "NULL");
                        int temp6 = s[i];
                        Contacts tmpContact = new Contacts(temp1, temp2, temp3, temp4, temp5, temp6);
                        Contact.add(tmpContact);

            }

    }

}
