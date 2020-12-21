package org.school.wordassistant.service;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import androidx.annotation.ArrayRes;

import org.school.wordassistant.entity.Word;
import org.school.wordassistant.util.DataBaseHelper;

import java.util.ArrayList;
import java.util.Iterator;
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

    //创建的SQLiteDataBase数据库对象
    private static SQLiteDatabase sqLiteDatabase;
    private static DataBaseHelper dataBaseHelper;

    //设置的返回的指向不同地址的子集合
    private static ArrayList<Word> returnListEvtime;


    //含参数的构造函数
    public DBWordDao(Context mycontext){

        if(dataBaseHelper == null){
            dataBaseHelper =new DataBaseHelper(mycontext);
        }

        if(sqLiteDatabase == null){
            sqLiteDatabase = dataBaseHelper.openDatabase();
        }
    }


    //创建一个得到allWords的方法
    public List<Word> getAllWords(){
        return allWords;
    }


    //更新allWords对象的数据
    public void setAllWords(List<Word> wordList){
        allWords = wordList;
    }

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

        //判断allwords是不是空的
        if(!allWords.isEmpty()){
            allWords.clear();
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


    //得到每一天单词的方法
    /**
     * @param seachNum  查阅几个单词
     * @param day 第几天（当前天数）
     * **/
    public static List<Word> getEachDayWordsList(int seachNum, int day){
        //每一次用到该方法时候需要重新设置一个list数组
        List<Word> eachDayWordsList ;
        //上一天的最后一个单词下标
        int fromNum=(day-1)*seachNum;

        //由allWords得到对应的数据
        eachDayWordsList = allWords.subList(fromNum,fromNum + seachNum);  //得到对应的截取数组

        Log.i("DBWordDao size1 is -->",eachDayWordsList.size()+"");
        Log.i("allWords1 is -->",DBWordDao.allWords.size()+"");

        returnListEvtime = new ArrayList<>();

        //使用Iterator实现数组的新建（原因是使用sublist得到的子数组对应的地址没有变化，也就是说更改eachDayWordsList还是会更改到对应的allWords数组）
        Iterator iterator = eachDayWordsList.iterator();
        while(iterator.hasNext()){ //表示iterator对象迭代遍历
            Word word = (Word) iterator.next();

            if (word.getIsDelCollect()!=2){
                returnListEvtime.add(word);
            }else {
                Log.i("get word isdelCollect --> ",word.getIsDelCollect()+"");
            }

        }

//        iterator = returnListEvtime.iterator();
//        while(iterator.hasNext()){ //表示iterator对象迭代遍历
//            Word word = (Word) iterator.next();
//            if(word.getIsDelCollect() == 2){
//                iterator.remove();
//            }
//        }

        Log.i("DBWordDao size2 is -->",eachDayWordsList.size()+"");
        Log.i("allWords2 is -->",DBWordDao.allWords.size()+"");

        return returnListEvtime;
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


