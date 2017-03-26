package com.netCracker.model;


import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class Sprint implements Serializable {
    private int id;
    @Size(min = 1, max = 32, message = "Name must be between 1 and 32")
    private String name;
    private int idProject;
    private int prevSprint;

    public Sprint() {
    }

    public Sprint(String name, int idProject, int prevSprint) {
        this.name = name;
        this.idProject = idProject;
        this.prevSprint = prevSprint;
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

    public int getIdProject() {
        return idProject;
    }

    public void setIdProject(int idProject) {
        this.idProject = idProject;
    }

    public int getPrevSprint() {
        return prevSprint;
    }

    public void setPrevSprint(int prevSprint) {
        this.prevSprint = prevSprint;
    }

    @Override
    public String toString() {
        return "Sprint{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", idProject=" + idProject +
                ", prevSprint=" + prevSprint +
                '}';
    }
}
