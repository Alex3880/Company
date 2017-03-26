package com.netCracker.comparators;

import com.netCracker.model.Project;

import java.util.Comparator;


public class ProjectIdComparator implements Comparator<Project> {
    @Override
    public int compare(Project o1, Project o2) {
        if (o1.getId() > o2.getId())
            return 1;
        else if (o2.getId() > o1.getId())
            return -1;
        else return 0;
    }
}

