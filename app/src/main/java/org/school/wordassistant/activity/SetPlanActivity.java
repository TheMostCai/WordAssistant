package org.school.wordassistant.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.school.wordassistant.R;
import org.school.wordassistant.util.DataBaseHelper;

public class SetPlanActivity extends AppCompatActivity {

    //定义每天显示单词数(默认值是20个)
    private static int wordsDisplayDay = 20;

    //设置的组件变量
    //定义数字选择器
    private NumberPicker numberPicker;
    //定义的显示用户选择的单词数量的TextView
    private TextView wordsDisplayOneDay;
    //定义的预计完成天数变量
    private TextView finishDays;

    //定义的开始背单词按钮
    private Button btn_set_plane;

    //定义的整数类型需要完成的天数
    private int finishDaysNumber;

    //得到当前activity中总的天数
    private int currentToTalWords;


    //定义spinner组件对象
    private Spinner spinner ;
    //定义的选择的类型字符串
    private String chooseType;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_plan);

        //得到总的天数
//        currentToTalWords = DataBaseHelper.TOTAL_WORD_NUMBER;

        //对象的初始化
        innitialize();

        //设置监听器
        setLisenter();

    }



    //对象初始化方法
    private void innitialize(){

        //获得布局文件中的组件
        numberPicker = (NumberPicker)findViewById(R.id.np_word_number);
        wordsDisplayOneDay = (TextView) findViewById(R.id.displayNumber);
        finishDays =  (TextView) findViewById(R.id.finishDays);
        btn_set_plane  = (Button) findViewById(R.id.btn_set_plane);
        spinner = (Spinner) findViewById(R.id.wordType);

        //初始化numberPicker组件对应的最大最小值
        //设置最大值
        numberPicker.setMaxValue(150);
        //设置最小值
        numberPicker.setMinValue(20);
        //设置当前值
        numberPicker.setValue(20);

        //初始化的时候得到的按照默认值需要完成的天数
        finishDaysNumber = currentToTalWords % wordsDisplayDay == 0
                ? currentToTalWords /  wordsDisplayDay : currentToTalWords / wordsDisplayDay + 1;

    }


    //设置监听器的方法
    private void setLisenter(){

        //设置滑动监听，获得对应的天数和当前天数的值
        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int oldvalue, int newvalue){
                Log.i("get i and  i1   ----> ",oldvalue + " " + newvalue);
                wordsDisplayOneDay.setText(newvalue+"个");  //设置最新选择的单词量
                //计算需要完成的天数
                finishDaysNumber = currentToTalWords % newvalue == 0 ? currentToTalWords /  newvalue : currentToTalWords / newvalue + 1;
                finishDays.setText(finishDaysNumber+"天");
                wordsDisplayDay = newvalue;  //设置用于传递的每页显示的单词个数的变量
            }
        });



        //设置的显示对应的点击开始背单词按钮之后跳转逻辑
        btn_set_plane.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toMainActivity  = new Intent(SetPlanActivity.this,MainActivity.class);
                //使用Bundle传值
                Bundle bundle = new Bundle();
                //得到spinner的值
                String getSpinnerString = spinner.getSelectedItem().toString();
                if(getSpinnerString.equals("单词类型")){  //表示用户没有选择背单词类型，那么不让用户点击
                    Toast.makeText(SetPlanActivity.this,"请选择词库类型！",Toast.LENGTH_SHORT).show();
                }else {
                    bundle.putString("wordType",getType(getSpinnerString));
                    bundle.putInt("single_display_number", wordsDisplayDay);
                    bundle.putInt("total_days", finishDaysNumber);
                    toMainActivity.putExtras(bundle);  //放到intent中
                    startActivity(toMainActivity);
                }
            }
        });

    }


    //设置的根据spinner选择的类型得到对应的代号
    private String getType(String type){
        String style = "";
        if(type.equals("高中(gk)")){
            style = "gk";
        }else if(type.equals("大学英语四级(CET4)")){
            style = "cet4";
        }else if(type.equals("大学英语六级(CET6)")){
            style = "cet6";
        }else if(type.equals("雅思(IELTS)")){
            style = "ielts";
        }else if(type.equals("托福(TOEFL)")){
            style = "toefl";
        }else if(type.equals("GRE")){
            style = "gre";
        }
        return style;
    }

}