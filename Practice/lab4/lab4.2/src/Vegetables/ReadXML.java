package Vegetables;

import Vegetables.Imported.Exotic;
import Vegetables.Imported.Merchantable;
import Vegetables.Local.Canned;
import Vegetables.Local.Perennial;
import Vegetables.Local.Seasonal;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;

class ReadXML {


    private String getValue(NodeList fields, int index) {
        NodeList list = fields.item(index).getChildNodes();
        if (list.getLength() > 0) {
            return list.item(0).getNodeValue();
        } else {
            return "";
        }
    }

    Salad readDataXML() {

        Salad vegetables = new Salad();

        SimpleDateFormat sdf = null;
        DocumentBuilderFactory dbf = null;
        DocumentBuilder db = null;
        Document doc = null;
        try {

            dbf = DocumentBuilderFactory.newInstance();
            db = dbf.newDocumentBuilder();

            FileInputStream fis = null;
            try {
                String INPUT = "src/vegetables.xml";
                fis = new FileInputStream(INPUT);
                doc = db.parse(fis);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            assert doc != null;
            doc.getDocumentElement().normalize();

            NodeList fields;
            NodeList seasonal = null;
            NodeList perennial = null;
            NodeList canned = null;
            NodeList exotic = null;
            NodeList merchantable = null;


            seasonal = doc.getElementsByTagName("Seasonal");
            perennial = doc.getElementsByTagName("Perennial");
            canned = doc.getElementsByTagName("Canned");
            exotic = doc.getElementsByTagName("Exotic");
            merchantable = doc.getElementsByTagName("Merchantable");

            for (int s = 0; s < seasonal.getLength(); s++) {
                Node node = seasonal.item(s);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element el = (Element) node;
                    fields = el.getElementsByTagName("field");
                    Seasonal seasonal1= new Seasonal(getValue(fields, 0), Integer.parseInt(getValue(fields, 1)), Integer.parseInt(getValue(fields, 2)), getValue(fields, 3));
                    vegetables.add(seasonal1);
                }
            }
            for (int s = 0; s < perennial.getLength(); s++) {
                Node node = perennial.item(s);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element el = (Element) node;
                    fields = el.getElementsByTagName("field");
                    Perennial perennial1 = new Perennial(getValue(fields, 0), Integer.parseInt(getValue(fields, 1)), Integer.parseInt(getValue(fields, 2)));
                    vegetables.add(perennial1);
                }
            }
            for (int s = 0; s < canned.getLength(); s++) {
                Node node = canned.item(s);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element el = (Element) node;
                    fields = el.getElementsByTagName("field");
                    Canned canned1 = new Canned(getValue(fields, 0), Integer.parseInt(getValue(fields, 1)), Integer.parseInt(getValue(fields, 2)),getValue(fields,3),Integer.parseInt(getValue(fields,4)));
                    vegetables.add(canned1);
                }
            }
            for (int s = 0; s < exotic.getLength(); s++) {
                Node node = exotic.item(s);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element el = (Element) node;
                    fields = el.getElementsByTagName("field");
                    Exotic exotic1 = new Exotic(getValue(fields, 0), Integer.parseInt(getValue(fields, 1)), Integer.parseInt(getValue(fields, 2)),getValue(fields,3));
                    vegetables.add(exotic1);
                }
            }
            for (int s = 0; s < merchantable.getLength(); s++) {
                Node node = merchantable.item(s);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element el = (Element) node;
                    fields = el.getElementsByTagName("field");
                    Merchantable merchantable1 = new Merchantable(getValue(fields, 0), Integer.parseInt(getValue(fields, 1)), Integer.parseInt(getValue(fields, 2)),getValue(fields,3));
                    vegetables.add(merchantable1);
                }
            }

        } catch (Exception e){
            System.out.println("INCORRECT FORMAT");
        }
        return vegetables;
    }
}
