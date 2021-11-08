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
import java.util.ArrayList;
import java.util.LinkedList;

public class readXMLfile {
    private static final String ALARM = "src/alarm_net.xml";
    private static final String BIG = "src/big_net.xml";

    public static bayesianNetwork read_net(String filename){
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
//        ArrayList<ArrayList<String>> list = new ArrayList<>();
        bayesianNetwork BN = new bayesianNetwork();
        ArrayList<String> variables = new ArrayList<String>();
        ArrayList<String> outcomes = new ArrayList<String>();
        ArrayList<String> givens = new ArrayList<String>();
        ArrayList<String> tables = new ArrayList<String>();
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

            for (int i = 0; i < variable.getLength(); i++) {
                variables = new ArrayList<String>();
                outcomes = new ArrayList<String>();
                givens = new ArrayList<String>();
                tables = new ArrayList<String>();

                ArrayList<String> definitions = new ArrayList<String>();
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

//                list.add(variables);
//                list.add(outcomes);
//                list.add(givens);
//                list.add(tables);
                BN.add_set(new bayesianNode(variables.get(0),givens, BN));

//                System.out.println("VARIABLES : " + variables);
//                System.out.println("OUTCOMES : " + outcomes);
//                System.out.println("GIVENS : " + givens);
//                System.out.println("TABLE : " + tables);
//                System.out.println();
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        return BN;
    }


//    public static void main(String[] args) {
//        read_net(ALARM);
//    }

}
