package controller;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import model.Part;

public class PartStore {
    private final List<Part> parts = new ArrayList<>();

    public PartStore(String xmlPath) throws Exception {
        File xmlFile = new File(xmlPath);
        if (!xmlFile.exists()) {
            throw new IllegalArgumentException("Brak pliku: " + xmlPath);
        }
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(xmlFile);
        doc.getDocumentElement().normalize();

        NodeList nodeList = doc.getElementsByTagName("czesc");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() != Node.ELEMENT_NODE) continue;
            Element e = (Element) node;

            int id = Integer.parseInt(e.getElementsByTagName("id").item(0).getTextContent().trim());
            String name = e.getElementsByTagName("nazwa").item(0).getTextContent().trim();
            String catalog = e.getElementsByTagName("numer_katalogowy").item(0).getTextContent().trim();
            int price = Integer.parseInt(e.getElementsByTagName("cena").item(0).getTextContent().trim());

            parts.add(new Part(id, name, catalog, price));
        }
    }

    public List<Part> getAllParts() {
        return parts;
    }
}
