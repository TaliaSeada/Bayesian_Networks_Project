import java.util.ArrayList;

public class bayesianNode {
    private String name;
    private ArrayList<bayesianNode> parents;
    private ArrayList<bayesianNode> children;
    private bayesianNetwork BN;

//    private CPT cpt;

    //basic constructor
    public bayesianNode(String name) {
        this.name = name;
        this.parents = new ArrayList<bayesianNode>();
        this.children = new ArrayList<bayesianNode>();
    }

    public bayesianNode(String name, ArrayList<String> parents, bayesianNetwork BN) {
        this.name = name;
        this.BN = BN;
        this.parents = new ArrayList<bayesianNode>();
        for(int i = 0; i < parents.size(); i++){
            //if the parent already exists dont add a new Node, add this parent
            if(this.BN.returnByName(parents.get(i)) != null){
                this.parents.add(this.BN.returnByName(parents.get(i)));
            }
            //else create a new parent
            else{
                this.parents.add(new bayesianNode(parents.get(i)));
            }
        }
        this.children = new ArrayList<bayesianNode>();
    }
    //
    public bayesianNode(String event, ArrayList<bayesianNode> parents, ArrayList<bayesianNode> children) {
        this.name = event;
        this.parents = new ArrayList<bayesianNode>();
        this.children = new ArrayList<bayesianNode>();
        for(int i = 0; i < parents.size(); i++){
            this.parents.add(parents.get(i));
        }
        for(int i = 0; i < parents.size(); i++){
            this.children.add(children.get(i));
        }
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

    public void addParent(bayesianNode parent){
        this.parents.add(new bayesianNode(parent));
    }

    public void addChild(bayesianNode child){
        this.children.add(child);
    }

    public String getName() {
        return name;
    }

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

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<bayesianNode> getParents() {
        return parents;
    }

    public void setParents(ArrayList<bayesianNode> parents) {
        this.parents = parents;
    }

    public ArrayList<bayesianNode> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<bayesianNode> children) {
        this.children = children;
    }
}
