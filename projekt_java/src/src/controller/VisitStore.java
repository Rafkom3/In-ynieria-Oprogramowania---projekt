
package controller;

import model.Visit;
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

public class VisitStore {
    private final File xmlFile;
    private final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public VisitStore(String xmlPath) throws Exception {
        this.xmlFile = new File(xmlPath);
        if (!xmlFile.exists()) {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.newDocument();
            Element root = doc.createElement("visits");
            doc.appendChild(root);
            writeDocument(doc);
        }
    }

    public void saveVisit(Visit v) throws Exception {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(xmlFile);
        Element root = doc.getDocumentElement();

        Element visitEl = doc.createElement("visit");
        appendChildWithText(doc, visitEl, "username",    v.getUsername());
        appendChildWithText(doc, visitEl, "serviceType", v.getServiceType());
        appendChildWithText(doc, visitEl, "dateTime",    v.getDateTime().format(fmt));
        appendChildWithText(doc, visitEl, "contactInfo", v.getContactInfo());
        appendChildWithText(doc, visitEl, "notes",       v.getNotes());
        appendChildWithText(doc, visitEl, "status",      v.getStatus());

        root.appendChild(visitEl);
        writeDocument(doc);
    }

    private void appendChildWithText(Document doc, Element parent,
                                     String tag, String text) {
        Element el = doc.createElement(tag);
        el.setTextContent(text);
        parent.appendChild(el);
    }

    private void writeDocument(Document doc) throws Exception {
        Transformer tf = TransformerFactory.newInstance().newTransformer();
        tf.setOutputProperty(OutputKeys.INDENT, "yes");
        tf.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        DOMSource src = new DOMSource(doc);
        StreamResult out = new StreamResult(xmlFile);
        tf.transform(src, out);
    }

    public List<Visit> getAllVisits() throws Exception {
        List<Visit> list = new ArrayList<>();
        DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document doc = db.parse(xmlFile);
        NodeList nodes = doc.getElementsByTagName("visit");
        for (int i = 0; i < nodes.getLength(); i++) {
            Element e = (Element) nodes.item(i);
            String user   = e.getElementsByTagName("username").item(0).getTextContent();
            String svc    = e.getElementsByTagName("serviceType").item(0).getTextContent();
            String dt     = e.getElementsByTagName("dateTime").item(0).getTextContent();
            String contact= e.getElementsByTagName("contactInfo").item(0).getTextContent();
            String notes  = e.getElementsByTagName("notes").item(0).getTextContent();
            String stat   = e.getElementsByTagName("status").item(0).getTextContent();
            list.add(new Visit(user, svc,
                    LocalDateTime.parse(dt, fmt),
                    contact, notes, stat));
        }
        return list;
    }
}
