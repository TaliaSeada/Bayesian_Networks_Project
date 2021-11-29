import java.io.*;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * this class reads the TEXT files
 */
public class readTextfile {
    private File path;
    private BufferedReader br;
    bayesianNetwork BN = new bayesianNetwork();
    ArrayList<String> lines = new ArrayList<String>();

    //constructor
    public readTextfile(String path) {
        this.path = new File(path);
        String line = "";
        try {
            br = new BufferedReader(new FileReader(this.path));
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (FileNotFoundException e) {
            System.out.println("could not read this file.\n");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String readfile() {
        String ans = "";
        BN = readXMLfile.read_net("src/" + this.lines.get(0));
        for (int i = 1; i < lines.size(); i++) {
            if (lines.get(i).charAt(0) == 'P') {
                String[] probs = new String[2];
                String[] hiddens = new String[2];
                String[] eliminate_split = lines.get(i).split(" ");
                probs[0] = eliminate_split[0];
                hiddens[0] = eliminate_split[1];
                probs[0] = probs[0].replace("P(","");
                probs[0] = probs[0].replace(")","");
                String[] hidden = hiddens[0].split("-");
                String[] given_split = probs[0].split("\\|");
                String query = given_split[0];
                String[] evi = given_split[1].split(",");
                variableElimination ve = new variableElimination(query, hidden, evi, BN);
                ans += ve.variableElimination() + "\n";
            }
            else {
                String[] given_split = lines.get(i).split("\\|");
                String[] query = given_split[0].split("-");
                ArrayList<bayesianNode> evidence = new ArrayList<>();
                if (given_split.length > 1) {
                    String[] ev = given_split[1].split(",");
                    for (int j = 0; j < ev.length; j++) {
                        String[] one = ev[j].split("=");
                        bayesianNode e = BN.returnByName(one[0]);
                        evidence.add(e);
                    }
                }
                bayesianNode src = BN.returnByName(query[0]);
                bayesianNode dest = BN.returnByName(query[1]);

                ans += BayesBall.isInd(BN, src, dest, evidence) + "\n";
            }
        }
        return ans;
    }


}