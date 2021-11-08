import java.util.ArrayList;

public class bayesianNode {
    private String event;
    private ArrayList<bayesianNode> parents;
    private ArrayList<bayesianNode> children;
//    private CPT cpt;

    public bayesianNode() {
        this.event = event;
        this.parents = new ArrayList<bayesianNode>();
        this.children = new ArrayList<bayesianNode>();
    }
    public bayesianNode(String event, ArrayList<bayesianNode> parents, ArrayList<bayesianNode> children) {
        this.event = event;
        for(int i = 0; i < parents.size(); i++){
            this.parents.add(parents.get(i));
        }
        for(int i = 0; i < parents.size(); i++){
            this.children.add(children.get(i));
        }
    }
    public bayesianNode(bayesianNode other) {
        this.event = other.getEvent();
        for(int i = 0; i < other.getParents().size(); i++){
            this.parents.add(other.getParents().get(i));
        }
        for(int i = 0; i < other.getParents().size(); i++){
            this.children.add(other.getChildren().get(i));
        }
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
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
