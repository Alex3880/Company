package com.netCracker.model;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class Project implements Serializable {
    private int id;
    @Size(min = 1, max = 32, message = "Name must be between 1 and 32")
    private String name;
    private int idCustomer;
    @Pattern(regexp = "[0-9]{4}-(0[1-9]|1[012])-(0[1-9]|1[0-9]|2[0-9]|3[01])",message = "Date begin is invalid")
    private String dateBegin;
    @Pattern(regexp = "[0-9]{4}-(0[1-9]|1[012])-(0[1-9]|1[0-9]|2[0-9]|3[01])",message = "Date end is invalid")
    private String dateEnd;
    private int idManager;

    public Project() {
    }

    public Project(String name, int idCustomer, String dateBegin, String dateEnd, int idManager) {
        this.name = name;
        this.idCustomer = idCustomer;
        this.dateBegin = dateBegin;
        this.dateEnd = dateEnd;
        this.idManager = idManager;
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

    public int getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(int idCustomer) {
        this.idCustomer = idCustomer;
    }

    public String getDateBegin() {
        return dateBegin;
    }

    public void setDateBegin(String dateBegin) {
        this.dateBegin = dateBegin;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

    public int getIdManager() {
        return idManager;
    }

    public void setIdManager(int idManager) {
        this.idManager = idManager;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", idCustomer=" + idCustomer +
                ", dateBegin='" + dateBegin + '\'' +
                ", dateEnd='" + dateEnd + '\'' +
                ", idManager=" + idManager +
                '}';
    }
}
