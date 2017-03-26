package com.netCracker.comparators;


import com.netCracker.model.Sprint;
import java.util.Comparator;

public class SprintIdComparator implements Comparator<Sprint> {
    @Override
    public int compare(Sprint o1, Sprint o2) {
        if (o1.getId() > o2.getId())
            return 1;
        else if (o2.getId() > o1.getId())
            return -1;
        else return 0;
    }

}
