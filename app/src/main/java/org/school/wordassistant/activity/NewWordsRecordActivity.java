package org.school.wordassistant.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import org.school.wordassistant.R;
import org.school.wordassistant.adapter.EasyHardSlipAdapter;
import org.school.wordassistant.entity.Word;
import org.school.wordassistant.util.StaticVariablesKeeper;

import java.util.List;


public class NewWordsRecordActivity extends AppCompatActivity {

    //定义一个适配器
    private EasyHardSlipAdapter adapter;

    //定义TextView对象
    private ListView hardWordList ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_words_record);

        hardWordList = (ListView) findViewById(R.id.hardWordList);

        Log.i("NWRA KEWL size is---->",StaticVariablesKeeper.keepHardWordList.size()+"");

        //设置适配器实现对应数据的渲染
        adapter =  new EasyHardSlipAdapter(this, StaticVariablesKeeper.keepHardWordList);

        // 注册监听器,回调用来刷新数据显示(不同的方法实现不同的数据操作,并且实现了对于不同的类实现不同方法的操作)
        adapter.setDelItemListener(new EasyHardSlipAdapter.DeleteItem() {
            @Override
            public void delete(int pos) {  //删除方法实现的是从生词本中移除对应的元素

                //得到对应的allWords中的index下标
                List<Object> innerList = StaticVariablesKeeper.keepHardWordList.get(pos);

                //得到对应的单词在allWords集合中的下标和word对象
                int getIndexAllWords  = (int)innerList.get(0);
                //得到当前allWords中对应下标的原来的words
                Word word = StaticVariablesKeeper.dbWordDao.allWords.get(getIndexAllWords);
                //更新对应的word的数据
                word.setIsDelCollect(0);  //表示设置为从记载到生词本到恢复到显示状态
                //更新allWords
                StaticVariablesKeeper.dbWordDao.allWords.set(getIndexAllWords,word);
                //移除需牢记数组中的对应单词
                StaticVariablesKeeper.keepHardWordList.remove(pos);
                //从对应的ma[集合中清除掉对应的key-value记录
                StaticVariablesKeeper.keepHardWordMap.remove(word.getId());  //根据对应的Key值移除对应的单词

                Log.i("NWRA KEWL size is -->",StaticVariablesKeeper.keepHardWordList.size()+"");

                //通知adapter更新界面
                adapter.notifyDataSetChanged();
            }

        });

//        Log.i("NWRA KEWL adapter --> ",adapter+"");

        //设置监听器
        hardWordList.setAdapter(adapter);
    }

}
