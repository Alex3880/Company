package com.netCracker.model;


public class GraphGanttSprintData {
    private String id;
    private String name;
    private int fullEstimate;
    private int percent;
    private String dependencyId;

    public GraphGanttSprintData() {
    }

    public GraphGanttSprintData(String id, String name, int fullEstimate, int percent, String dependencyId) {
        this.id = id;
        this.name = name;
        this.fullEstimate = fullEstimate;
        this.percent = percent;
        this.dependencyId = dependencyId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFullEstimate() {
        return fullEstimate;
    }

    public void setFullEstimate(int fullEstimate) {
        this.fullEstimate = fullEstimate;
    }

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }

    public String getDependencyId() {
        return dependencyId;
    }

    public void setDependencyId(String dependencyId) {
        this.dependencyId = dependencyId;
    }

    @Override
    public String toString() {
        return "GraphGanttSprintData{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", fullEstimate=" + fullEstimate +
                ", percent=" + percent +
                ", dependencyId='" + dependencyId + '\'' +
                '}';
    }
}


