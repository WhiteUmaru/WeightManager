package com.gank.mybodymanage.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.gank.mybodymanage.entry.Body;
import com.gank.mybodymanage.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据库操作类
 * Created by Ly on 2018/2/22.
 */

public class DBImp {
    private static final String TAG = "DBImp";

    private Context context;
    private OrderDBHelper ordersDBHelper;
    private final String[] ORDER_COLUMNS = new String[]{"Id", "name", "height", "weight", "date"};
    public static DBImp imp;

    public static DBImp getInstance(Context context) {
        if (imp == null) {
            imp = new DBImp(context);
        }
        return imp;
    }

    private DBImp(Context context) {
        this.context = context;
        ordersDBHelper = new OrderDBHelper(context);
    }

    /**
     * 判断表中是否有数据
     */
    public boolean isDataExist() {
        int count = 0;

        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            db = ordersDBHelper.getReadableDatabase();
            // select count(Id) from Orders
            cursor = db.query(OrderDBHelper.TABLE_NAME, new String[]{"COUNT(Id)"}, null, null, null, null, null);

            if (cursor.moveToFirst()) {
                count = cursor.getInt(0);
            }
            if (count > 0) {
                return true;
            }
        } catch (Exception e) {
            Log.e(TAG, "", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return false;
    }

    public int add(Body body) {
        SQLiteDatabase db = ordersDBHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", body.getName());
        contentValues.put("height", body.getHeight());
        contentValues.put("date", body.getDate());
        contentValues.put("weight", body.getWeight());
        long ret = db.insertOrThrow(OrderDBHelper.TABLE_NAME, null, contentValues);
        db.close();
        return (int) ret;
    }

    public int addUser(Body body) {
        SQLiteDatabase db = ordersDBHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", body.getName());
        contentValues.put("height", body.getHeight());
        contentValues.put("date", body.getDate());
        contentValues.put("weight", body.getWeight());
        long ret = db.insertOrThrow(OrderDBHelper.TABLE_NAME_USER, null, contentValues);
        db.close();
        return (int) ret;
    }

    public ArrayList<Body> getAllDate(boolean all) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        String userName = Util.getString(context, Util.USER_NAME, "小土豆");
        try {
            db = ordersDBHelper.getReadableDatabase();
            // select * from Orders
            if (!all) {
                cursor = db.query(OrderDBHelper.TABLE_NAME, ORDER_COLUMNS, "name = ?", new String[]{userName}, null, null, null);
            } else {
                cursor = db.query(OrderDBHelper.TABLE_NAME, ORDER_COLUMNS, null, null, null, null, null);
            }

            if (cursor.getCount() > 0) {
                ArrayList<Body> orderList = new ArrayList<Body>(cursor.getCount());
                while (cursor.moveToNext()) {
                    orderList.add(parseBody(cursor));
                }
                return orderList;
            }
        } catch (Exception e) {
            Log.e(TAG, "", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return null;
    }

    public ArrayList<Body> getAllUser() {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = ordersDBHelper.getReadableDatabase();
            // select * from Orders
            cursor = db.query(OrderDBHelper.TABLE_NAME_USER, ORDER_COLUMNS, null, null, null, null, null);
            if (cursor.getCount() > 0) {
                ArrayList<Body> orderList = new ArrayList<Body>(cursor.getCount());
                while (cursor.moveToNext()) {
                    orderList.add(parseBody(cursor));
                }
                return orderList;
            }
        } catch (Exception e) {
            Log.e(TAG, "", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return null;
    }

    public int deleteMsg(Body body) {
        SQLiteDatabase db = ordersDBHelper.getWritableDatabase();
        int id = body.getId();
        if (id > 0) {
            int res = db.delete(OrderDBHelper.TABLE_NAME, "id = ?", new String[]{body.getId() + ""});
            return res;
        }
        return 0;
    }


    /**
     * 将查找到的数据转换成Body类
     */
    private Body parseBody(Cursor cursor) {
        Body body = new Body();
        body.setName(cursor.getString(cursor.getColumnIndex("name")));
        body.setId(cursor.getInt(cursor.getColumnIndex("Id")));
        body.setWeight(cursor.getInt(cursor.getColumnIndex("weight")));
        body.setHeight(cursor.getInt(cursor.getColumnIndex("height")));
        body.setDate(cursor.getInt(cursor.getColumnIndex("date")));
        return body;
    }
}