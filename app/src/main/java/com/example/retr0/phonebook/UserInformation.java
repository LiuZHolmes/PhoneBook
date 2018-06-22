package com.example.retr0.phonebook;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UserInformation extends AppCompatActivity {

    private Button return_btn;
    private Button ok_button;
    private int size;
    private EditText etName,etHome,etMobile,etAdd,etBirth;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_information);
        pref = getSharedPreferences("ContactData", MODE_PRIVATE);

        size=pref.getInt("size",0);

        return_btn = findViewById(R.id.return_button);
        ok_button= findViewById(R.id.ok_button);

        etName=findViewById(R.id.edit_name);
        etHome=findViewById(R.id.edit_home);
        etMobile=findViewById(R.id.edit_mobile);
        etAdd=findViewById(R.id.edit_add);
        etBirth=findViewById(R.id.edit_birth);


    }
    public void return_main_page(View view) {
        Intent intent = new Intent();
        intent.setClass(UserInformation.this,MainActivity.class);
        startActivity(intent);
    }
    public void check_ok(View view) {
        String name=etName.getText().toString();
        String home=etHome.getText().toString();
        String mobile=etMobile.getText().toString();
        String add=etAdd.getText().toString();
        String birth=etBirth.getText().toString();

        editor=pref.edit();
        editor.putString("contact_name"+size,name);
        editor.putString("contact_home"+size,home);
        editor.putString("contact_mobile"+size,mobile);
        editor.putString("contact_add"+size,add);
        editor.putString("contact_birth"+size,birth);
        editor.putInt("size",size+1);
        editor.putString("contactName",name);
        editor.putString("contactHome",home);
        editor.putString("contactMobile",mobile);
        editor.putString("contactAdd",add);
        editor.putString("contactBirth",birth);
        editor.apply();

        DisplayToast(getResources().getString(R.string.add_succeed));

        Intent intent = new Intent();
        intent.setClass(UserInformation.this,UserShowInformation.class);
        startActivity(intent);
    }
    public void DisplayToast(String s) {
        Toast toast=Toast.makeText(this,s,Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP,0,1020);
        toast.show();
    }
}
