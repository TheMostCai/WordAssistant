package org.school.wordassistant.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;


//尝试使用AudioService实现使用有道api接口调用（但是出现了too much threads 报错，怀疑是主线程中处理数据过多的原因）
public class AudioService extends Service {

    private MediaPlayer mp;
    private String query;

    @Override
    public void onCreate() {
        System.out.println("初始化音乐资源  ");
        super.onCreate();
    }

    @Override
    public void onStart(Intent intent, int startId) {

        // super.onStart(intent, startId);
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (query != null && !query.equals(intent.getStringExtra("query")) && mp != null) {
            mp.start();
        } else {
            //得到前边的传值
            String query = intent.getStringExtra("query");
            Uri location = Uri.parse("http://dict.youdao.com/dictvoice?audio=" + query);

            System.out.println("AudioService location-->  " + location);

            try {
                mp = MediaPlayer.create(this,location);
                mp.prepare(); //准备播放
                System.out.println("音乐开始播放");
                mp.start();

                // 音乐播放完毕的事件处理
                mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    public void onCompletion(MediaPlayer mp) {
                        // 不循环播放
                        try {
                            // mp.start();
                            mp.stop();
                            System.out.println("stopped");
                        } catch (IllegalStateException e) {
                            e.printStackTrace();
                        }
                    }
                });

                // 播放音乐时发生错误的事件处理
                mp.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                    public boolean onError(MediaPlayer mp, int what, int extra) {
                        // 释放资源
                        try {
                            mp.release();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return false;
                    }
                });

            }catch (Exception e){
                e.printStackTrace();
            }

        }

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        // 服务停止时停止播放音乐并释放资源
        mp.release();
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}


