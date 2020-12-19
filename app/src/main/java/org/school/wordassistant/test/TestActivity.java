package org.school.wordassistant.test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import org.school.wordassistant.R;
import org.school.wordassistant.entity.Word;
import org.school.wordassistant.service.DBWordDao;

import java.util.List;


public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

//        //创建一个db对象
//        DBWordDao dbWordDao = new DBWordDao(this);
//        List<Word> getResults = dbWordDao.getWordsList("gk",50,2);
//        for (Word word : getResults){
//            System.out.println(word.getDescription() + " : " + word.getWordString() + " : " + word.getPhoneticSymbol());
//        }
    }
}
