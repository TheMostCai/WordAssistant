package org.school.wordassistant.util;

import org.school.wordassistant.entity.Word;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ListKeeper {
    //定义 收藏 常量
    public static int COLLEC_NUMBER = 1;
    //定义 删除 常量
    public static int DELETE_NUMBER = 2;

    //定义的存放每一次更改的单词（用于最后的退出应用之前的更新）
    public static List<Word> keepChangedList = new ArrayList<>();
}
