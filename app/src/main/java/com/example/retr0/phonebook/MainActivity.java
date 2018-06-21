package com.example.retr0.phonebook;

import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    //UI Object
    private TextView txt_topbar;
    private TextView txt_contact;
    private TextView txt_record;
    private TextView txt_statistic;

    private FrameLayout ly_content;
    private Button add_user_button;
    private Switch switch_unpick;

    //Fragment Object
    private MyFragment fg1,fg2,fg3;
    private FragmentManager fManager;

    private WordsNavigation word;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        fManager = getFragmentManager();
        bindViews();
        txt_contact.performClick();   //模拟一次点击，即进去后选择第一项

    }


    //UI组件初始化与事件绑定
    @SuppressLint("WrongViewCast")
    private void bindViews() {
        txt_topbar = (TextView) findViewById(R.id.txt_topbar);
        txt_contact = (TextView) findViewById(R.id.txt_contact);
        txt_record = (TextView) findViewById(R.id.txt_record);
        txt_statistic = (TextView) findViewById(R.id.txt_statistic);
        ly_content = (FrameLayout) findViewById(R.id.ly_content);

        //switch_unpick = (Switch)LayoutInflater.from(MainActivity.this).inflate(R.layout.record_content, null).findViewById(R.id.switch_unpick);
        add_user_button =(Button)findViewById(R.id.floatingActionButton);
        word = (WordsNavigation) LayoutInflater.from(MainActivity.this).inflate(R.layout.contact_content, null).findViewById(R.id.words);

        txt_contact.setOnClickListener(this);
        txt_record.setOnClickListener(this);
        txt_statistic.setOnClickListener(this);
    }

    //重置所有文本的选中状态
    private void setSelected(){
        txt_contact.setSelected(false);
        txt_record.setSelected(false);
        txt_statistic.setSelected(false);
    }

    //隐藏所有Fragment
    private void hideAllFragment(FragmentTransaction fragmentTransaction){
        if(fg1 != null)fragmentTransaction.hide(fg1);
        if(fg2 != null)fragmentTransaction.hide(fg2);
        if(fg3 != null)fragmentTransaction.hide(fg3);
    }


    //响应点中不同fragment后的动作，将要加载的内容绘制到ly_content中
    @Override
    public void onClick(View v) {
        FragmentTransaction fTransaction = fManager.beginTransaction();
        //先隐藏已显示的fragment
        hideAllFragment(fTransaction);
        switch (v.getId()){
            case R.id.txt_contact:
                setSelected();
                txt_contact.setSelected(true);
                // 若fragment未初始化，则初始化并发送信息到MyFragment类，选择绘制对应的界面。否则直接显示已绘制的界面。
                if(fg1 == null){
                    fg1 = new MyFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("str",getResources().getString(R.string.contact));
                    fg1.setArguments(bundle);
                    fTransaction.add(R.id.ly_content,fg1);
                }else{
                    fTransaction.show(fg1);
                }
                break;
            case R.id.txt_record:
                setSelected();
                txt_record.setSelected(true);
                if(fg2 == null){
                    fg2 = new MyFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("str",getResources().getString(R.string.record));
                    fg2.setArguments(bundle);
                    fTransaction.add(R.id.ly_content,fg2);
                }else{
                    fTransaction.show(fg2);
                }
                break;
            case R.id.txt_statistic:
                setSelected();
                txt_statistic.setSelected(true);
                if(fg3 == null){
                    fg3 = new MyFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("str",getResources().getString(R.string.statistic));
                    fg3.setArguments(bundle);
                    fTransaction.add(R.id.ly_content,fg3);
                }else{
                    fTransaction.show(fg3);
                }
                break;
        }
        fTransaction.commit();
    }

    public  void click_add_user(View view) {
        Intent intent = new Intent();
        intent.setClass(MainActivity.this,UserInformation.class);
        startActivity(intent);
    }


}
