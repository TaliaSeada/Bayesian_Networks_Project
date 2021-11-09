
public class Main {
    private static final String ALARM = "src/alarm_net.xml";
    private static final String BIG = "src/big_net.xml";
    public static void main(String[] args){
        bayesianNetwork BN = new bayesianNetwork();
        BN = readXMLfile.read_net(ALARM);
        System.out.println(BN._bayesianNetwork);
    }
}
