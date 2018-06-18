package com.example.retr0.phonebook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class UserInformation extends AppCompatActivity {

    private Button return_btn;
    private Button ok_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_information);
        return_btn = (Button)findViewById(R.id.return_button);
        ok_button=(Button)findViewById(R.id.ok_button);
    }
    public void return_main_page(View view) {
        Intent intent = new Intent();
        intent.setClass(UserInformation.this,MainActivity.class);
        startActivity(intent);
    }
    public void check_ok(View view) {
        DisplayToast("添加成功");
    }
    public void DisplayToast(String s) {
        Toast toast=Toast.makeText(this,s,Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP,0,1020);
        toast.show();
    }
}
