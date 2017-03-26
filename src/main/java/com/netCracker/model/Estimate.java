package com.netCracker.model;


import java.io.Serializable;

public class Estimate implements Serializable {
    private int id;
    private int hours;

    public Estimate() {
    }

    public Estimate(int hours) {
        this.hours = hours;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    @Override
    public String toString() {
        return "Estimate{" +
                "id=" + id +
                ", hours=" + hours +
                '}';
    }
}
