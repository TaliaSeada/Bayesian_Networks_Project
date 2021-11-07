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

public class readXMLfile {
    private static final String FILENAME1 = "src/alarm_net.xml";
    private static final String FILENAME2 = "src/big_net.xml";
    public static void main(String[] args) {
        //alarm_net read:
        alarm_net();
        //big_net read:
        big_net();
    }

    public static void alarm_net(){
        System.out.println("ALARM NET:");
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            // parse XML file
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document alarm_net = db.parse(new File(FILENAME1));

            alarm_net.getDocumentElement().normalize();
            System.out.println("Root Element :" + alarm_net.getDocumentElement().getNodeName());
            System.out.println("-----------------------------");
            // get <VARIABLE>
            NodeList variable = alarm_net.getElementsByTagName("VARIABLE");
            // get <DEFINITION>
            NodeList definition = alarm_net.getElementsByTagName("DEFINITION");
            for (int temp = 0; temp < variable.getLength(); temp++) {
                Node node = variable.item(temp);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;

                    // get text
                    String name = element.getElementsByTagName("NAME").item(0).getTextContent();
                    String outcome1 = element.getElementsByTagName("OUTCOME").item(0).getTextContent();
                    String outcome2 = element.getElementsByTagName("OUTCOME").item(1).getTextContent();

                    System.out.println("NAME : " + name);
                    System.out.println("OUTCOME : " + outcome1);
                    System.out.println("OUTCOME : " + outcome2);
                    System.out.println();
                }
            }


            for (int temp = 0; temp < definition.getLength(); temp++) {
                Node node = definition.item(temp);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;

                    // get text
                    String name = element.getElementsByTagName("FOR").item(0).getTextContent();
                    String table = element.getElementsByTagName("TABLE").item(0).getTextContent();

                    System.out.println("FOR : " + name);
                    System.out.println("TABLE : " + table);
                    System.out.println();
                }
            }

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }

    public static void big_net(){
        System.out.println("BIG NET:");
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            // parse XML file
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document big_net = db.parse(new File(FILENAME2));
            big_net.getDocumentElement().normalize();
            System.out.println("Root Element :" + big_net.getDocumentElement().getNodeName());
            System.out.println("-----------------------------");

            // get <VARIABLE>
            NodeList variable = big_net.getElementsByTagName("VARIABLE");
            // get <DEFINITION>
            NodeList definition = big_net.getElementsByTagName("DEFINITION");

            for (int i = 0; i < variable.getLength(); i++) {
                LinkedList<String> variables = new LinkedList<String>();
                LinkedList<String> outcomes = new LinkedList<String>();
                LinkedList<String> givens = new LinkedList<String>();
                LinkedList<String> tables = new LinkedList<String>();

                LinkedList<String> definitions = new LinkedList<String>();
                Node var = variable.item(i);
                if (var.getNodeType() == Node.ELEMENT_NODE) {
                    Element outcome_var = (Element) var;

                    // get text
                    String name = outcome_var.getElementsByTagName("NAME").item(0).getTextContent();
                    variables.add(name);
                    for(int j=0;j< outcome_var.getElementsByTagName("OUTCOME").getLength();j++){
                        outcomes.add(outcome_var.getElementsByTagName("OUTCOME").item(j).getTextContent());
                    }
                }
                Node def = definition.item(i);
                if (def.getNodeType() == Node.ELEMENT_NODE) {
                    Element outcome_def = (Element) def;

                    // get text
                    String for_def = outcome_def.getElementsByTagName("FOR").item(0).getTextContent();
                    definitions.add(for_def);
                    for (int j = 0; j < outcome_def.getElementsByTagName("GIVEN").getLength(); j++) {
                        givens.add(outcome_def.getElementsByTagName("GIVEN").item(j).getTextContent());
                    }
                    for (int j = 0; j < outcome_def.getElementsByTagName("TABLE").getLength(); j++) {
                        tables.add(outcome_def.getElementsByTagName("TABLE").item(j).getTextContent());
                    }

                }
                System.out.println("VARIABLES : " + variables);
                System.out.println("OUTCOMES : " + outcomes);
                System.out.println("GIVENS : " + givens);
                System.out.println("TABLE : " + tables);
                System.out.println();
            }

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }



}
