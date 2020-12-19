package org.school.wordassistant.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.school.wordassistant.R;

public class Setting extends AppCompatActivity {

    //定义的更改生词本tv
    private TextView tv_changeWord;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);


        //获得组件
        tv_changeWord = (TextView) findViewById(R.id.tv_changeWord);

        //点击更改生词本的时候跳转
        tv_changeWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent to_wordChange = new Intent(Setting.this,WordChangeActivity.class);
                startActivity(to_wordChange);
            }
        });



    }
}