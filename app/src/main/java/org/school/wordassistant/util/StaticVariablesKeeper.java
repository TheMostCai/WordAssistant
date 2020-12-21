package org.school.wordassistant.util;

import org.school.wordassistant.entity.Word;
import org.school.wordassistant.service.DBWordDao;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


//用来保存需要使用的静态变量（主要是实现的同一个地址访问，节约系统开销）
public class StaticVariablesKeeper {

    //定义的存放每一次放入熟词本的单词数组（用于最后的退出应用之前的更新）
    public static List<List<Object>> keepHardWordList = new ArrayList<>();
    //用来实现添加新的删除元素时候的判断
    public static Map<Integer,Word> keepHardWordMap = new HashMap<>();



    //定义的存放每一次放入生词本的单词数组
    public static List<List<Object>> keepEasyWordList = new ArrayList<>();
    //用来实现添加新的收藏元素时候的判断
    public static Map<Integer,Word> keepEasyWordMap = new HashMap<>();



    //保存的是dao对象
    public static DBWordDao dbWordDao = null;

    //设置一个变量标记当前已经背到的天数（初始化需要放到setplan中进行，默认值是1）
    public static int flagHasRecitedDay = 1;
}
