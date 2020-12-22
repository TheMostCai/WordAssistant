package org.school.wordassistant.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.LinearLayout;

import org.school.wordassistant.R;

public class EntryActivity extends AppCompatActivity {

    //表示进入的时候判断是否是初次进入
    private SharedPreferences pref;
    //用于判断是否是第一次运行，运行后变为false
    private boolean isFirst = true;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);

        //设置的导航页界面
        isFirstEntry();

    }


    //设置的导航页面
    public void isFirstEntry(){
        LinearLayout layoutWelcome = (LinearLayout) findViewById(R.id.layoutwelcome);
        //设置渐变效果
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.1f, 1.0f);
        //设置渐变效果持续时间
        alphaAnimation.setDuration(3000);
        //给导航页面设置的对应的渐变效果对象
        layoutWelcome.startAnimation(alphaAnimation);
        //给渐变动画对象设置的监听器用来实现动画加载完毕前判断是否是第一次登录

        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                judgeIntent();
            }

            //设置使用sharedPreference对象检测是不是首次进入的判断方法
            private void judgeIntent() {

                //创建SharedPreferences对象
                pref = getSharedPreferences("isFirst", MODE_PRIVATE);

                Log.i("EntryActivity pref ---> " ,pref + "");

                //如果第一次运行，无isFirstIn值，自动获取第二个参数为默认值（默认值为true）
                isFirst = pref.getBoolean("isFirstIn", true);

                Log.i("EntryActivity isFirst ---> " ,isFirst+"");

                if (isFirst) {//如果为true，进入if语句
                    intent = new Intent(EntryActivity.this, SetPlanActivity.class);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putBoolean("isFirstIn", false);//保存isFirstIn值为false
                    editor.commit();//提交数据
                } else {
                    //如果为false，说明程序已经运行过，直接跳转到主页面
                    intent = new Intent(EntryActivity.this, SetPlanActivity.class);
                }

                startActivity(intent);
                finish();
            }
        });


    }

}
