package org.school.wordassistant.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.school.wordassistant.R;
import org.school.wordassistant.entity.Word;
import org.school.wordassistant.service.DBWordDao;
import org.school.wordassistant.util.StaticVariablesKeeper;

import java.util.List;

public class SettingActivity extends AppCompatActivity {

    //定义的更改生词本tv
    private TextView tv_changeWord;
    //定义的查看生词本组件
    private TextView btn_hardWord;
    //定义的查看熟词本组件
    private TextView btn_easyWord;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        //组件初始化
        innitialized();

        //设置监听器
        setListener();

    }


    //组件初始化方法
    public void innitialized(){
        //获得组件
        tv_changeWord = (TextView) findViewById(R.id.tv_changeWord);
        btn_hardWord = (TextView) findViewById(R.id.btn_hardWord);
        btn_easyWord = (TextView) findViewById(R.id.btn_easyWord);
    }


    //设置的监听器初始化方法
    public void setListener(){

        //点击更改计划时候跳转
        tv_changeWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent to_wordChange = new Intent(SettingActivity.this,SetPlanActivity.class);
                startActivity(to_wordChange);
            }
        });


        //点击查看生词本之后跳转到对应的界面
        btn_hardWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(SettingActivity.this,NewWordsRecordActivity.class);
                //结束当前界面
//                finish();
                startActivity(intent);
            }
        });



        //设置的查看熟次本跳转逻辑
        btn_easyWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingActivity.this,FamiliarWordsActivity.class);
                //结束当前界面
//                finish();
                startActivity(intent);
            }
        });

    }
}