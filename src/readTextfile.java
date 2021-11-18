import java.io.*;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * this class reads the TEXT files
 */
public class readTextfile {
    private final String ALARM = "src/alarm_net.xml";
    private final String BIG = "src/big_net.xml";

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
//        if (this.lines.get(0).equals("alarm_net.xml")) {
//            BN = readXMLfile.read_net(ALARM);
//        }
//        if (this.lines.get(0).equals("big_net.xml")) {
//            BN = readXMLfile.read_net(BIG);
//        }
        for (int i = 1; i < lines.size(); i++) {
            if (lines.get(i).charAt(0) == 'P') {
                ans += "implement :/\n";
//                ans += variablesElimination() + "\n";
            } else {
                String[] given_split = lines.get(i).split("\\|");
                String[] query = given_split[0].split("-");
                ArrayList<bayesianNode> evidence = new ArrayList<>();
                if (given_split.length > 1) {
                    String[] ev = given_split[1].split(",");
                    for (int j = 0; j < ev.length; j++) {
                        bayesianNode e = BN.returnByName(ev[0].charAt(0)+"");
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