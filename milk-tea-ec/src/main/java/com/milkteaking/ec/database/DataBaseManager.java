package com.milkteaking.ec.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * @author TanJJ
 * @time 2018/5/8 21:10
 * @des 数据库管理
 */

public class DataBaseManager {

    private DaoSession mSession;
    private UserProfileDao mUserProfileDao;

    private DataBaseManager() {
    }

    private static class Helper {
        private static final DataBaseManager INSTANCE = new DataBaseManager();
    }

    public static DataBaseManager getInstance() {
        return Helper.INSTANCE;
    }

    public DataBaseManager init(Context context) {
        initDao(context);
        return this;
    }

    private void initDao(Context context) {
        ReleaseOpenHelper helper = new ReleaseOpenHelper(context, "milk_tea_king.db");
        SQLiteDatabase database = helper.getWritableDatabase();
        DaoMaster master = new DaoMaster(database);
        mSession = master.newSession();
        mUserProfileDao = mSession.getUserProfileDao();
    }

    public UserProfileDao getUserProfileDao() {
        return mUserProfileDao;
    }

}
