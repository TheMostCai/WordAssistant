package org.school.wordassistant.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.school.wordassistant.R;
import org.school.wordassistant.adapter.SideSlipAdapter;
import org.school.wordassistant.entity.Word;
import org.school.wordassistant.service.DBWordDao;
import org.school.wordassistant.util.StaticVariablesKeeper;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    //定义静态常量UPDATE
    private static final int UPDATE = 666;

    //创建的一个handler对象
    private MyHandler mHandler;
    //定义组件成员变量
    private TextView tv_setting;
    //定义的backtextview
    private TextView tv_back;
    //定义的是frontview
    private TextView tv_front;
    //tv_days
    private TextView tv_days;
    //定义的是最下方的BUtton
    private Button bu_over;
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


        //初始化一个handler对象
        mHandler = new MyHandler();//创建Handler

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

                //直接更新allWords集合实现当次进入app的总体的数据的更新
                //根据当前天数以及每页单词个数得到的在allWords集合中的Index
                int getIndexAllWords = 0;
                getIndexAllWords = (currentDay-1) * displayNumberSinglePage + pos;
                //得到当前allWords中对应下标的原来的words
                Word word = StaticVariablesKeeper.dbWordDao.allWords.get(getIndexAllWords);

                //更新对应的word的数据
                word.setIsDelCollect(2);  //表示设置为删除状态

                StaticVariablesKeeper.dbWordDao.allWords.set(getIndexAllWords,word);

                Log.i("get alWords is -->",StaticVariablesKeeper.dbWordDao.allWords.get(getIndexAllWords).getIsDelCollect()+"");

                Log.i("beforedel size is ->",StaticVariablesKeeper.keepHardWordList.size()+"");

                //保存更改状态单词，用于实现最后退出app的时候，更新数据库
                List<Object> innerList = new ArrayList<>();
                innerList.add(getIndexAllWords,word);
                StaticVariablesKeeper.keepHardWordList.add(innerList);

                Log.i("afterdel size is ->",StaticVariablesKeeper.keepHardWordList.size()+"");

                //通知adapter更新界面
                adapter.notifyDataSetChanged();
            }

            @Override
            public void collect(int pos) {

                //直接更新allWords集合实现当次进入app的总体的数据的更新
                //根据当前天数以及每页单词个数得到的在allWords集合中的Index
                int getIndexAllWords = 0;
                getIndexAllWords = (currentDay-1) * displayNumberSinglePage + pos;
                //得到当前allWords中对应下标的原来的words
                Word word = StaticVariablesKeeper.dbWordDao.allWords.get(getIndexAllWords);
                //更新对应的word的数据
                word.setIsDelCollect(1);  //表示设置为收藏状态
                StaticVariablesKeeper.dbWordDao.allWords.set(getIndexAllWords,word);

                Log.i("beforecollec size is ->",StaticVariablesKeeper.keepEasyWordList.size()+"");

                //保存更改状态单词，用于实现最后退出app的时候，更新数据库
                List<Object> innerList = new ArrayList<>();
                innerList.add(getIndexAllWords,word);
                StaticVariablesKeeper.keepHardWordList.add(innerList);

                Log.i("aftercollec size is ->",StaticVariablesKeeper.keepEasyWordList.size()+"");

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
        bu_over=(Button) findViewById(R.id.bu_over);
        lv_words = (ListView) findViewById(R.id.lv_words);

        //设置当前显示的天数
        tv_days.setText("第" + currentDay + "天");

        //设置最开始无法跳转到下一页，当完成按钮被点击后才可以点击
        tv_front.setEnabled(false);
    }


    //集合很多的设置监听器的方法
    private void setListener(){

        //判断完成单词按钮是否点击过
        bu_over.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //点击过后设置tv_front可以点击，并将已被过的单词天数加1
                StaticVariablesKeeper.flagHasRecitedDay++;
                tv_front.setEnabled(true);
                bu_over.setEnabled(false);
                bu_over.setText("该页单词您已学完");
                Toast.makeText(MainActivity.this,"现在您可以进入下一天的学习啦！",Toast.LENGTH_SHORT).show();
            }
        });

        //点击的时候实现跳转
        tv_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toSetting = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(toSetting);
                Log.i("MainActivity    ----> ","onCreate() intent to ------>"+toSetting);
            }
        });


        //实现的是点击向后向前的按钮实现更换单词进度和显示的天数，并且实现更新渲染出来的数据
        tv_front.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //先判断是不是在对应的天数范围内，如果在进行下一个天数数据的获得并通知adapter进行更新
                if(currentDay < totalDays){

                    //表示当前天数加1
                    currentDay += 1;
                    //得到目前的天数
                    setDaysSting = "第" + currentDay + "天";
                    //显示目前的天数
                    tv_days.setText(setDaysSting);

//                    Log.i("Main eachDayListMA  dis-->",displayNumberSinglePage+"");
//                    Log.i("Main eachDayListMA currentDay -->",currentDay+"");
//                    Log.i("allWords size is -->",DBWordDao.allWords.size()+"");

                    //得到下一天数据
                    eachDayListMA = StaticVariablesKeeper.dbWordDao.getEachDayWordsList(displayNumberSinglePage,currentDay);

                    Log.i("Main eachDayListMA  -->",eachDayListMA.size()+"");

                    adapter.setList(eachDayListMA);  //更新adapter中的数据

                    //通知更新handler实现更新数据和UI
                    Message msg = mHandler.obtainMessage(UPDATE);
                    mHandler.sendMessage(msg);

                    //判断点击后退按钮时，当前页面天数是否小于已背完的最大天数，如果小于，则下方按钮不可点击，否则可以
                    //currentDay较小，则表示该页单词已经学完，将下方按钮设置不可点击，上方可以点击
                    if (currentDay<StaticVariablesKeeper.flagHasRecitedDay){
                        bu_over.setEnabled(false);
                        bu_over.setText("该页单词您已学完");
                        tv_front.setEnabled(true);
                    }else{
                        bu_over.setEnabled(true);
                        bu_over.setText("已完成当天单词请点击此处");
                        tv_front.setEnabled(false);
                    }

                }else{

                    Toast.makeText(MainActivity.this,"已经是最后一天了",Toast.LENGTH_SHORT).show();
                }
            }
        });


        //实现点击后退按钮天数减少一天，并实现的是通过handler控制器实现对应的数据的更新和UI界面的更新
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

                    //得到前一天的数据
                    eachDayListMA = StaticVariablesKeeper.dbWordDao.getEachDayWordsList(displayNumberSinglePage,currentDay);

//                    Log.i("Main eachDayListMA  -->",eachDayListMA.size()+"");

                    adapter.setList(eachDayListMA);  //更新adapter中的数据

                    //通知更新handler实现更新数据和UI
                    Message msg = mHandler.obtainMessage(UPDATE);
                    mHandler.sendMessage(msg);

                    bu_over.setEnabled(false);
                    bu_over.setText("该页单词您已学完");
                    tv_front.setEnabled(true);

                }else{
                    Toast.makeText(MainActivity.this,"已经是第一天了",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    //创建的一个handle私有类实现对于监听器中点击下一天按钮之后实现UI界面的更改
    private class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            switch(msg.what) {
                case UPDATE://在收到消息时，对之后的界面进行更新
//                    Log.i("handler adapter --->",adapter.toString());
                    adapter.notifyDataSetChanged();
                    break;
            }
        }
    }


}

