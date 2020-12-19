package org.school.wordassistant.operation;


import org.school.wordassistant.entity.Word;

import java.util.ArrayList;
import java.util.List;

//设置的连接Sqlite数据库的类
public class DataBaseHelper {

    //得到所有单词
    public static List<Word> allWords;

    //得到对应的总的数据个数
    public static int TOTAL_WORD_NUMBER = 6600;

    //得到对应的生词本数据
    public static void loadWords(){
        allWords = new ArrayList<>();
        TOTAL_WORD_NUMBER = 6600;
    }






}
