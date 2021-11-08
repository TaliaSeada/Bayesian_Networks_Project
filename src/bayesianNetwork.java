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

    /*
        this function adds nodes and set their parents to be their parent
        meaning: add the nodes to their children array
     */
    public void add_set(bayesianNode node){
        if(!this._bayesianNetwork.contains(node)){
            this._bayesianNetwork.add(node);
            setParent(node);
        }
    }
    private void setParent(bayesianNode node){
         for(int i = 0; i < node.getParents().size(); i++){
             if(!(node.getParents().get(i).getChildren().contains(node))){
                 node.getParents().get(i).getChildren().add(node);
             }
         }
    }

    /*
        this function checks if the parent is already in the network
        in order to work recursively with the network we will have to insert the children references
        to their parents, and the parents to their children
     */
    public bayesianNode returnByName(String parent){
          for(int i = 0; i < this._bayesianNetwork.size(); i++){
              //if my network contains this parent
              if(this._bayesianNetwork.get(i).getName().equals(parent)){
                  return this._bayesianNetwork.get(i);
              }
          }
          return null;
    }


}
