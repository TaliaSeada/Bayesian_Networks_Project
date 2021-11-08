import java.util.ArrayList;

public class bayesianNode {
    private String name;
    private ArrayList<bayesianNode> parents;
    private ArrayList<bayesianNode> children;
//    private CPT cpt;

    //basic constructor
    public bayesianNode(String name) {
        this.name = name;
        this.parents = new ArrayList<bayesianNode>();
        this.children = new ArrayList<bayesianNode>();
    }
    //
    public bayesianNode(String event, ArrayList<bayesianNode> parents, ArrayList<bayesianNode> children) {
        this.name = event;
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
