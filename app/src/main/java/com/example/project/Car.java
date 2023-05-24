package com.example.project;

import com.google.gson.annotations.SerializedName;

public class Car {
    @SerializedName("ID")
    private String licensplate;
    private String name;
    @SerializedName("company")
    private String speed;
    @SerializedName("location")
    private String color;
    private String category;
    @SerializedName("auxdata")
    private String doors;

    public Car(String licensplate, String name, String speed, String color, String category, String doors) {
        this.licensplate = licensplate;
        this.name = name;
        this.speed = speed;
        this.color = color;
        this.category = category;
        this.doors = doors;
    }

    public java.lang.String getLicensplate() {
        return licensplate;
    }

    public java.lang.String getName() {
        return name;
    }

    public java.lang.String getSpeed() {
        return speed;
    }

    public java.lang.String getColor() {
        return color;
    }

    public java.lang.String getCategory() {
        return category;
    }

    public java.lang.String getDoors() {
        return doors;
    }
    public String getCar() {
        return "Licensplate: " + licensplate +
                "\nName: " + name +
                "\nSpeed: " + speed +
                "\nColor: " + color +
                "\nCategory: " + category +
                "\nDoors: " + doors;
    }
}
