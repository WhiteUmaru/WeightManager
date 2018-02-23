package com.gank.mybodymanage.entry;

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

    @Override
    public String toString() {
        JSONObject object = new JSONObject();
        try {
            object.put("name", name);
            object.put("weight", weight);
            object.put("height", height);
            object.put("date", date);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object.toString();
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

}
