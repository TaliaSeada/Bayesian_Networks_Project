
public class Main {

    public static void main(String[] args){
        bayesianNetwork BN = new bayesianNetwork();
//        BN = readXMLfile.read_net("src/alarm_net.xml");
        BN = readXMLfile.read_net("src/big_net.xml");
//        readTextfile reader = new readTextfile("src/input.txt");
//        String s = reader.readfile();
//        System.out.println(s);
        System.out.println(BN._bayesianNetwork);




    }
}
