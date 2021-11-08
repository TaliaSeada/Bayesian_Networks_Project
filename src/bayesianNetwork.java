import java.util.ArrayList;

public class bayesianNetwork {
    ArrayList<bayesianNode> _bayesianNode;

    public bayesianNetwork() {
        this._bayesianNode = new ArrayList<bayesianNode>();
    }
    public bayesianNetwork(bayesianNetwork other){
        this._bayesianNode = new ArrayList<bayesianNode>(other._bayesianNode);
    }

    public bayesianNetwork build_network(String fileName){
        readXMLfile.read_net(fileName);

    }

}
