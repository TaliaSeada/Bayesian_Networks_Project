import java.util.ArrayList;
import java.util.LinkedList;

public class bayesianNetwork {
    ArrayList<bayesianNode> _bayesianNode;

    public bayesianNetwork() {
        this._bayesianNode = new ArrayList<bayesianNode>();
    }
    public bayesianNetwork(bayesianNetwork other){
        this._bayesianNode = new ArrayList<bayesianNode>(other._bayesianNode);
    }

    public static void build_network(String fileName){
       LinkedList<LinkedList<String>> net = readXMLfile.read_net(fileName);
        System.out.println(net);
        for(int i = 0; i < net.size(); i+=4){
            System.out.println(net.get(i));
            bayesianNode n = new bayesianNode(net.get(i).get(0));
            if(net.get(i+2).size() != 0){
                for(int j = 0; j < net.get(i+2).size(); j++){
                    bayesianNode p = new bayesianNode(net.get(i+2).get(j));
                    p.addChild(n);
                    n.addParent(p);

                }
            }
        }
        System.out.println(net.get(0));






    }



    public static void main(String[] args){

        build_network("src/alarm_net.xml");
    }
}
