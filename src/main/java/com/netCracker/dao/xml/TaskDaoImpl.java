package com.netCracker.dao.xml;

import com.netCracker.dao.TaskDao;
import com.netCracker.model.Task;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import com.netCracker.utils.XmlUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TaskDaoImpl implements TaskDao {
    private final static String FILE_NAME = "xml/Task.xml";
    @Override
    public boolean create(Task entity) {
        return XmlUtils.creteEntity(entity,FILE_NAME);
    }

    @Override
    public Task getById(int id) {
        Document document = XmlUtils.getDocument(FILE_NAME);
        NodeList list = document.getElementsByTagName(Task.class.getSimpleName());
        Task task = null;
        for (int i = 0; i < list.getLength(); i++) {

            Node node = list.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                if (id == Integer.valueOf(element.getElementsByTagName("id").item(0).getFirstChild().getNodeValue())) {
                    task = new Task();
                    task.setId(Integer.valueOf(element.getElementsByTagName("id").item(0).getFirstChild().getNodeValue()));
                    task.setName(element.getElementsByTagName("name").item(0).getFirstChild().getNodeValue());
                    task.setIdSprint(Integer.valueOf(element.getElementsByTagName("idSprint").item(0).getFirstChild().getNodeValue()));
                    task.setIdEstimate(Integer.valueOf(element.getElementsByTagName("idEstimate").item(0).getFirstChild().getNodeValue()));
                    task.setIdCondition(Integer.valueOf(element.getElementsByTagName("idCondition").item(0).getFirstChild().getNodeValue()));
                    task.setDependencyTask(Integer.valueOf(element.getElementsByTagName("dependencyTask").item(0).getFirstChild().getNodeValue()));
                    task.setIsSubtask(Integer.valueOf(element.getElementsByTagName("isSubtask").item(0).getFirstChild().getNodeValue()));
                    task.setIdSkill(Integer.valueOf(element.getElementsByTagName("idSkill").item(0).getFirstChild().getNodeValue()));
                }
            }
        }
        return task;
    }

    @Override
    public List<Task> getAll() {
        Document document = XmlUtils.getDocument(FILE_NAME);
        NodeList list = document.getElementsByTagName(Task.class.getSimpleName());
        List<Task> tasks = new ArrayList<>();
        for (int i = 0; i < list.getLength(); i++) {

            Node node = list.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                Task task = new Task();
                task.setId(Integer.valueOf(element.getElementsByTagName("id").item(0).getFirstChild().getNodeValue()));
                task.setName(element.getElementsByTagName("name").item(0).getFirstChild().getNodeValue());
                task.setIdSprint(Integer.valueOf(element.getElementsByTagName("idSprint").item(0).getFirstChild().getNodeValue()));
                task.setIdEstimate(Integer.valueOf(element.getElementsByTagName("idEstimate").item(0).getFirstChild().getNodeValue()));
                task.setIdCondition(Integer.valueOf(element.getElementsByTagName("idCondition").item(0).getFirstChild().getNodeValue()));
                task.setDependencyTask(Integer.valueOf(element.getElementsByTagName("dependencyTask").item(0).getFirstChild().getNodeValue()));
                task.setIsSubtask(Integer.valueOf(element.getElementsByTagName("isSubtask").item(0).getFirstChild().getNodeValue()));
                task.setIdSkill(Integer.valueOf(element.getElementsByTagName("idSkill").item(0).getFirstChild().getNodeValue()));
                tasks.add(task);
            }
        }
        return tasks;
    }

    @Override
    public boolean update(Task entity) {
        boolean updated = false;
        Document document = XmlUtils.getDocument(FILE_NAME);
        NodeList customers = document.getElementsByTagName(entity.getClass().getSimpleName());
        for (int i = 0; i < customers.getLength(); i++) {
            Node node = customers.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;

                if (entity.getId() == Integer.valueOf(element.getElementsByTagName("id").item(0).getFirstChild().getNodeValue())) {
                    element.getElementsByTagName("name").item(0).setTextContent(entity.getName());
                    element.getElementsByTagName("idSprint").item(0).setTextContent(String.valueOf(entity.getIdSprint()));
                    element.getElementsByTagName("idEstimate").item(0).setTextContent(String.valueOf(entity.getIdEstimate()));
                    element.getElementsByTagName("idCondition").item(0).setTextContent(String.valueOf(entity.getIdCondition()));
                    element.getElementsByTagName("dependencyTask").item(0).setTextContent(String.valueOf(entity.getDependencyTask()));
                    element.getElementsByTagName("isSubtask").item(0).setTextContent(String.valueOf(entity.getIsSubtask()));
                    element.getElementsByTagName("idSkill").item(0).setTextContent(String.valueOf(entity.getIdSkill()));
                    updated = true;
                }
            }

        }
        XmlUtils.setDocument(document,FILE_NAME);


        return updated;
    }

    @Override
    public boolean delete(Task entity) {
        return delete(entity.getId());
    }

    @Override
    public boolean delete(int id) {
        return XmlUtils.deleteById("Tasks",Task.class.getSimpleName(),id,FILE_NAME);
    }

}
