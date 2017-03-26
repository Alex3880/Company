package com.netCracker.utils;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;

public class XmlUtils {

    public static boolean creteEntity(Object entity,String fileName) {
        boolean flag = false;
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(fileName);

            Class clazz = entity.getClass();
            Field[] fields = clazz.getDeclaredFields();
            Node EntityElement = document.getElementsByTagName(clazz.getSimpleName() + "s").item(0);

            Element newEntity = document.createElement(clazz.getSimpleName());
            EntityElement.appendChild(newEntity);

            for (Field field : fields) {
                field.setAccessible(true);
                Element element = document.createElement(field.getName());
                element.appendChild(document.createTextNode(field.get(entity).toString()));
                newEntity.appendChild(element);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File(fileName));
            transformer.transform(domSource, streamResult);
            flag = true;
        } catch (ParserConfigurationException pce) {
            System.out.println(pce.getLocalizedMessage());
            pce.printStackTrace();
        } catch (TransformerException te) {
            System.out.println(te.getLocalizedMessage());
            te.printStackTrace();
        } catch (IOException ioe) {
            System.out.println(ioe.getLocalizedMessage());
            ioe.printStackTrace();
        } catch (SAXException sae) {
            System.out.println(sae.getLocalizedMessage());
            sae.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return flag;
    }

    public static Document getDocument(String fileName) {
        Document document = null;
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            document = documentBuilder.parse(fileName);


        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return document;
    }

    public static void setDocument(Document document,String fileName) {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File(fileName));
            transformer.transform(domSource, streamResult);

        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    public static boolean deleteById(String containerEnity, String entity, int id,String fileName){
        boolean deleted = false;
        Document document = XmlUtils.getDocument(fileName);
        Node container  =document.getElementsByTagName(containerEnity).item(0);
        NodeList customers = document.getElementsByTagName(entity);
        for (int i = 0; i < customers.getLength(); i++) {
            Node node = customers.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;

                if (id == Integer.valueOf(element.getElementsByTagName("id").item(0).getFirstChild().getNodeValue())) {
                    container.removeChild(node);
                    deleted = true;
                }
            }

        }
        XmlUtils.setDocument(document,fileName);
        return deleted;
    }
}
