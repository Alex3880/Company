package com.netCracker.controllers;

import com.netCracker.dao.EstimateDao;
import com.netCracker.dao.ProjectDao;
import com.netCracker.dao.SprintDao;
import com.netCracker.dao.TaskDao;
import com.netCracker.model.GraphGanttSprintData;
import com.netCracker.utils.GraphUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class GraphController {

    @Autowired
    ProjectDao projectDao;
    @Autowired
    private SprintDao sprintDao;
    @Autowired
    private TaskDao taskDao;
    @Autowired
    private EstimateDao estimateDao;

    @RequestMapping(value = "sprintDataGraph", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public List<GraphGanttSprintData> getSprintData(@RequestParam int idProject) {
        return GraphUtils.getGraphGanttSprintDataList(idProject,sprintDao,taskDao,estimateDao);
    }
}
