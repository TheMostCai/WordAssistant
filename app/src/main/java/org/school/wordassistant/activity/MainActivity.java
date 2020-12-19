package org.school.wordassistant.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.school.wordassistant.R;
import org.school.wordassistant.adapter.SideSlipAdapter;
import org.school.wordassistant.entity.Word;
import org.school.wordassistant.service.DBWordDao;
import org.school.wordassistant.util.StaticVariablesKeeper;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //定义组件成员变量
    private TextView tv_setting;
    //定义的backtextview
    private TextView tv_back;
    //定义的是frontview
    private TextView tv_front;
    //tv_days
    private TextView tv_days;
    //设置的天数显示的string字符串
    private String setDaysSting;
    //当前显示的天数
    private int currentDay = 1;
    //定义的是每一页显示单词数
    private int displayNumberSinglePage;
    //定义的是总共显示的天数
    private int totalDays = 0;

    //定义的是ListView组件变量
    private ListView lv_words;
    //定义每一天显示的存储数据数组
    private List<Word> eachDayListMA = new ArrayList<>();
    //定义一个适配器
    private SideSlipAdapter adapter;

    //定义词库类型
    private String getType = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //创建一个db对象
        if(StaticVariablesKeeper.dbWordDao == null) {
            StaticVariablesKeeper.dbWordDao = new DBWordDao(this);
        }

        //初始化组件
        initialize();

        //得到的传过来的每一页显示的个数
        Bundle bundle = getIntent().getExtras();

        try {
            displayNumberSinglePage = bundle.getInt("single_display_number");
            totalDays = bundle.getInt("total_days");
            getType = bundle.getString("wordType");

          Log.i("MainAcitivity ------>  ","onCreate --->  设置每天单词数量是：" + displayNumberSinglePage +
                    "    总天数是：" + totalDays+"   得到的词库类型：  " + getType);

        }catch (Exception e){
            e.printStackTrace();
        }  //用于设置每个页面显示单词数(实现分页查询)


        //数据库对象初始化（第一次获得数据库）
        try {
            //加载对应类型的单词数据
            StaticVariablesKeeper.dbWordDao.loadWords(getType);  //首次加载应用缓冲数据

            //得到当天数据
            eachDayListMA = StaticVariablesKeeper.dbWordDao.getEachDayWordsList(displayNumberSinglePage,currentDay);

        }catch (Exception e){
            e.printStackTrace();  //打印对应的报错信息
        }



        //适配器对象的初始化
        adapter =  new SideSlipAdapter(this,eachDayListMA);

        // 注册监听器,回调用来刷新数据显示(不同的方法实现不同的数据操作,并且实现了对于不同的类实现不同方法的操作)
        adapter.setDelItemListener(new SideSlipAdapter.DeleteItem() {
            @Override
            public void delete(int pos) {
                eachDayListMA.remove(pos);  //直接移除数据
                StaticVariablesKeeper.keepChangedList.add(eachDayListMA.get(pos));  //保存更改状态单词
                //更新
                adapter.notifyDataSetChanged();  //通知adapter更新界面
            }

            @Override
            public void collect(int pos) {
                // to-do

            }

            @Override
            public void addColor(int pos) {
                //to-do

            }
        });

        //给listView设置相应的适配器
        lv_words.setAdapter(adapter);

        //设置监听器
        setListener();

    }

    //初始化组件方法
    public void initialize(){

        //初始化组件
        tv_setting = (TextView) findViewById(R.id.tv_setting);
        tv_back = (TextView) findViewById(R.id.tv_back);
        tv_days = (TextView) findViewById(R.id.tv_days);
        tv_front = (TextView) findViewById(R.id.tv_front);
        lv_words = (ListView) findViewById(R.id.lv_words);

        //设置当前显示的天数
        tv_days.setText("第" + currentDay + "天");
    }

    //集合很多的设置监听器的方法
    private void setListener(){

        //点击的时候实现跳转
        tv_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toSetting = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(toSetting);
                Log.i("MainActivity    ----> ","onCreate() intent to ------>"+toSetting);
            }
        });


        //实现的是点击向后向前的按钮实现更换单词进度和显示的天数
        tv_front.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //先判断是不是在指定的范围内
                if(currentDay < totalDays){
                    //表示当前天数加1
                    currentDay += 1;
                    //得到目前的天数
                    setDaysSting = "第" + currentDay + "天";
                    //显示目前的天数
                    tv_days.setText(setDaysSting);
                }else{
                    Toast.makeText(MainActivity.this,"已经是最后一天了",Toast.LENGTH_SHORT).show();
                }
            }
        });


        //实现点击后退按钮天数减少一天
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //先判断是不是在指定的范围内
                if(currentDay > 1){
                    //表示当前天数加1
                    currentDay -= 1;
                    //得到目前的天数
                    setDaysSting = "第" + currentDay + "天";
                    //显示目前的天数
                    tv_days.setText(setDaysSting);
                }else{
                    Toast.makeText(MainActivity.this,"已经是第一天了",Toast.LENGTH_SHORT).show();
                }
            }
        });


    }





}