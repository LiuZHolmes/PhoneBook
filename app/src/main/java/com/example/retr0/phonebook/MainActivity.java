package com.example.retr0.phonebook;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    //UI Object
    private TextView txt_topbar;
    private TextView txt_contact;
    private TextView txt_record;
    private TextView txt_statistic;
    private FrameLayout ly_content;

    //Fragment Object
    private MyFragment fg1,fg2,fg3;
    private FragmentManager fManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        fManager = getFragmentManager();
        bindViews();
        txt_contact.performClick();   //模拟一次点击，既进去后选择第一项
    }

    //UI组件初始化与事件绑定
    private void bindViews() {
        txt_topbar = (TextView) findViewById(R.id.txt_topbar);
        txt_contact = (TextView) findViewById(R.id.txt_contact);
        txt_record = (TextView) findViewById(R.id.txt_record);
        txt_statistic = (TextView) findViewById(R.id.txt_statistic);
        ly_content = (FrameLayout) findViewById(R.id.ly_content);

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



    @Override
    public void onClick(View v) {
        FragmentTransaction fTransaction = fManager.beginTransaction();
        switch (v.getId()){
            case R.id.txt_contact:
                setSelected();
                txt_contact.setSelected(true);
                if(fg1 == null){
                    fg1 = new MyFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("str","联系人");
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
                    bundle.putString("str","通话记录");
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
                    bundle.putString("str","统计");
                    fg3.setArguments(bundle);
                    fTransaction.add(R.id.ly_content,fg3);
                }else{
                    fTransaction.show(fg3);
                }
                break;
        }
        fTransaction.commit();
    }
}
