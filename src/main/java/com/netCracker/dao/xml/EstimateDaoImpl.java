package com.netCracker.dao.xml;

import com.netCracker.dao.EstimateDao;
import com.netCracker.model.Estimate;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import com.netCracker.utils.XmlUtils;
import java.util.ArrayList;
import java.util.List;

public class EstimateDaoImpl implements EstimateDao {
    private final static String FILE_NAME = "xml/Estimate.xml";
    @Override
    public boolean create(Estimate entity) {
        return XmlUtils.creteEntity(entity,FILE_NAME);
    }

    @Override
    public Estimate getById(int id) {
        Document document = XmlUtils.getDocument(FILE_NAME);
        NodeList list = document.getElementsByTagName(Estimate.class.getSimpleName());
        Estimate estimate = null;
        for (int i = 0; i < list.getLength(); i++) {

            Node node = list.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                if (id == Integer.valueOf(element.getElementsByTagName("id").item(0).getFirstChild().getNodeValue())) {
                    estimate = new Estimate();
                    estimate.setId(Integer.valueOf(element.getElementsByTagName("id").item(0).getFirstChild().getNodeValue()));
                    estimate.setHours(Integer.valueOf(element.getElementsByTagName("hours").item(0).getFirstChild().getNodeValue()));
                }
            }
        }
        return estimate;
    }

    @Override
    public List<Estimate> getAll() {
        Document document = XmlUtils.getDocument(FILE_NAME);
        NodeList list = document.getElementsByTagName(Estimate.class.getSimpleName());
        List<Estimate> estimates = new ArrayList<>();
        for (int i = 0; i < list.getLength(); i++) {

            Node node = list.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                Estimate estimate = new Estimate();
                estimate.setId(Integer.valueOf(element.getElementsByTagName("id").item(0).getFirstChild().getNodeValue()));
                estimate.setHours(Integer.valueOf(element.getElementsByTagName("hours").item(0).getFirstChild().getNodeValue()));
                estimates.add(estimate);
            }

        }

        return estimates;
    }

    @Override
    public boolean update(Estimate entity) {
        boolean updated = false;
        Document document = XmlUtils.getDocument(FILE_NAME);
        NodeList customers = document.getElementsByTagName(entity.getClass().getSimpleName());
        for (int i = 0; i < customers.getLength(); i++) {
            Node node = customers.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;

                if (entity.getId() == Integer.valueOf(element.getElementsByTagName("id").item(0).getFirstChild().getNodeValue())) {
                    element.getElementsByTagName("hours").item(0).setTextContent(String.valueOf(entity.getHours()));
                    updated = true;
                }
            }
        }
        XmlUtils.setDocument(document,FILE_NAME);
        return updated;
    }

    @Override
    public boolean delete(Estimate entity) {
        return XmlUtils.deleteById("Estimates",entity.getClass().getSimpleName(),entity.getId(),FILE_NAME);
    }

}
