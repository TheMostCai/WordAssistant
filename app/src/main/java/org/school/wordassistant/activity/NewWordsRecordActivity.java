package org.school.wordassistant.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import org.school.wordassistant.R;
import org.school.wordassistant.adapter.EasyHardSlipAdapter;
import org.school.wordassistant.adapter.SideSlipAdapter;
import org.school.wordassistant.entity.Word;
import org.school.wordassistant.util.StaticVariablesKeeper;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class NewWordsRecordActivity extends AppCompatActivity {

    //设置的是是否是更改了对应的生词本的标记变量
    private static boolean isChangeNewWords;

    //定义一个适配器
    private EasyHardSlipAdapter adapter;

    //定义TextView对象
    ListView newWordsLv ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_words_record);


        newWordsLv = (ListView) findViewById(R.id.newWordsLv);


        //判断是否是更改了对应的数据
        if(isChangeNewWords){  //表示更改了生词本，那么实现生词本的重新加载
            //重新加载生词本，用来获得更新后的生词本
//            DataBaseHelper.loadWords();
        }

        Log.i("NewWords---->",StaticVariablesKeeper.keepEasyWordList.size()+"");

        //设置适配器实现对应数据的渲染
        adapter =  new EasyHardSlipAdapter(this, StaticVariablesKeeper.keepEasyWordList);

        // 注册监听器,回调用来刷新数据显示(不同的方法实现不同的数据操作,并且实现了对于不同的类实现不同方法的操作)
        adapter.setDelItemListener(new EasyHardSlipAdapter.DeleteItem() {
            @Override
            public void collect(int pos) {
                //得到对应的allWords中的index下标
                List<Object> innerList = StaticVariablesKeeper.keepEasyWordList.get(pos);

                //得到对应的下标和word
                int getIndexAllWords  = (int)innerList.get(0);
                //得到当前allWords中对应下标的原来的words
                Word word = StaticVariablesKeeper.dbWordDao.allWords.get(getIndexAllWords);
                //更新对应的word的数据
                word.setIsDelCollect(0);  //表示设置为删除状态
                //更新allWords
                StaticVariablesKeeper.dbWordDao.allWords.set(getIndexAllWords,word);

                //结束更新

//                Log.i("get alWords is -->",StaticVariablesKeeper.dbWordDao.allWords.get(getIndexAllWords).getIsDelCollect()+"");
//
//                Log.i("beforedel size is ->",StaticVariablesKeeper.keepChangedList.size()+"");

//
//                Log.i("afterdel size is ->",StaticVariablesKeeper.keepChangedList.size()+"");

                //移除需牢记数组中的对应单词
                StaticVariablesKeeper.keepEasyWordList.remove(pos);

                //通知adapter更新界面
                adapter.notifyDataSetChanged();
            }

//            @Override
//            public void collect(int pos) {
//
//                //得到对应的allWords中的index下标
//                List<Object> innerList = StaticVariablesKeeper.keepHardWordList.get(pos);
//
//                //得到对应的下标和word
//                int getIndexAllWords  = (int)innerList.get(0);
//                //得到当前allWords中对应下标的原来的words
//                Word word = StaticVariablesKeeper.dbWordDao.allWords.get(getIndexAllWords);
//                //更新对应的word的数据
//                word.setIsDelCollect(0);  //表示设置为删除状态
//                //更新allWords
//                StaticVariablesKeeper.dbWordDao.allWords.set(getIndexAllWords,word);
//
//                //结束更新
//
////                Log.i("get alWords is -->",StaticVariablesKeeper.dbWordDao.allWords.get(getIndexAllWords).getIsDelCollect()+"");
////
////                Log.i("beforedel size is ->",StaticVariablesKeeper.keepChangedList.size()+"");
//
////
////                Log.i("afterdel size is ->",StaticVariablesKeeper.keepChangedList.size()+"");
//
//                //移除需牢记数组中的对应单词
//                StaticVariablesKeeper.keepHardWordList.remove(pos);
//
//                //通知adapter更新界面
//                adapter.notifyDataSetChanged();
//
//            }
//
//            @Override
//            public void addColor(int pos) {
//                //to-do
//
//            }
        });


        //设置监听器
        newWordsLv.setAdapter(adapter);


    }

}
