// src/src/controller/OrderStore.java
package controller;

import model.Order;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OrderStore {
    private final File xmlFile;
    private final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public OrderStore(String path) throws Exception {
        xmlFile = new File(path);
        if (!xmlFile.exists()) {
            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = db.newDocument();
            Element root = doc.createElement("orders");
            doc.appendChild(root);
            writeDoc(doc);
        }
    }

    public void saveOrder(Order o) throws Exception {
        DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document doc = db.parse(xmlFile);
        Element root = doc.getDocumentElement();

        Element el = doc.createElement("order");
        append(doc, el, "orderId",   o.getOrderId());
        append(doc, el, "partName",  o.getPartName());
        append(doc, el, "quantity",  String.valueOf(o.getQuantity()));
        append(doc, el, "orderDate", o.getOrderDate().format(fmt));
        append(doc, el, "supplier",  o.getSupplier());
        append(doc, el, "status",    o.getStatus());

        root.appendChild(el);
        writeDoc(doc);
    }

    public List<Order> getAllOrders() throws Exception {
        List<Order> list = new ArrayList<>();
        DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document doc = db.parse(xmlFile);
        NodeList nodes = doc.getElementsByTagName("order");
        for (int i = 0; i < nodes.getLength(); i++) {
            Element e = (Element) nodes.item(i);
            String id    = e.getElementsByTagName("orderId").item(0).getTextContent();
            String part  = e.getElementsByTagName("partName").item(0).getTextContent();
            int qty      = Integer.parseInt(e.getElementsByTagName("quantity").item(0).getTextContent());
            String dt    = e.getElementsByTagName("orderDate").item(0).getTextContent();
            String sup   = e.getElementsByTagName("supplier").item(0).getTextContent();
            String stat  = e.getElementsByTagName("status").item(0).getTextContent();
            list.add(new Order(id, part, qty,
                    LocalDateTime.parse(dt, fmt),
                    sup, stat));
        }
        return list;
    }

    private void append(Document d, Element p, String tag, String txt) {
        Element ch = d.createElement(tag);
        ch.setTextContent(txt);
        p.appendChild(ch);
    }

    private void writeDoc(Document d) throws Exception {
        Transformer t = TransformerFactory.newInstance().newTransformer();
        t.setOutputProperty(OutputKeys.INDENT, "yes");
        t.setOutputProperty("{http://xml.apache.org/xslt}indent-amount","2");
        t.transform(new DOMSource(d), new StreamResult(xmlFile));
    }

    public static String generateId() {
        return UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
