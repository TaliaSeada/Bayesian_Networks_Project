import java.util.ArrayList;

public class bayesianNetwork {
    ArrayList<bayesianNode> _bayesianNetwork = new ArrayList<bayesianNode>();

    //basic constructor
    public bayesianNetwork() {
    }

    //copy constructor
    public bayesianNetwork(bayesianNetwork other) {
        this._bayesianNetwork = new ArrayList<bayesianNode>(other._bayesianNetwork);
    }

    /*
       this function checks if the parent is already in the network
       in order to work recursively with the network we will have to insert the children references
       to their parents, and the parents to their children
    */
    public bayesianNode returnByName(String node) {
        for (int i = 0; i < this._bayesianNetwork.size(); i++) {
            //if my network contains this nodes name
            if (this._bayesianNetwork.get(i).getName().equals(node)) {
                return this._bayesianNetwork.get(i);
            }
        }
        return null;
    }

    /*
        this function adds nodes and set their parents to be their parent
        meaning: add the nodes to their children array
     */
    public void add_set(bayesianNode node) {
        if (!this._bayesianNetwork.contains(node)) {
            this._bayesianNetwork.add(node);
            setParent(node);
        }
    }

    private void setParent(bayesianNode node) {
        for (int i = 0; i < node.getParents().size(); i++) {
            if (!(node.getParents().get(i).getChildren().contains(node))) {
                node.getParents().get(i).getChildren().add(node);
            }
        }
    }


    public void fixNet() {
        for (int i = 0; i < this._bayesianNetwork.size(); i++) {
            //fix children
            bayesianNode curr = returnByName(this._bayesianNetwork.get(i).getName());
            //check if i is in its parents list
            for (int j = 0; j < curr.getParents().size(); j++) {
                bayesianNode parent = returnByName(curr.getParents().get(j).getName());
                if (!parent.getChildren().contains(curr)) {
                    parent.getChildren().add(curr);
                }
            }
            //fix outcomes
            bayesianNode node = _bayesianNetwork.get(i);
            for (int j = 0; j < node.getParents().size(); j++) {
                bayesianNode p = returnByName(node.getParents().get(j).getName());
                if (node.getParents().get(j).getOutcomes().size() == 0) {
                    node.getParents().get(j).setOutcomes(p.getOutcomes());
                }
            }
        }
    }


}
