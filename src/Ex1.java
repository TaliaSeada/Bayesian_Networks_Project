
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

        System.out.println(BN._bayesianNetwork.get(1).getCPT());
        String[] ev = {"J=T","M=T"};
        factor f = new factor(BN._bayesianNetwork.get(1).getName(), BN._bayesianNetwork.get(1).getCPT(), ev);
        System.out.println(f.factor);
        f.removeIrrelevantRows();
        System.out.println(f.factor);



    }
    public void writefile() {
//        TODO
    }
}
