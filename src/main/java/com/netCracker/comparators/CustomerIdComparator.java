package com.netCracker.comparators;

import com.netCracker.model.Customer;
import com.netCracker.model.Project;

import java.util.Comparator;


public class CustomerIdComparator implements Comparator<Customer> {
    @Override
    public int compare(Customer o1, Customer o2) {
        if (o1.getId() > o2.getId())
            return 1;
        else if (o2.getId() > o1.getId())
            return -1;
        else return 0;
    }
}
