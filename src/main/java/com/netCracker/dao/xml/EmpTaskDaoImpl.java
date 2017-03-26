package com.netCracker.dao.xml;

import com.netCracker.dao.EmpTaskDao;
import com.netCracker.model.EmpTask;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import com.netCracker.utils.XmlUtils;
import java.util.ArrayList;
import java.util.List;

public class EmpTaskDaoImpl implements EmpTaskDao {
    private final static String FILE_NAME = "xml/EmpTask.xml";
    @Override
    public boolean create(EmpTask entity) {
        return XmlUtils.creteEntity(entity,FILE_NAME);
    }

    @Override
    public List<EmpTask> getIdEmps(int idTask) {
        Document document = XmlUtils.getDocument(FILE_NAME);
        NodeList list = document.getElementsByTagName(EmpTask.class.getSimpleName());
        List<EmpTask> empTasks = new ArrayList<>();
        for (int i = 0; i < list.getLength(); i++) {

            Node node = list.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                if (idTask == Integer.valueOf(element.getElementsByTagName("idTask").item(0).getFirstChild().getNodeValue())) {
                    EmpTask empTask = new EmpTask();
                    empTask.setIdTask(Integer.valueOf(element.getElementsByTagName("idTask").item(0).getFirstChild().getNodeValue()));
                    empTask.setIdEmp(Integer.valueOf(element.getElementsByTagName("idEmp").item(0).getFirstChild().getNodeValue()));
                    empTasks.add(empTask);
                }
            }
        }
        return empTasks;
    }

    @Override
    public List<EmpTask> getIdTasks(int idEmps) {
        Document document = XmlUtils.getDocument(FILE_NAME);
        NodeList list = document.getElementsByTagName(EmpTask.class.getSimpleName());
        List<EmpTask> empTasks = new ArrayList<>();
        for (int i = 0; i < list.getLength(); i++) {

            Node node = list.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                if (idEmps == Integer.valueOf(element.getElementsByTagName("idEmp").item(0).getFirstChild().getNodeValue())) {
                    EmpTask empTask = new EmpTask();
                    empTask.setIdEmp(Integer.valueOf(element.getElementsByTagName("idEmp").item(0).getFirstChild().getNodeValue()));
                    empTask.setIdTask(Integer.valueOf(element.getElementsByTagName("idTask").item(0).getFirstChild().getNodeValue()));
                    empTasks.add(empTask);
                }
            }
        }
        return empTasks;
    }

    @Override
    public List<EmpTask> getAll() {
        Document document = XmlUtils.getDocument(FILE_NAME);
        NodeList list = document.getElementsByTagName(EmpTask.class.getSimpleName());
        List<EmpTask> empTasks = new ArrayList<>();
        for (int i = 0; i < list.getLength(); i++) {

            Node node = list.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                EmpTask empTask = new EmpTask();
                empTask.setIdTask(Integer.valueOf(element.getElementsByTagName("idEmp").item(0).getFirstChild().getNodeValue()));
                empTask.setIdEmp(Integer.valueOf(element.getElementsByTagName("idTask").item(0).getFirstChild().getNodeValue()));
                empTasks.add(empTask);
            }

        }

        return empTasks;
    }

    @Override
    public boolean delete(EmpTask empTask) {
        boolean deleted = false;
        Document document = XmlUtils.getDocument(FILE_NAME);
        Node container  =document.getElementsByTagName("EmpTasks").item(0);
        NodeList customers = document.getElementsByTagName(empTask.getClass().getSimpleName());
        for (int i = 0; i < customers.getLength(); i++) {
            Node node = customers.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;

                if (empTask.getIdEmp() == Integer.valueOf(element.getElementsByTagName("idEmp").item(0).getFirstChild().getNodeValue())&&
                        empTask.getIdTask() == Integer.valueOf(element.getElementsByTagName("idTask").item(0).getFirstChild().getNodeValue())) {
                    container.removeChild(node);
                    deleted = true;
                }
            }

        }
        XmlUtils.setDocument(document,FILE_NAME);
        return deleted;
    }
}
