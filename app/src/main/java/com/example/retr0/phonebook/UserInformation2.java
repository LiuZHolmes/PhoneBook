package com.example.retr0.phonebook;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class UserInformation2 extends AppCompatActivity {
    static boolean isEdit = false;
    private Button return_btn;
    private android.widget.Button ok_button;
    private int size;
    private EditText etName,etHome,etMobile,etAdd,etBirth;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private int num;
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

        String temp1 = pref.getString("contactName", "NULL");
        etName.setText(temp1);
        String temp2 = pref.getString("contactHome", "NULL");
        etHome.setText(temp2);
        String temp3 = pref.getString("contactMobile", "NULL");
        etMobile.setText(temp3);
        String temp4 = pref.getString("contactAdd", "NULL");
        etAdd.setText(temp4);
        String temp5 = pref.getString("contactBirth", "NULL");
        etBirth.setText(temp5);
        num = pref.getInt("contactSize", 0);
        /*String find1, find2, find3, find4, find5;
        for(int i = 0; i < size; i++) {
            find1 = pref.getString("contact_name"+i, "NULL");
            find2 = pref.getString("contact_home"+i, "NULL");
            find3 = pref.getString("contact_mobile"+i, "NULL");
            find4 = pref.getString("contact_add"+i, "NULL");
            find5 = pref.getString("contact_birth"+i, "NULL");
            if(find1.equals(temp1) && find2.equals(temp2) && find3.equals(temp3) && find4.equals(temp4)&& find5.equals(temp5)) {
                num = i;
            }
            else {
                num = i+1;
            }
        }*/
    }
    public void return_main_page(View view) {
        Intent intent = new Intent();
        intent.setClass(UserInformation2.this,MainActivity.class);
        startActivity(intent);
    }
    public void check_ok(View view) {
        String name=etName.getText().toString();
        String home=etHome.getText().toString();
        String mobile=etMobile.getText().toString();
        String add=etAdd.getText().toString();
        String birth=etBirth.getText().toString();

        editor=pref.edit();

        editor.putString("contact_name" + num, name);
        editor.putString("contact_home" + num, home);
        editor.putString("contact_mobile" + num, mobile);
        editor.putString("contact_add" + num, add);
        editor.putString("contact_birth" + num, birth);
        editor.putInt("contact_size" + num, num);

        editor.putString("contactName",name);
        editor.putString("contactHome",home);
        editor.putString("contactMobile",mobile);
        editor.putString("contactAdd",add);
        editor.putString("contactBirth",birth);
        editor.putInt("contactSize" + num, num);
        editor.apply();

        DisplayToast(getResources().getString(R.string.add_succeed));
        isEdit = true;

        Intent intent = new Intent();
        intent.setClass(UserInformation2.this,UserShowInformation.class);
        startActivity(intent);
    }
    public void DisplayToast(String s) {
        Toast toast=Toast.makeText(this,s,Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP,0,1020);
        toast.show();
    }
}
