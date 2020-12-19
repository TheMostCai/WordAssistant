package org.school.wordassistant.entity;


/**
 * 定义的是单词类别
 * **/
public class Word {
    private int id;
    //单词字符串
    private String wordString;
    //单词描述
    private String description;
    //单词对应的音标
    private String phoneticSymbol;
    //设置的是否被收藏or删除
    private int isDelCollect;


    //生成的对应的set和get方法
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWordString() {
        return wordString;
    }

    public void setWordString(String wordString) {
        this.wordString = wordString;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoneticSymbol() {
        return phoneticSymbol;
    }

    public void setPhoneticSymbol(String phoneticSymbol) {
        this.phoneticSymbol = phoneticSymbol;
    }


    public int getIsDelCollect() {
        return isDelCollect;
    }

    public void setIsDelCollect(int isDelCollect) {
        this.isDelCollect = isDelCollect;
    }
}
