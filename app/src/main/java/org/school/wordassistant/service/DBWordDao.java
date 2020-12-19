package org.school.wordassistant.service;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import androidx.annotation.ArrayRes;

import org.school.wordassistant.entity.Word;
import org.school.wordassistant.util.DataBaseHelper;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


public class DBWordDao {


    //自己写的数据库操作方法
    //得到所有单词(直接初始化)
    /**
     * 下面两个变量外部类可以直接访问
     * **/
    public static List<Word> allWords = new ArrayList<>();

    //得到对应的数据类型对应的总的单词个数
    public static int TOTAL_WORD_NUMBER = 0;


    //含参数的构造函数
    public DBWordDao(Context mycontext){
        dataBaseHelper =new DataBaseHelper(mycontext);
        sqLiteDatabase = dataBaseHelper.openDatabase();
    }

    //创建的SQLiteDataBase数据库对象
    private static SQLiteDatabase sqLiteDatabase;
    private DataBaseHelper dataBaseHelper;

    //得到对应的生词本数据（得到对应词库类型的所有单词）
    public static void loadWords(String type){
        String sql = "select id,word,translation,phonetic from dictionary where tag='"+type+"'";

        Cursor cursor = null;
        //得到的数据集合
        try {
            cursor = sqLiteDatabase.rawQuery(sql, null);
        }catch (Exception e){
            e.printStackTrace();
        }

        //向list数组中增加word
        while(cursor.moveToNext()){
            Word word=new Word();
            word.setId(cursor.getInt(cursor.getColumnIndex("id")));
            word.setWordString(cursor.getString(cursor.getColumnIndex("word")));
            word.setPhoneticSymbol(cursor.getString(cursor.getColumnIndex("phonetic")));
            word.setDescription(cursor.getString(cursor.getColumnIndex("translation")));
            allWords.add(word);
        }

        Log.i("DbWordDao ---->","loadWords --->"+allWords.size()+"");
        TOTAL_WORD_NUMBER = allWords.size();  //得到单词个数
    }


    /**
     @param seachNum 每天背单词的数量
     @param day  背单词第几天
     */

    //使用类直接访问
    public static List<Word> getEachDayWordsList(int seachNum, int day){

        //每一次用到该方法时候需要重新设置一个list数组
        List<Word> eachDayWordsList ;
        //上一天的最后一个单词下标
        int fromNum=(day-1)*seachNum;
        //由allWords得到对应的数据
        eachDayWordsList = allWords.subList(fromNum,seachNum);  //得到对应的截取数组

        for(Word word : eachDayWordsList){
            if(word.getIsDelCollect() == 2){  //表示得到的数据是为加到熟词本
                eachDayWordsList.remove(word);
            }

        }

        return eachDayWordsList;

    }


    /**
     /type表示用户选择的词库类型，Num表示用户选择的每天要背的单词数，day表示第几天
     /该方法用来收藏用户选择的单词，把相应的单词collect字段设置为1
     */
    public boolean setCollect(String type,int num,int day){
        int fromNum=(day-1)*num;
        return true;
    }

    public int getDictionaryid(String type, int seachNum, int day){
        int fromNum=(day-1)*seachNum;
        int id=0;
        String sql="select id,word,translate,phonetic from dictionary where tag='"+type+"' limit "+fromNum+","+seachNum;

        Cursor cursor = sqLiteDatabase.rawQuery(sql,null);

        while(cursor.moveToNext()){
            id=cursor.getInt(cursor.getColumnIndex("id"));
//            word.setId(cursor.getInt(cursor.getColumnIndex("id")));
//            word.setWord(cursor.getString(cursor.getColumnIndex("word")));
//            word.setPhonetic(cursor.getString(cursor.getColumnIndex("phonetic")));
//            word.setTranslate(cursor.getString(cursor.getColumnIndex("translation")));
//            listWord.add(word);
        }

        return id;
    }
}


