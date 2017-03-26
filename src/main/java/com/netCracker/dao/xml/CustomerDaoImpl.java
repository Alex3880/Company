package com.netCracker.dao.xml;

import com.netCracker.dao.CustomerDao;
import com.netCracker.model.Customer;
import com.netCracker.security.MD5Hash;
import org.w3c.dom.*;
import com.netCracker.utils.XmlUtils;
import java.util.ArrayList;
import java.util.List;

public class CustomerDaoImpl implements CustomerDao {
    private final static String FILE_NAME = "xml/Customer.xml";
    @Override
    public boolean create(Customer entity) {
        MD5Hash md5Hash = new MD5Hash();
        entity.setPass(md5Hash.MD5(entity.getPass()));
        return XmlUtils.creteEntity(entity,FILE_NAME);
    }

    @Override
    public Customer getById(int id) {
        Document document = XmlUtils.getDocument(FILE_NAME);
        NodeList list = document.getElementsByTagName(Customer.class.getSimpleName());
        Customer customer = null;
        for (int i = 0; i < list.getLength(); i++) {

            Node node = list.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                if (id == Integer.valueOf(element.getElementsByTagName("id").item(0).getFirstChild().getNodeValue())) {
                    customer = new Customer();
                    customer.setId(Integer.valueOf(element.getElementsByTagName("id").item(0).getFirstChild().getNodeValue()));
                    customer.setName(element.getElementsByTagName("name").item(0).getFirstChild().getNodeValue());
                    customer.setLogin(element.getElementsByTagName("login").item(0).getFirstChild().getNodeValue());
                    customer.setPass(element.getElementsByTagName("pass").item(0).getFirstChild().getNodeValue());
                }
            }

        }

        return customer;
    }

    @Override
    public List<Customer> getAll() {
        Document document = XmlUtils.getDocument(FILE_NAME);
        NodeList list = document.getElementsByTagName(Customer.class.getSimpleName());
        List<Customer> listEntitys = new ArrayList<>();
        for (int i = 0; i < list.getLength(); i++) {

            Node node = list.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                Customer newCustomer = new Customer();
                newCustomer.setId(Integer.valueOf(element.getElementsByTagName("id").item(0).getFirstChild().getNodeValue()));
                newCustomer.setName(element.getElementsByTagName("name").item(0).getFirstChild().getNodeValue());
                newCustomer.setLogin(element.getElementsByTagName("login").item(0).getFirstChild().getNodeValue());
                newCustomer.setPass(element.getElementsByTagName("pass").item(0).getFirstChild().getNodeValue());
                listEntitys.add(newCustomer);
            }

        }

        return listEntitys;
    }

    @Override
    public boolean update(Customer entity) {
        boolean updated = false;
            Document document = XmlUtils.getDocument(FILE_NAME);
            NodeList customers = document.getElementsByTagName(entity.getClass().getSimpleName());
            for (int i = 0; i < customers.getLength(); i++) {
                Node node = customers.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;

                    if (entity.getId() == Integer.valueOf(element.getElementsByTagName("id").item(0).getFirstChild().getNodeValue())) {
                        MD5Hash md5 = new MD5Hash();
                        element.getElementsByTagName("name").item(0).setTextContent(entity.getName());
                        element.getElementsByTagName("login").item(0).setTextContent(entity.getLogin());
                        element.getElementsByTagName("pass").item(0).setTextContent(md5.isValidMD5(entity.getPass()) ? entity.getPass() : md5.MD5(entity.getPass()));
                        updated = true;
                    }
                }

            }
            XmlUtils.setDocument(document,FILE_NAME);


        return updated;
    }

    @Override
    public boolean delete(Customer entity) {
        return delete(entity.getId());
    }

    @Override
    public boolean delete(int id) {
        return XmlUtils.deleteById("Customers",Customer.class.getSimpleName(),id,FILE_NAME);
    }

}
