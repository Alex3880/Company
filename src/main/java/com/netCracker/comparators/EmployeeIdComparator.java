package com.netCracker.comparators;

import com.netCracker.model.Employee;

import java.util.Comparator;

/**
 * Created by aleksandr on 15.02.17.
 */
public class EmployeeIdComparator implements Comparator<Employee> {
    @Override
    public int compare(Employee o1, Employee o2) {
        if (o1.getId() > o2.getId())
            return 1;
        else if (o2.getId() > o1.getId())
            return -1;
        else return 0;
    }
}
