package com.example.milkteaking.push;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;

import com.alibaba.fastjson.JSONObject;
import com.example.milkteaking.MainActivity;
import com.milkteaking.core.util.log.MilkTeaLogger;

import java.util.Set;

import cn.jpush.android.api.JPushInterface;

/**
 * @author TanJJ
 * @time 2018/5/31 14:21
 * @des 接受极光推送的广播
 */

public class PushReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle extras = intent.getExtras();
        Set<String> keySet = extras.keySet();
        JSONObject jsonObject = new JSONObject();
        for (String s : keySet) {
            Object value = extras.get(s);
            jsonObject.put(s, value);
        }

        MilkTeaLogger.json("PushReceiver", jsonObject.toJSONString());
        String pushAction = intent.getAction();

        if (pushAction == JPushInterface.ACTION_NOTIFICATION_RECEIVED) {
            //处理接收到的消息
            onReceiverMessage(extras);
        } else if (pushAction == JPushInterface.ACTION_NOTIFICATION_OPENED) {
            // 打开相应的Notification
            openedNotification(context, extras);
        }
    }

    private void onReceiverMessage(Bundle bundle) {
        String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
        String id = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_ID);
        int msgId = bundle.getInt(JPushInterface.EXTRA_MSG_ID);
        String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
        String extra = bundle.getString(JPushInterface.EXTRA_EXTRA);
        String alert = bundle.getString(JPushInterface.EXTRA_ALERT);
    }

    // 如果是打开的通知就开启一个activity
    private void openedNotification(Context context, Bundle bundle) {
        String extra = bundle.getString(JPushInterface.EXTRA_EXTRA);
        Bundle openActivityBundle = new Bundle();
        openActivityBundle.putString(JPushInterface.EXTRA_EXTRA, extra);
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtras(openActivityBundle);
        // 开启一个新的任务栈,并且清除顶部activity
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        ContextCompat.startActivity(context, intent, null);
    }
}
