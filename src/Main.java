
public class Main {
    public static void main(String[] args){
        bayesianNetwork BN = new bayesianNetwork();
        BN = readXMLfile.read_net("src/alarm_net.xml");
        System.out.println(BN._bayesianNetwork);
    }
}
