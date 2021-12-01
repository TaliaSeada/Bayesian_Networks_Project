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

/**
 * this class reads the XML files
 */
public class readXMLfile {
    public static bayesianNetwork read_net(String filename){
        ArrayList<String[]> cpts = new ArrayList<String[]>();
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        //build a network
        bayesianNetwork BN = new bayesianNetwork();
        //create all the inputs in the xml file
        ArrayList<String> variables = new ArrayList<String>();
        ArrayList<String> outcomes = new ArrayList<String>();
        ArrayList<String> givens = new ArrayList<String>();
        ArrayList<String> tables = new ArrayList<String>();
        try {
            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            // parse XML file
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document net = db.parse(new File(filename));
            net.getDocumentElement().normalize();

            // get <VARIABLE>
            NodeList variable = net.getElementsByTagName("VARIABLE");
            // get <DEFINITION>
            NodeList definition = net.getElementsByTagName("DEFINITION");

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
                String[] table = tables.get(0).split(" ");
                cpts.add(table);
                bayesianNode bn = new bayesianNode(variables.get(0),givens, BN, outcomes);
                //add the node to the network
                BN.add_set(bn);
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        // if there are incorrect nodes - fix them
        BN.fixNet();
        //build CPT:
        for(int i = 0; i < BN._bayesianNetwork.size(); i++){
            BN._bayesianNetwork.get(i).build(cpts.get(i));
        }
        return BN;
    }



}