
public class Ex1 {

    public static void main(String[] args) {
        bayesianNetwork BN = new bayesianNetwork();
//        BN = readXMLfile.read_net("src/alarm_net.xml");
        BN = readXMLfile.read_net("src/big_net.xml");
//        BN = readXMLfile.read_net("src/example.xml");

        String path = "src/input2.txt";
//        readTextfile reader = new readTextfile(path);
//        String s = reader.readfile();
//        System.out.println(s);
//        System.out.println(BN._bayesianNetwork);

        int node  = 6;
//        System.out.println(BN._bayesianNetwork.get(node).getCPT());
//        String[] ev = {"B=T","M=T"};
//        factor f = new factor(BN._bayesianNetwork.get(node).getName(), BN._bayesianNetwork.get(node).getCPT(), ev);
//        f.removeIrrelevantRows();
//        System.out.println(f.factor);

//        bayesianNode query = BN._bayesianNetwork.get(node);
//        bayesianNode hidden = BN.returnByName("B0");
//        boolean an = isAncestor(hidden,query);
//        System.out.println(an);

        String q = "B0=v3";
        String[] h = {"A2", "D1", "B3", "C1", "A1", "B1", "A3"};
        String[] e = {"C3=T", "B2=F", "C2=v3"};
        variableElimination ve = new variableElimination(q, h, e, BN);


    }
    public static boolean isAncestor(bayesianNode hidden, bayesianNode query){
        if (query.getParents().contains(hidden)) return true;
        boolean flag = false;
        for (int i = 0; i < query.getParents().size(); i++) {
            flag = isAncestor(hidden, query.getParents().get(i));
            if (flag) return true;
        }
        return false;
    }
    public void writefile() {
//        TODO
    }
}
