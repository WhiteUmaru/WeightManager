package com.gank.mybodymanage.entry;

import android.content.ContentValues;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 实体类
 *
 * @author Ly
 * @date 2018/2/22
 */

public class Body {

    private int id;
    private String name;
    private int weight;
    private int height;
    private int date;
    private int BMI;
    private int userId;

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Body(String name, int weight, int height, int date) {
        this.name = name;
        this.weight = weight;
        this.height = height;
        this.date = date;
    }

    public Body(int id, String name, int weight, int height, int date) {
        this.id = id;
        this.name = name;
        this.weight = weight;
        this.height = height;
        this.date = date;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public Body() {
    }

    public Body(String name, int weight, int height) {
        this.name = name;
        this.weight = weight;
        this.height = height;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Body(int id, String name, int weight, int height, int date, int BMI, int userId) {
        this.id = id;
        this.name = name;
        this.weight = weight;
        this.height = height;
        this.date = date;
        this.BMI = BMI;
        this.userId = userId;
    }

    public int getBMI() {
        return BMI;
    }

    public void setBMI(int BMI) {
        this.BMI = BMI;
    }

    public Body(int id, String name, int weight, int height, int date, int BMI) {
        this.id = id;
        this.name = name;
        this.weight = weight;
        this.height = height;
        this.date = date;
        this.BMI = BMI;
    }

    @Override
    public String toString() {
        JSONObject object = new JSONObject();
        try {
            object.put("name", name);
            object.put("weight", weight);
            object.put("height", height);
            object.put("date", date);
            object.put("id", id);
            object.put("bmi", BMI);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object.toString();
    }

    public Body getBody(String json) {
        Body body = new Body();
        try {
            JSONObject object = new JSONObject(json);
            if (object.has("name")) {
                body.setName(object.getString("name"));
            }
            if (object.has("weight")) {
                body.setWeight(object.getInt("weight"));
            }
            if (object.has("height")) {
                body.setHeight(object.getInt("height"));
            }
            if (object.has("date")) {
                body.setDate(object.getInt("date"));
            }
            if (object.has("id")) {
                body.setId(object.getInt("id"));
            }
            if (object.has("bmi")) {
                body.setBMI(object.getInt("bmi"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return body;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public ContentValues getBody() {
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("height", height);
        contentValues.put("date", date);
        contentValues.put("weight", weight);
        contentValues.put("BMI", BMI);
        contentValues.put("userId", userId);
        return contentValues;
    }


}
