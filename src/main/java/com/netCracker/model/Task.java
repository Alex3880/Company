package com.netCracker.model;

import javax.validation.constraints.Size;
import java.io.Serializable;

public class Task implements Serializable {
    private int id;
    @Size(min = 1, max = 32, message = "Name must be between 1 and 32")
    private String name;
    private int idSprint;
    private int idEstimate;
    private int idCondition;
    private int dependencyTask;
    private int isSubtask;
    private int idSkill;

    public Task() {
    }

    public Task(String name, int idSprint, int idEstimate, int idCondition, int dependencyTask, int isSubtask, int idSkill) {
        this.name = name;
        this.idSprint = idSprint;
        this.idEstimate = idEstimate;
        this.idCondition = idCondition;
        this.dependencyTask = dependencyTask;
        this.isSubtask = isSubtask;
        this.idSkill = idSkill;
    }

    public boolean isComplete(){
        return idCondition == Condition.CONDITION_DONE;
    }

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

    public int getIdSprint() {
        return idSprint;
    }

    public void setIdSprint(int idSprint) {
        this.idSprint = idSprint;
    }

    public int getIdEstimate() {
        return idEstimate;
    }

    public void setIdEstimate(int idEstimate) {
        this.idEstimate = idEstimate;
    }

    public int getIdCondition() {
        return idCondition;
    }

    public void setIdCondition(int idCondition) {
        this.idCondition = idCondition;
    }

    public int getDependencyTask() {
        return dependencyTask;
    }

    public void setDependencyTask(int dependencyTask) {
        this.dependencyTask = dependencyTask;
    }

    public int getIsSubtask() {
        return isSubtask;
    }

    public void setIsSubtask(int isSubtask) {
        this.isSubtask = isSubtask;
    }

    public int getIdSkill() {
        return idSkill;
    }

    public void setIdSkill(int idSkill) {
        this.idSkill = idSkill;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", idSprint=" + idSprint +
                ", idEstimate=" + idEstimate +
                ", idCondition=" + idCondition +
                ", dependencyTask=" + dependencyTask +
                ", isSubtask=" + isSubtask +
                ", idSkill=" + idSkill +
                '}';
    }
}
