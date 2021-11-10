import java.io.*;
import java.util.ArrayList;

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
            while((line = br.readLine()) != null){
                lines.add(line);
            }
        } catch (FileNotFoundException e) {
            System.out.println("could not read this file.\n");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String readfile(){
        String ans = "";
        if(this.lines.get(0) == "alarm_net.xml"){
            BN = readXMLfile.read_net(ALARM);
        }
        if(this.lines.get(0) == "big_net.xml"){
            BN = readXMLfile.read_net(BIG);
        }
        for(int i = 1; i < lines.size(); i++){
            if(lines.get(i).charAt(0) == 'p'){
//                ans = variablesElimination();
            }
            else{
                bayesianNode src = BN.returnByName(lines.get(i).charAt(0) + "");
                bayesianNode dest = BN.returnByName(lines.get(i).charAt(2) + "");
                ArrayList<bayesianNode> evidence = new ArrayList<>();

                ans = BayesBall.isInd(BN, src,null, dest, evidence);
            }
        }
        return ans;
    }

}
