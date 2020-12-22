package org.school.wordassistant.util;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.school.wordassistant.R;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

//设置的连接Sqlite数据库的类
public class DataBaseHelper {

    private final int BUFFER_SIZE = 400000;  //设置缓冲区
    private static final String DB_NAME = "hiword.db"; // 保存的数据库文件名称
    private static final String PACKAGE_NAME = "org.school.wordassistant";// 应用的包名
    private static final String DB_PATH = "/data/data" +"/" + PACKAGE_NAME+ "/databases"; // 在手机里存放数据库的位置

    //sdcard的路径(在android 4.4中不好使，文件成功创建是在手机的)
    //public static final String DB_PATH = Environment.getExternalStorageDirectory().getAbsolutePath()+"/idiom";

    private Context context;

    public DataBaseHelper(Context context) {
        this.context = context;
    }


    //打开数据库(将数据库写入到设备中)
    public SQLiteDatabase openDatabase() {
        try {
            //打开文件
            File myDataPath = new File(DB_PATH);
            //如果路径不存在
            if (!myDataPath.exists()) {
                myDataPath.mkdirs();// 假设没有这个文件夹,则创建
            }

            //得到数据库存放位置路径
            String dbfile=myDataPath+"/"+DB_NAME;

            //如果数据库文件不存在
            if (!(new File(dbfile).exists())) {// 推断数据库文件是否存在，若不存在则运行导入，否则直接打开数据库
                InputStream is = context.getResources().openRawResource(R.raw.hiword); // 欲导入的数据库
                FileOutputStream fos = new FileOutputStream(dbfile);
                byte[] buffer = new byte[BUFFER_SIZE];
                int count = 0;
                while ((count = is.read(buffer)) > 0) {
                    fos.write(buffer, 0, count);
                }

                fos.close();
                is.close();
            }

            //以文件创建一个SQLiteDatabase对象形式存在
            SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(dbfile, null);
            return db;

        } catch (FileNotFoundException e) {
            Log.e("Database", "File not found");
            e.printStackTrace();
        } catch (IOException e) {
            Log.e("Database", "IO exception");
            e.printStackTrace();
        }
        return null;
    }

}
