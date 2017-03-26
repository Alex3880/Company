package com.netCracker.model;

import java.io.Serializable;

public class EmpTask implements Serializable {
    private int idEmp;
    private int idTask;

    public EmpTask() {
    }

    public EmpTask(int idEmp, int idTask) {
        this.idEmp = idEmp;
        this.idTask = idTask;
    }

    public int getIdEmp() {
        return idEmp;
    }

    public void setIdEmp(int idEmp) {
        this.idEmp = idEmp;
    }

    public int getIdTask() {
        return idTask;
    }

    public void setIdTask(int idTask) {
        this.idTask = idTask;
    }

    @Override
    public String toString() {
        return "EmpTask{" +
                "idEmp=" + idEmp +
                ", idTask=" + idTask +
                '}';
    }
}
