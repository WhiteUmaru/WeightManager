package com.gank.mybodymanage.entry;

import android.content.ContentValues;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 用户类
 *
 * @author Ly
 * @date 2018/2/24
 */

public class User {
    private int id;
    private int height;
    private String name;
    private int date;
    private String userInfo;

    public User() {
    }

    public User(int id, int height, String name) {
        this.id = id;
        this.height = height;
        this.name = name;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public String getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(String userInfo) {
        this.userInfo = userInfo;
    }

    public User(int id, int height, String name, int date, String userInfo) {
        this.id = id;
        this.height = height;
        this.name = name;
        this.date = date;
        this.userInfo = userInfo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ContentValues getBodyUser() {
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("height", height);
        contentValues.put("Id", id);
        return contentValues;
    }

    @Override
    public String toString() {
        JSONObject object = new JSONObject();
        try {
            object.put("name", name);
            object.put("userInfo", userInfo);
            object.put("height", height);
            object.put("date", date);
            object.put("id", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object.toString();
    }

    public static User getUser(String userString) {
        User user = new User();
        if (userString == null) {
            return user;
        }
        try {
            JSONObject object = new JSONObject(userString);
            if (object.has("name")) {
                user.setName(object.getString("name"));
            }
            if (object.has("userInfo")) {
                user.setUserInfo(object.getString("userInfo"));
            }
            if (object.has("height")) {
                user.setHeight(object.getInt("height"));
            }
            if (object.has("date")) {
                user.setDate(object.getInt("date"));
            }
            if (object.has("id")) {
                user.setId(object.getInt("id"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return user;
    }

    public ContentValues getContentValues() {
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("height", height);
        contentValues.put("date", date);
        contentValues.put("userInfo", userInfo);
        return contentValues;
    }

}
