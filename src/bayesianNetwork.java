import java.util.ArrayList;
import java.util.LinkedList;

public class bayesianNetwork {
    ArrayList<bayesianNode> _bayesianNetwork;

    public bayesianNetwork() {
        this._bayesianNetwork = new ArrayList<bayesianNode>();
    }
    public bayesianNetwork(bayesianNetwork other){
        this._bayesianNetwork = new ArrayList<bayesianNode>(other._bayesianNetwork);
    }

    public void build_network(String fileName){
       LinkedList<LinkedList<String>> net = readXMLfile.read_net(fileName);
        for(int i = 0; i < net.size(); i+=4){
            bayesianNode n = new bayesianNode(net.get(i).get(0));
            if(net.get(i+2).size() != 0){
                for(int j = 0; j < net.get(i+2).size(); j++){
                    bayesianNode p = new bayesianNode(net.get(i+2).get(j));
                    n.addParent(p);
//                    this._bayesianNetwork.get().addChild(n);
                }

            }
            this._bayesianNetwork.add(n);
        }

        System.out.println(this._bayesianNetwork.toString());


    }

}
