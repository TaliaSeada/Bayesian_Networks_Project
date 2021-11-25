
public class Ex1 {

    public static void main(String[] args) {
        bayesianNetwork BN = new bayesianNetwork();
        BN = readXMLfile.read_net("src/alarm_net.xml");
//        BN = readXMLfile.read_net("src/big_net.xml");
//        BN = readXMLfile.read_net("src/example.xml");

        String path = "src/input.txt";
//        readTextfile reader = new readTextfile(path);
//        String s = reader.readfile();
//        System.out.println(s);
//        System.out.println(BN._bayesianNetwork);

        int node  = 3;
        System.out.println(BN._bayesianNetwork.get(node).getCPT());
        String[] ev = {"E=T","A=T"};
        factor f = new factor(BN._bayesianNetwork.get(node).getName(), BN._bayesianNetwork.get(node).getCPT(), ev);
        f.removeIrrelevantRows();
        System.out.println(f.factor);



    }
    public void writefile() {
//        TODO
    }
}
