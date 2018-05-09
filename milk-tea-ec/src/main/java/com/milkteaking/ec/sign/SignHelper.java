package com.milkteaking.ec.sign;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.milkteaking.ec.database.DataBaseManager;
import com.milkteaking.ec.database.UserProfile;

/**
 * @author TanJJ
 * @time 2018/5/9 10:13
 */

public class SignHelper {

    public static void signIn(String response, ISignListener listener) {
        JSONObject data = JSON.parseObject(response).getJSONObject("data");
        int userId = data.getInteger("userId");
        String name = data.getString("name");
        String avatar = data.getString("avatar");
        String gender = data.getString("gender");
        String address = data.getString("address");
        UserProfile userProfile = new UserProfile(userId, name, avatar, gender, address);
        // 通过GreenDao存入数据库
        DataBaseManager.getInstance().getUserProfileDao().insert(userProfile);

        AccountManager.setSignTag(true);
        if (listener != null) {
            listener.onSignInSuccess();
        }
    }

    public static void signOn(String response, ISignListener listener) {
        JSONObject data = JSON.parseObject(response).getJSONObject("data");
        int userId = data.getInteger("userId");
        String name = data.getString("name");
        String avatar = data.getString("avatar");
        String gender = data.getString("gender");
        String address = data.getString("address");
        UserProfile userProfile = new UserProfile(userId, name, avatar, gender, address);
        // 通过GreenDao存入数据库
        DataBaseManager.getInstance().getUserProfileDao().insert(userProfile);

        AccountManager.setSignTag(true);
        if (listener != null) {
            listener.onSignUpSuccess();
        }
    }
}
