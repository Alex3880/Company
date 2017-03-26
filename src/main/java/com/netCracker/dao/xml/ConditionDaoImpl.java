package com.netCracker.dao.xml;

import com.netCracker.dao.ConditionDao;
import com.netCracker.model.Condition;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import com.netCracker.utils.XmlUtils;

import java.util.ArrayList;
import java.util.List;


public class ConditionDaoImpl implements ConditionDao {
    private final static String FILE_NAME = "xml/Condition.xml";
    @Override
    public boolean create(Condition entity) {
        return XmlUtils.creteEntity(entity,FILE_NAME);
    }

    @Override
    public Condition getById(int id) {
        Document document = XmlUtils.getDocument(FILE_NAME);
        NodeList list = document.getElementsByTagName(Condition.class.getSimpleName());
        Condition condition = null;
        for (int i = 0; i < list.getLength(); i++) {

            Node node = list.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                if (id == Integer.valueOf(element.getElementsByTagName("id").item(0).getFirstChild().getNodeValue())) {
                    condition = new Condition();
                    condition.setId(Integer.valueOf(element.getElementsByTagName("id").item(0).getFirstChild().getNodeValue()));
                    condition.setName(element.getElementsByTagName("name").item(0).getFirstChild().getNodeValue());
                }
            }
        }
        return condition;
    }

    @Override
    public List<Condition> getAll() {
        Document document = XmlUtils.getDocument(FILE_NAME);
        NodeList list = document.getElementsByTagName(Condition.class.getSimpleName());
        List<Condition> conditions = new ArrayList<>();
        for (int i = 0; i < list.getLength(); i++) {

            Node node = list.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                Condition sprint = new Condition();
                sprint.setId(Integer.valueOf(element.getElementsByTagName("id").item(0).getFirstChild().getNodeValue()));
                sprint.setName(element.getElementsByTagName("name").item(0).getFirstChild().getNodeValue());
                conditions.add(sprint);
            }
        }
        return conditions;
    }

    @Override
    public boolean update(Condition entity) {
        boolean updated = false;
        Document document = XmlUtils.getDocument(FILE_NAME);
        NodeList customers = document.getElementsByTagName(entity.getClass().getSimpleName());
        for (int i = 0; i < customers.getLength(); i++) {
            Node node = customers.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;

                if (entity.getId() == Integer.valueOf(element.getElementsByTagName("id").item(0).getFirstChild().getNodeValue())) {
                    element.getElementsByTagName("name").item(0).setTextContent(entity.getName());
                    updated = true;
                }
            }

        }
        XmlUtils.setDocument(document,FILE_NAME);
        return updated;
    }

    @Override
    public boolean delete(Condition entity) {
        return XmlUtils.deleteById("Conditions",entity.getClass().getSimpleName(),entity.getId(),FILE_NAME);
    }
}
