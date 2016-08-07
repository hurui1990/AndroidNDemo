package com.hurui.androidndemo;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.RemoteInput;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by hurui on 16/8/7.
 */
public class GetMessageReceiver extends BroadcastReceiver {

    private Context context;
    private NotificationManager notificationManager;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        //模拟发送消息
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);//设置三秒钟之后更新Notification的状态
                    updateNotification();
                    Thread.sleep(3000);//三秒钟后移除Notification
                    cancalNotification();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //消息发送状态的Notification
    private void updateNotification(){
        Notification repliedNotification =
                new Notification.Builder(context)
                        .setSmallIcon(R.drawable.ic_account_circle_white_24dp)
                        .setContentText("消息发送成功！")
                        .build();

        notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        notificationManager.notify(MainActivity.NOTIFICATION_ID, repliedNotification);
    }

    //移除Notification
    private void cancalNotification(){
        notificationManager.cancel(MainActivity.NOTIFICATION_ID);
    }

    //获取快捷回复中用户输入的字符串
    private CharSequence getMessageText(Intent intent) {
        Bundle remoteInput = RemoteInput.getResultsFromIntent(intent);
        if (remoteInput != null) {
            return remoteInput.getCharSequence(MainActivity.KEY_TEXT_REPLY);//通过KEY_TEXT_REPLY来获取输入的内容
        }
        return null;
    }
}
