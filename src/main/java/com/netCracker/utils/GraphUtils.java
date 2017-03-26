package com.netCracker.utils;

import com.netCracker.dao.EstimateDao;
import com.netCracker.dao.SprintDao;
import com.netCracker.dao.TaskDao;
import com.netCracker.model.Estimate;
import com.netCracker.model.GraphGanttSprintData;
import com.netCracker.model.Sprint;
import com.netCracker.model.Task;

import java.util.ArrayList;
import java.util.List;


public class GraphUtils {
    public static List<GraphGanttSprintData> getGraphGanttSprintDataList(int idProject, SprintDao sprintDao, TaskDao taskDao, EstimateDao estimateDao){
        List<GraphGanttSprintData> dataList = new ArrayList<>();

        List<Sprint> sprints = sprintDao.getByProjectId(idProject);
        if (sprints==null){
            return null;
        }
        for (Sprint sprint: sprints){
            GraphGanttSprintData data = new GraphGanttSprintData();
            data.setId(String.valueOf(sprint.getId()));
            data.setName(sprint.getName());
            if (sprint.getPrevSprint()==0){
                data.setDependencyId(null);
            }
            else {
                data.setDependencyId(String.valueOf(sprint.getPrevSprint()));
            }
            int timeBySprint=0;
            int percentToReady=0;
            List<Task> tasksBySprintId= taskDao.getBySprintId(sprint.getId());
            for (Task task : tasksBySprintId){
                Estimate estimate = estimateDao.getById(task.getIdEstimate());
                timeBySprint+=estimate.getHours();
                if (task.isComplete()){
                    percentToReady+=100/tasksBySprintId.size();
                }
            }
            data.setFullEstimate(timeBySprint);
            data.setPercent(percentToReady);
            dataList.add(data);
        }
        return dataList;
    }
}
