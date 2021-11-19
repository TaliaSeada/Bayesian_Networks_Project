
public class Main {

    public static void main(String[] args) {
        bayesianNetwork BN = new bayesianNetwork();
//        BN = readXMLfile.read_net("src/alarm_net.xml");
//        BN = readXMLfile.read_net("src/big_net.xml");
//        BN = readXMLfile.read_net("src/example.xml");

        String path = "src/input3.txt";
        readTextfile reader = new readTextfile(path);
        String s = reader.readfile();
        System.out.println(s);
//        System.out.println(BN._bayesianNetwork);


    }
}
