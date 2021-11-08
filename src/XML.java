import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
public class XML {
    private static final String ALARM = "src/alarm_net.xml";
    private static final String BIG = "src/big_net.xml";

    public static LinkedList[] read_net(String filename){
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        LinkedList[] list = new LinkedList[4];

        LinkedList<String> variables = new LinkedList<String>();
        LinkedList<String> outcomes = new LinkedList<String>();
        LinkedList<String> givens = new LinkedList<String>();
        LinkedList<String> tables = new LinkedList<String>();

        try {
            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            // parse XML file
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document big_net = db.parse(new File(filename));
            big_net.getDocumentElement().normalize();
            System.out.println("Root Element :" + big_net.getDocumentElement().getNodeName());
            System.out.println("-----------------------------");

            // get <VARIABLE>
            NodeList variable = big_net.getElementsByTagName("VARIABLE");
            // get <DEFINITION>
            NodeList definition = big_net.getElementsByTagName("DEFINITION");
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        return list;
    }
}
