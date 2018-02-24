package com.gank.mybodymanage.sql;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 数据库创建类
 *
 * @author Ly
 * @date 2018/2/22
 */

public class OrderDBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "BodyManage";
    public static final String TABLE_NAME = "weight";
    public static final String TABLE_NAME_USER = "user";
    public static final int DB_VERSION_3 = 3;
    public static final int DB_VERSION_2 = 2;
    public static final int DB_VERSION_1 = 1;

    public OrderDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION_1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql1 = "create table if not exists " + TABLE_NAME + " (Id integer primary key, name varchar(20), weight integer(5), height integer(5), date integer(20),BMI integer(5), userId integer(5) )";
        String sql2 = "create table if not exists " + TABLE_NAME_USER + " (Id integer primary key, name varchar(20), userInfo TEXT, height integer(5), date integer(20))";
        db.execSQL(sql2);
        db.execSQL(sql1);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

}
