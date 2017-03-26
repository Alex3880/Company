package com.netCracker.dao.xml;

import com.netCracker.dao.EmployeeDao;
import com.netCracker.model.Employee;
import com.netCracker.model.Project;
import com.netCracker.model.User;
import com.netCracker.security.MD5Hash;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import com.netCracker.utils.XmlUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class EmployeeDaoImpl implements EmployeeDao {
    private final static String FILE_NAME = "xml/Employee.xml";
    @Override
    public boolean create(Employee entity) {
        MD5Hash md5Hash = new MD5Hash();
        entity.setPass(md5Hash.MD5(entity.getPass()));
        return XmlUtils.creteEntity(entity,FILE_NAME);
    }

    @Override
    public Employee getById(int id) {
        Document document = XmlUtils.getDocument(FILE_NAME);
        NodeList list = document.getElementsByTagName(Employee.class.getSimpleName());
        Employee employee = null;
        for (int i = 0; i < list.getLength(); i++) {

            Node node = list.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                if (id == Integer.valueOf(element.getElementsByTagName("id").item(0).getFirstChild().getNodeValue())) {
                    employee = new Employee();
                    employee.setId(Integer.valueOf(element.getElementsByTagName("id").item(0).getFirstChild().getNodeValue()));
                    employee.setName(element.getElementsByTagName("name").item(0).getFirstChild().getNodeValue());
                    employee.setLastName(element.getElementsByTagName("lastName").item(0).getFirstChild().getNodeValue());
                    employee.setIdSkill(Integer.valueOf(element.getElementsByTagName("idSkill").item(0).getFirstChild().getNodeValue()));
                    employee.setLogin(element.getElementsByTagName("login").item(0).getFirstChild().getNodeValue());
                    employee.setPass(element.getElementsByTagName("pass").item(0).getFirstChild().getNodeValue());
                }
            }
        }
        return employee;
    }

    @Override
    public List<Employee> getAll() {
        Document document = XmlUtils.getDocument(FILE_NAME);
        NodeList list = document.getElementsByTagName(Employee.class.getSimpleName());
        List<Employee> employees = new ArrayList<>();
        for (int i = 0; i < list.getLength(); i++) {
            Node node = list.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                Employee employee = new Employee();
                employee.setId(Integer.valueOf(element.getElementsByTagName("id").item(0).getFirstChild().getNodeValue()));
                employee.setName(element.getElementsByTagName("name").item(0).getFirstChild().getNodeValue());
                employee.setLastName(element.getElementsByTagName("lastName").item(0).getFirstChild().getNodeValue());
                employee.setIdSkill(Integer.valueOf(element.getElementsByTagName("idSkill").item(0).getFirstChild().getNodeValue()));
                employee.setLogin(element.getElementsByTagName("login").item(0).getFirstChild().getNodeValue());
                employee.setPass(element.getElementsByTagName("pass").item(0).getFirstChild().getNodeValue());
                employees.add(employee);
            }
        }
        return employees;
    }

    @Override
    public boolean update(Employee entity) {
        boolean updated = false;
        Document document = XmlUtils.getDocument(FILE_NAME);
        NodeList customers = document.getElementsByTagName(entity.getClass().getSimpleName());
        for (int i = 0; i < customers.getLength(); i++) {
            Node node = customers.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;

                if (entity.getId() == Integer.valueOf(element.getElementsByTagName("id").item(0).getFirstChild().getNodeValue())) {
                    element.getElementsByTagName("name").item(0).setTextContent(entity.getName());
                    element.getElementsByTagName("lastName").item(0).setTextContent(entity.getLastName());
                    element.getElementsByTagName("idSkill").item(0).setTextContent(String.valueOf(entity.getIdSkill()));
                    element.getElementsByTagName("login").item(0).setTextContent(entity.getLastName());
                    MD5Hash md5Hash = new MD5Hash();
                    element.getElementsByTagName("pass").item(0).setTextContent(md5Hash.isValidMD5(entity.getPass()) ? entity.getPass() : md5Hash.MD5(entity.getPass()));
                    updated = true;
                }
            }

        }
        XmlUtils.setDocument(document,FILE_NAME);
        return updated;
    }

    @Override
    public boolean delete(Employee entity) {
        return delete(entity.getId());
    }

    @Override
    public boolean delete(int id) {
        return XmlUtils.deleteById("Employees",Employee.class.getSimpleName(),id,FILE_NAME);
    }

}
