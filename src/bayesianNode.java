import java.util.ArrayList;

public class bayesianNode {
    private String name;
    private ArrayList<bayesianNode> parents = new ArrayList<bayesianNode>();
    private ArrayList<bayesianNode> children = new ArrayList<bayesianNode>();
    private bayesianNetwork BN; //links between the BN to its nodes
    private boolean color = false;
//    private CPT cpt;
//    ArrayList<String>outcomes=new ArrayList<>();

    //basic constructor
    public bayesianNode(String name) {
        this.name = name;
    }
    //copy constructor
    public bayesianNode(bayesianNode other) {
        this.name = other.getName();
        this.parents = new ArrayList<bayesianNode>();
        this.children = new ArrayList<bayesianNode>();
        for(int i = 0; i < other.getParents().size(); i++){
            this.parents.add(other.getParents().get(i));
        }
        for(int i = 0; i < other.getParents().size(); i++){
            this.children.add(other.getChildren().get(i));
        }
    }

    //read from xml file constructor
    public bayesianNode(String name, ArrayList<String> parents, bayesianNetwork BN) {
        this.name = name;
        this.BN = BN;
        //set parents by givens:
        for(int i = 0; i < parents.size(); i++){
            //if the parent already exists don't add a new Node, add this parent
            if(this.BN.returnByName(parents.get(i)) != null){
                this.parents.add(this.BN.returnByName(parents.get(i)));
            }
            //else create a new parent
            else{
                this.parents.add(new bayesianNode(parents.get(i)));
            }
        }
    }

    //to string function
    public String toString(){
        String res = "name: " + name + "\nparents = [" ;
        for(int i = 0; i < parents.size(); i++){
            res += parents.get(i).getName() + ",";
        }
        res += "]\nchildren = [";
        for(int i = 0; i < children.size(); i++){
            res += children.get(i).getName() + ",";
        }
        res += "]\n";
        return res;
    }

    //getters
    public boolean getColor(){return color;}
    public String getName() {
        return name;
    }
    public ArrayList<bayesianNode> getParents() {
        return parents;
    }
    public ArrayList<bayesianNode> getChildren() {
        return children;
    }

    public boolean setColor(boolean color){return this.color = color;}
}
