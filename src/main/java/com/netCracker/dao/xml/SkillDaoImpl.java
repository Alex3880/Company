package com.netCracker.dao.xml;

import com.netCracker.dao.SkillDao;
import com.netCracker.model.Skill;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import com.netCracker.utils.XmlUtils;
import java.util.ArrayList;
import java.util.List;

public class SkillDaoImpl implements SkillDao {
    private final static String FILE_NAME = "xml/Skill.xml";
    @Override
    public boolean create(Skill entity) {
        return XmlUtils.creteEntity(entity,FILE_NAME);
    }

    @Override
    public Skill getById(int id) {
        Document document = XmlUtils.getDocument(FILE_NAME);
        NodeList list = document.getElementsByTagName(Skill.class.getSimpleName());
        Skill skill = null;
        for (int i = 0; i < list.getLength(); i++) {

            Node node = list.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                if (id == Integer.valueOf(element.getElementsByTagName("id").item(0).getFirstChild().getNodeValue())) {
                    skill = new Skill();
                    skill.setId(Integer.valueOf(element.getElementsByTagName("id").item(0).getFirstChild().getNodeValue()));
                    skill.setSkillName(element.getElementsByTagName("skillName").item(0).getFirstChild().getNodeValue());
                }
            }
        }
        return skill;
    }

    @Override
    public List<Skill> getAll() {
        Document document = XmlUtils.getDocument(FILE_NAME);
        NodeList list = document.getElementsByTagName(Skill.class.getSimpleName());
        List<Skill> skills = new ArrayList<>();
        for (int i = 0; i < list.getLength(); i++) {

            Node node = list.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                Skill sprint = new Skill();
                sprint.setId(Integer.valueOf(element.getElementsByTagName("id").item(0).getFirstChild().getNodeValue()));
                sprint.setSkillName(element.getElementsByTagName("skillName").item(0).getFirstChild().getNodeValue());
                skills.add(sprint);
            }
        }
        return skills;
    }

    @Override
    public boolean update(Skill entity) {
        boolean updated = false;
        Document document = XmlUtils.getDocument(FILE_NAME);
        NodeList customers = document.getElementsByTagName(entity.getClass().getSimpleName());
        for (int i = 0; i < customers.getLength(); i++) {
            Node node = customers.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;

                if (entity.getId() == Integer.valueOf(element.getElementsByTagName("id").item(0).getFirstChild().getNodeValue())) {
                    element.getElementsByTagName("skillName").item(0).setTextContent(entity.getSkillName());
                    updated = true;
                }
            }

        }
        XmlUtils.setDocument(document,FILE_NAME);
        return updated;
    }

    @Override
    public boolean delete(Skill entity) {
        return XmlUtils.deleteById("Skills",entity.getClass().getSimpleName(),entity.getId(),FILE_NAME);
    }

}
