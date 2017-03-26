package com.netCracker.model;

import java.io.Serializable;

public class Condition implements Serializable {
    private int id;
    private String name;
    public final static int CONDITION_WAITING = 1;
    public final static int CONDITION_WORKING = 2;
    public final static int CONDITION_DONE = 3;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Condition{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
