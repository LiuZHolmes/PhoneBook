package com.example.retr0.phonebook;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class UserShowInformation extends AppCompatActivity {
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
}
