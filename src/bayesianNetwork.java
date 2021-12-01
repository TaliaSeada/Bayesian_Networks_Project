import java.util.ArrayList;

/**
 * this class represents the Bayesian Network
 */
public class bayesianNetwork {
    ArrayList<bayesianNode> _bayesianNetwork = new ArrayList<bayesianNode>();

    //basic constructor
    public bayesianNetwork() {
    }

    //copy constructor
    public bayesianNetwork(bayesianNetwork other) {
        this._bayesianNetwork = new ArrayList<bayesianNode>(other._bayesianNetwork);
    }

    /**
     * this function checks if the parent is already in the network
     * in order to work recursively with the network we will have to insert the children references
     * to their parents, and the parents to their children
     * @param node - the name of the node we want to return from the network
     * @return - if the name of the node is in the network, we will return the node by its name, if not we will return null
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

    /**
     * this function adds nodes and set their parents to be their parent
     * meaning: add the nodes to their children array
     * @param node - the node we want to set
     */
    public void add_set(bayesianNode node) {
        if (!this._bayesianNetwork.contains(node)) {
            this._bayesianNetwork.add(node);
            setParent(node);
        }
    }
    // this function sets the parent list of a given node
    private void setParent(bayesianNode node) {
        for (int i = 0; i < node.getParents().size(); i++) {
            if (!(node.getParents().get(i).getChildren().contains(node))) {
                node.getParents().get(i).getChildren().add(node);
            }
        }
    }

    /**
     * this function works on this network, it iterates over the nodes in the network
     * and checks if its parents are up-to-date with after we build the network
     * also this function fixes the outcomes lists
     * we do that because while we build the network we define nodes that we didn't read yet
     */
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
