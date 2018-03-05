package com.gasmanager.viacheslav.gasmanager;


import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;


public class MyData extends RealmObject implements Serializable {
    @PrimaryKey
    @Index
    private long id;

    private long date;
    private double odometer;
    private double distance;
    private double liters;
    private double price;
    private double paid;

    public MyData() {
    }

    public MyData(long id, long date, double odometer, double distance, double liters, double price, double paid) {
        this.id = id;
        this.date = date;
        this.odometer = odometer;
        this.distance = distance;
        this.liters = liters;
        this.price = price;
        this.paid = paid;
    }

    public long getID() {
        return id;
    }

    public void setID(long id) {
        this.id = id;
    }

    public long getDate() {
        return date;
    }

    public double getOdometer() {
        return odometer;
    }

    public double getDistance() {
        return distance;
    }

    public double getLiters() {
        return liters;
    }

    public double getPrice() {
        return price;
    }

    public double getPaid() {
        return paid;
    }
}