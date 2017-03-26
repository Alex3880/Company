package com.netCracker.dao.xml;

import com.netCracker.dao.ProjectDao;
import com.netCracker.model.Project;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import com.netCracker.utils.XmlUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProjectDaoImpl implements ProjectDao {
    private final static String FILE_NAME = "xml/Project.xml";
    @Override
    public boolean create(Project entity) {
        return XmlUtils.creteEntity(entity,FILE_NAME);
    }

    @Override
    public Project getById(int id) {
        Document document = XmlUtils.getDocument(FILE_NAME);
        NodeList list = document.getElementsByTagName(Project.class.getSimpleName());
        Project project = null;
        for (int i = 0; i < list.getLength(); i++) {

            Node node = list.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                if (id == Integer.valueOf(element.getElementsByTagName("id").item(0).getFirstChild().getNodeValue())) {
                    project = new Project();
                    project.setId(Integer.valueOf(element.getElementsByTagName("id").item(0).getFirstChild().getNodeValue()));
                    project.setName(element.getElementsByTagName("name").item(0).getFirstChild().getNodeValue());
                    project.setIdCustomer(Integer.valueOf(element.getElementsByTagName("idCustomer").item(0).getFirstChild().getNodeValue()));
                    project.setDateBegin(element.getElementsByTagName("dateBegin").item(0).getFirstChild().getNodeValue());
                    project.setDateEnd(element.getElementsByTagName("dateEnd").item(0).getFirstChild().getNodeValue());
                    project.setIdManager(Integer.valueOf(element.getElementsByTagName("idManager").item(0).getFirstChild().getNodeValue()));
                }
            }

        }

        return project;
    }

    @Override
    public List<Project> getAll() {
        Document document = XmlUtils.getDocument(FILE_NAME);
        NodeList list = document.getElementsByTagName(Project.class.getSimpleName());
        List <Project>projects = new ArrayList<>();
        for (int i = 0; i < list.getLength(); i++) {

            Node node = list.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                Project project = new Project();
                project.setId(Integer.valueOf(element.getElementsByTagName("id").item(0).getFirstChild().getNodeValue()));
                project.setName(element.getElementsByTagName("name").item(0).getFirstChild().getNodeValue());
                project.setIdCustomer(Integer.valueOf(element.getElementsByTagName("idCustomer").item(0).getFirstChild().getNodeValue()));
                project.setDateBegin(element.getElementsByTagName("dateBegin").item(0).getFirstChild().getNodeValue());
                project.setDateEnd(element.getElementsByTagName("dateEnd").item(0).getFirstChild().getNodeValue());
                project.setIdManager(Integer.valueOf(element.getElementsByTagName("idManager").item(0).getFirstChild().getNodeValue()));
                projects.add(project);
            }

        }

        return projects;
    }

    @Override
    public boolean update(Project entity) {
        boolean updated = false;
        Document document = XmlUtils.getDocument(FILE_NAME);
        NodeList customers = document.getElementsByTagName(entity.getClass().getSimpleName());
        for (int i = 0; i < customers.getLength(); i++) {
            Node node = customers.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;

                if (entity.getId() == Integer.valueOf(element.getElementsByTagName("id").item(0).getFirstChild().getNodeValue())) {
                    element.getElementsByTagName("name").item(0).setTextContent(entity.getName());
                    element.getElementsByTagName("idCustomer").item(0).setTextContent(String.valueOf(entity.getIdCustomer()));
                    element.getElementsByTagName("dateBegin").item(0).setTextContent(entity.getDateBegin());
                    element.getElementsByTagName("dateEnd").item(0).setTextContent(entity.getDateEnd());
                    element.getElementsByTagName("idManager").item(0).setTextContent(String.valueOf(entity.getIdManager()));
                    updated = true;
                }
            }

        }
        XmlUtils.setDocument(document,FILE_NAME);


        return updated;
    }

    @Override
    public boolean delete(Project entity) {
        return delete(entity.getId());
    }

    @Override
    public boolean delete(int id) {
        return XmlUtils.deleteById("Projects",Project.class.getSimpleName(),id,FILE_NAME);
    }

}
