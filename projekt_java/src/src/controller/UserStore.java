package controller;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class UserStore {
    private final Map<String, model.User> users = new HashMap<>();

    public UserStore(String xmlFilePath) throws Exception {
        File xmlFile = new File(xmlFilePath);
        if (!xmlFile.exists()) {
            throw new IllegalArgumentException("Plik XML nie znaleziony: " + xmlFilePath);
        }
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(xmlFile);
        doc.getDocumentElement().normalize();

        NodeList nodeList = doc.getElementsByTagName("user");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() != Node.ELEMENT_NODE) continue;
            Element e = (Element) node;
            String username = e.getElementsByTagName("username").item(0).getTextContent().trim();
            String password = e.getElementsByTagName("password").item(0).getTextContent().trim();
            String role = e.getElementsByTagName("role").item(0).getTextContent().trim().toUpperCase();
            users.put(username, new model.User(username, password, role));
        }
    }

    public model.User getUserByUsername(String username) {
        return users.get(username);
    }
}
