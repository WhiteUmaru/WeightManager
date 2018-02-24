package com.gank.mybodymanage.sql;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.gank.mybodymanage.entry.Body;
import com.gank.mybodymanage.entry.User;
import com.gank.mybodymanage.util.Util;

import java.util.ArrayList;

/**
 * 数据库操作类
 * Created by Ly on 2018/2/22.
 */

public class DBImp {
    private static final String TAG = "DBImp";

    private Context context;
    private OrderDBHelper ordersDBHelper;
    private final String[] ORDER_COLUMNS = new String[]{"Id", "name", "height", "weight", "date", "BMI"};
    private final String[] USER_COLUMNS = new String[]{"Id", "name", "height", "userInfo", "date"};
    private static DBImp imp;

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

    public int updateUser(User user) {
        SQLiteDatabase db = ordersDBHelper.getWritableDatabase();
        int res = db.update(OrderDBHelper.TABLE_NAME_USER, user.getBodyUser(), "id = ?", new String[]{user.getId() + ""});
        db.close();
        return res;
    }

    public int updateMsg(Body body) {
        SQLiteDatabase db = ordersDBHelper.getWritableDatabase();
        int res = db.update(OrderDBHelper.TABLE_NAME, body.getBody(), "Id = ?", new String[]{body.getId() + ""});
        db.close();
        return res;
    }

    public int add(Body body) {
        SQLiteDatabase db = ordersDBHelper.getWritableDatabase();
        long ret = db.insertOrThrow(OrderDBHelper.TABLE_NAME, null, body.getBody());
        db.close();
        return (int) ret;
    }

    public int addUser(User user) {
        SQLiteDatabase db = ordersDBHelper.getWritableDatabase();
        long ret = db.insertOrThrow(OrderDBHelper.TABLE_NAME_USER, null, user.getContentValues());
        db.close();
        return (int) ret;
    }

    public ArrayList<Body> getAllDate(boolean all) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        int userId = Util.getInt(context, Util.USER_ID, 1);
        try {
            db = ordersDBHelper.getReadableDatabase();
            // select * from Orders
            if (!all) {
                cursor = db.query(OrderDBHelper.TABLE_NAME, ORDER_COLUMNS, "userId = ?", new String[]{userId + ""}, null, null, null);
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

    public ArrayList<User> getAllUser() {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = ordersDBHelper.getReadableDatabase();
            // select * from Orders
            cursor = db.query(OrderDBHelper.TABLE_NAME_USER, USER_COLUMNS, null, null, null, null, null);
            if (cursor.getCount() > 0) {
                ArrayList<User> orderList = new ArrayList<User>(cursor.getCount());
                while (cursor.moveToNext()) {
                    orderList.add(parseBodyUser(cursor));
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

    public User getUser(String name) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = ordersDBHelper.getReadableDatabase();
            // select * from Orders
            cursor = db.query(OrderDBHelper.TABLE_NAME_USER, USER_COLUMNS, "name = ?", new String[]{name}, null, null, null);
            if (cursor.getCount() > 0) {
                if (cursor.moveToNext()) {
                    return parseBodyUser(cursor);
                }
                return null;
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

    public User getUser(int id) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = ordersDBHelper.getReadableDatabase();
            // select * from Orders
            cursor = db.query(OrderDBHelper.TABLE_NAME_USER, USER_COLUMNS, "id = ?", new String[]{id + ""}, null, null, null);
            if (cursor.getCount() > 0) {
                if (cursor.moveToNext()) {
                    return parseBodyUser(cursor);
                }
                return null;
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

    public int deleteUser(User body) {
        SQLiteDatabase db = ordersDBHelper.getWritableDatabase();
        int id = body.getId();
        if (id > 0) {
            int res = db.delete(OrderDBHelper.TABLE_NAME_USER, "id = ?", new String[]{body.getId() + ""});
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
        body.setBMI(cursor.getInt(cursor.getColumnIndex("BMI")));
        return body;
    }

    /**
     * 将查找到的数据转换成Body类
     */
    private User parseBodyUser(Cursor cursor) {
        User user = new User();
        user.setName(cursor.getString(cursor.getColumnIndex("name")));
        user.setId(cursor.getInt(cursor.getColumnIndex("Id")));
        user.setHeight(cursor.getInt(cursor.getColumnIndex("height")));
        user.setUserInfo(cursor.getString(cursor.getColumnIndex("userInfo")));
        user.setDate(cursor.getInt(cursor.getColumnIndex("date")));
        return user;
    }
}