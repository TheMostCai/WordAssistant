package org.school.wordassistant.util;

import org.school.wordassistant.entity.Word;
import org.school.wordassistant.service.DBWordDao;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StaticVariablesKeeper {

    //定义的存放每一次放入熟词本的单词数组（用于最后的退出应用之前的更新）
    public static List<List<Object>> keepHardWordList = new ArrayList<>();

    //定义的存放每一次放入生词本的单词数组
    public static List<List<Object>> keepEasyWordList = new ArrayList<>();

    //保存的是dao对象
    public static DBWordDao dbWordDao = null;

//    //设置保存每一个页面底部对应的按钮是否被点击过
//    public static boolean isClicked[];  //不初始化，初始化操作放到SettingPlan中实现


    //设置一个变量标记当前已经背到的天数（初始化需要放到setplan中进行）
    public static int flagHasRecitedDay = 1;
}
