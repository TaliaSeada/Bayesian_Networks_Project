
public class Ex1 {

    public static void main(String[] args) {
        bayesianNetwork BN = new bayesianNetwork();
        BN = readXMLfile.read_net("src/alarm_net.xml");
//        BN = readXMLfile.read_net("src/big_net.xml");
//        BN = readXMLfile.read_net("src/example.xml");

        String path = "src/input.txt";
        readTextfile reader = new readTextfile(path);
        String s = reader.readfile();
        System.out.println(s);
//        System.out.println(BN._bayesianNetwork);

        int node  = 2;
//        System.out.println(BN._bayesianNetwork.get(node).getCPT());
//        String[] ev = {"B=T"};
////        String[] ev = {"C2=v1","C1=T"};
//        factor f = new factor(BN._bayesianNetwork.get(node).getCPT(), ev);
//        System.out.println(f.factor.get(0));
//        f.removeIrrelevantRows();
//        System.out.println(f.factor);

//        bayesianNode query = BN._bayesianNetwork.get(node);
//        bayesianNode hidden = BN.returnByName("B0");
//        boolean an = isAncestor(hidden,query);
//        System.out.println(an);

//        String q = "A2=T";
//        String[] h = {"D1", "B3", "C1", "B0", "A1", "B1", "A3"};
//        String[] e = {"C3=T", "B2=F", "C2=v3"};
//        variableElimination ve = new variableElimination(q, h, e, BN);
//        System.out.println(ve.factors);
//        System.out.println(ve.factors.get(0));
//        System.out.println(ve.factors.get(4));
//        factor n = ve.join(ve.factors.get(0), ve.factors.get(4), BN.returnByName("A1"));
//        System.out.println(n);

        String q = "B=T";
        String[] h = {"A", "E"};
        String[] e = {"J=T", "M=T"};
        variableElimination ve = new variableElimination(q, h, e, BN);
//        System.out.println(ve.factors);
//        System.out.println(ve.factors.get(2));
//        System.out.println(ve.factors.get(3));
        factor n = ve.join2(ve.factors.get(2), ve.factors.get(3));
//        System.out.println(n);
//        System.out.println(ve.factors.get(4));
//        factor n2 = ve.join(ve.factors.get(4), n, BN.returnByName("A"));
//        System.out.println(n2);
        factor n3 = ve.join2(n, ve.factors.get(4));
//        System.out.println(n3);
        factor elim1 = ve.eliminate(n3, BN.returnByName("A"));
//        System.out.println(elim1);
        factor n4 = ve.join2(ve.factors.get(1), elim1);
//        System.out.println(n4);
        factor elim2 = ve.eliminate(n4, BN.returnByName("E"));
//        System.out.println(elim2);
        factor n5 = ve.join2(ve.factors.get(0), elim2);
//        System.out.println(n5);
        factor norm = ve.normalize(n5);
        System.out.println(norm);



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
