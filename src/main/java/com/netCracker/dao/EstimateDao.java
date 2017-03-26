package com.netCracker.dao;

import com.netCracker.model.Estimate;

import java.util.List;

public interface EstimateDao extends CRUDable<Estimate> {
    default Estimate getByEstimateHours(int hours) {
        List<Estimate> estimates = getAll();
        Estimate find = null;
        for (Estimate estimate : estimates) {
            if (estimate.getHours() == hours) {
                find = estimate;
            }
        }
        return find;
    }
}
