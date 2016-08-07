package com.hurui.androidndemo;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.RemoteInput;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    public static final String KEY_TEXT_REPLY = "key_text_reply";//用于获取快速回复内容的key
    public static final int NOTIFICATION_ID = 100;
    private Button mBtnOpenNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtnOpenNotification = (Button) findViewById(R.id.btn_open_notification);
        mBtnOpenNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNotification();
            }
        });
    }

    private void addNotification(){

        String replyLabel = getResources().getString(R.string.reply_label);
        //创建一个远程输入（既：通知栏的快捷回复）
        RemoteInput remoteInput = new RemoteInput.Builder(KEY_TEXT_REPLY)
                .setLabel(replyLabel)
                .build();

        Intent intent = new Intent(MainActivity.this,GetMessageReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,0,intent,PendingIntent.FLAG_ONE_SHOT);

        //创建快速回复的动作，并添加remoteInut
        Notification.Action replyAction = new Notification.Action.Builder(
                R.drawable.ic_chat_blue_24dp, getString(R.string.label), pendingIntent)
                .addRemoteInput(remoteInput)
                .build();

        //创建一个Notification，并设置title，content，icon等内容
        Notification newMessageNotification = new Notification.Builder(this)
                .setSmallIcon(R.drawable.ic_account_circle_white_24dp)
                .setContentTitle(getString(R.string.title))
                .setContentText(getString(R.string.content))
                .addAction(replyAction)
                .build();

        //发出通知
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, newMessageNotification);
    }


}