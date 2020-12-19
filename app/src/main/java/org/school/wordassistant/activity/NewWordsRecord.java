package org.school.wordassistant.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import org.school.wordassistant.R;
import org.school.wordassistant.operation.DataBaseHelper;

public class NewWordsRecord extends AppCompatActivity {

    //设置的是是否是更改了对应的生词本的标记变量
    private static boolean isChangeNewWords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_words_record);

        //判断是否是更改了对应的数据
        if(isChangeNewWords){  //表示更改了生词本，那么实现生词本的重新加载
            //重新加载生词本，用来获得更新后的生词本
            DataBaseHelper.loadWords();
        }


    }
}
