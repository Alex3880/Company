package com.netCracker.dao.xml;

import com.netCracker.dao.SprintDao;
import com.netCracker.model.Sprint;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import com.netCracker.utils.XmlUtils;

import java.util.ArrayList;
import java.util.List;

public class SprintDaoImpl implements SprintDao {
    private final static String FILE_NAME = "xml/Sprint.xml";

    @Override
    public boolean create(Sprint entity) {
        return XmlUtils.creteEntity(entity, FILE_NAME);
    }

    @Override
    public Sprint getById(int id) {
        Document document = XmlUtils.getDocument(FILE_NAME);
        NodeList list = document.getElementsByTagName(Sprint.class.getSimpleName());
        Sprint sprint = null;
        for (int i = 0; i < list.getLength(); i++) {

            Node node = list.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                if (id == Integer.valueOf(element.getElementsByTagName("id").item(0).getFirstChild().getNodeValue())) {
                    sprint = new Sprint();
                    sprint.setId(Integer.valueOf(element.getElementsByTagName("id").item(0).getFirstChild().getNodeValue()));
                    sprint.setName(element.getElementsByTagName("name").item(0).getFirstChild().getNodeValue());
                    sprint.setIdProject(Integer.valueOf(element.getElementsByTagName("idProject").item(0).getFirstChild().getNodeValue()));
                    sprint.setPrevSprint(Integer.valueOf(element.getElementsByTagName("prevSprint").item(0).getFirstChild().getNodeValue()));
                }
            }
        }
        return sprint;
    }

    @Override
    public List<Sprint> getAll() {
        Document document = XmlUtils.getDocument(FILE_NAME);
        NodeList list = document.getElementsByTagName(Sprint.class.getSimpleName());
        List<Sprint> sprints = new ArrayList<>();
        for (int i = 0; i < list.getLength(); i++) {
            Node node = list.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                Sprint sprint = new Sprint();
                sprint.setId(Integer.valueOf(element.getElementsByTagName("id").item(0).getFirstChild().getNodeValue()));
                sprint.setName(element.getElementsByTagName("name").item(0).getFirstChild().getNodeValue());
                sprint.setIdProject(Integer.valueOf(element.getElementsByTagName("idProject").item(0).getFirstChild().getNodeValue()));
                sprint.setPrevSprint(Integer.valueOf(element.getElementsByTagName("prevSprint").item(0).getFirstChild().getNodeValue()));
                sprints.add(sprint);
            }
        }
        return sprints;
    }

    @Override
    public boolean update(Sprint entity) {
        boolean updated = false;
        Document document = XmlUtils.getDocument(FILE_NAME);
        NodeList customers = document.getElementsByTagName(entity.getClass().getSimpleName());
        for (int i = 0; i < customers.getLength(); i++) {
            Node node = customers.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;

                if (entity.getId() == Integer.valueOf(element.getElementsByTagName("id").item(0).getFirstChild().getNodeValue())) {
                    element.getElementsByTagName("name").item(0).setTextContent(entity.getName());
                    element.getElementsByTagName("idProject").item(0).setTextContent(String.valueOf(entity.getIdProject()));
                    element.getElementsByTagName("prevSprint").item(0).setTextContent(String.valueOf(entity.getPrevSprint()));
                    updated = true;
                }
            }

        }
        XmlUtils.setDocument(document, FILE_NAME);
        return updated;
    }

    @Override
    public boolean delete(Sprint entity) {
        return delete(entity.getId());
    }

    @Override
    public boolean delete(int id) {
        return XmlUtils.deleteById("Sprints", Sprint.class.getSimpleName(), id, FILE_NAME);
    }

}
