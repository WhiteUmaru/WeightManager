package com.gank.mybodymanage.entry;

/**
 * 实体类
 * Created by Ly on 2018/2/22.
 */

public class Body {

    private String name;
    private int weight;
    private int height;
    private int date;

    public String getName() {
        return name;
    }

    public Body(String name, int weight, int height, int date) {
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
        return "Body{" +
                "name='" + name + '\'' +
                ", weight=" + weight +
                ", height=" + height +
                ", date=" + date +
                '}';
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
