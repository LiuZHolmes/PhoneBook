package com.example.retr0.phonebook;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class UserShowInformation extends AppCompatActivity {
    static List<String> name, birth, mobile, add, home;
    static int nowSize;
    static boolean isdelete, notzero;
    private Button return_btn;
    private Button ok_button;
    private int size;
    private TextView etName,etHome,etMobile,etAdd,etBirth;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_show_information);
        pref = getSharedPreferences("ContactData", MODE_PRIVATE);

        size=pref.getInt("size",0);

        return_btn = findViewById(R.id.return_button);
        ok_button= findViewById(R.id.ok_button);

        etName=findViewById(R.id.edit_name);
        etHome=findViewById(R.id.edit_home);
        etMobile=findViewById(R.id.edit_mobile);
        etAdd=findViewById(R.id.edit_add);
        etBirth=findViewById(R.id.edit_birth);

        String temp = pref.getString("contactName", "NULL");
        etName.setText(temp);
        temp = pref.getString("contactHome", "NULL");
        etHome.setText(temp);
        temp = pref.getString("contactMobile", "NULL");
        etMobile.setText(temp);
        temp = pref.getString("contactAdd", "NULL");
        etAdd.setText(temp);
        temp = pref.getString("contactBirth", "NULL");
        etBirth.setText(temp);
    }
    public void return_main_page(View view) {
        Intent intent = new Intent();
        intent.setClass(UserShowInformation.this,MainActivity.class);
        startActivity(intent);
    }
    public void check_edit(View view) {
        Intent intent = new Intent();
        intent.setClass(UserShowInformation.this,UserInformation2.class);
        startActivity(intent);
    }
    public void check_delete(View view) {
        /*int num = pref.getInt("contactSize", 0);
        editor = pref.edit();
        editor.remove("contact_home" + num);
        editor.remove("contact_name" + num);
        editor.remove("contact_add" + num);
        editor.remove("contact_birth" + num);
        editor.remove("contact_mobile" + num);
        editor.remove("contact_size" + num);
        num = pref.getInt("size", 0);
        //editor.putInt("size", num - 1);
        editor.apply();

        */


        isdelete = true;
        editor = pref.edit();
        int num = pref.getInt("contactSize", 0);
        name= new ArrayList<String>();
        mobile= new ArrayList<String>();
        home= new ArrayList<String>();
        add= new ArrayList<String>();
        birth = new ArrayList<String>();

        nowSize = size - 1;
        if(nowSize == 0) {
            editor.clear();
            editor.apply();
        }
        else {

            notzero = true;
            for (int i = 0; i < size; i++) {
                if (i != num) {
                    name.add(pref.getString("contact_name" + i, "NULL"));
                    mobile.add(pref.getString("contact_mobile" + i, "NULL"));
                    home.add(pref.getString("contact_home" + i, "NULL"));
                    add.add(pref.getString("contact_add" + i, "NULL"));
                    birth.add(pref.getString("contact_birth" + i, "NULL"));

                }
            }

            editor.clear();
            editor.apply();
        }



        Intent intent = new Intent();
        intent.setClass(UserShowInformation.this,MainActivity.class);
        startActivity(intent);
    }
}
