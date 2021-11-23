import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

public class bayesianNode {
    private String name;
    private ArrayList<bayesianNode> parents = new ArrayList<bayesianNode>();
    private ArrayList<bayesianNode> children = new ArrayList<bayesianNode>();
    private ArrayList<String> outcomes = new ArrayList<String>();
    private bayesianNetwork BN; //links between the BN to its nodes
    private ArrayList<HashMap> cpt;

    //basic constructor
    public bayesianNode(String name) {
        this.name = name;
    }

    //copy constructor
    public bayesianNode(bayesianNode other) {
        this.name = other.getName();
        this.parents = new ArrayList<bayesianNode>();
        this.children = new ArrayList<bayesianNode>();
        for (int i = 0; i < other.getParents().size(); i++) {
            this.parents.add(other.getParents().get(i));
        }
        for (int i = 0; i < other.getParents().size(); i++) {
            this.children.add(other.getChildren().get(i));
        }
        this.cpt = new ArrayList<HashMap>();
        for (int i = 0; i < other.cpt.size(); i++) {
            this.cpt.add(other.cpt.get(i));
        }
        outcomes = new ArrayList<String>();
        for (int i = 0; i < other.outcomes.size(); i++) {
            this.outcomes.add(other.outcomes.get(i));
        }
    }

    //read from xml file constructor
    public bayesianNode(String name, ArrayList<String> parents, bayesianNetwork BN, ArrayList<String> outcomes) {
        this.name = name;
        this.BN = BN;
        //set parents by givens:
        for (int i = 0; i < parents.size(); i++) {
            //if the parent already exists don't add a new Node, add this parent
            if (this.BN.returnByName(parents.get(i)) != null) {
                this.parents.add(this.BN.returnByName(parents.get(i)));
            }
            //else create a new parent
            else {
                this.parents.add(new bayesianNode(parents.get(i)));
            }
        }
        for (int i = 0; i < outcomes.size(); i++) {
            this.outcomes.add(outcomes.get(i));
        }
        this.cpt = new ArrayList<HashMap>();
    }

    public void build(String[] table) {
        for (int i = 0; i < table.length; i++) {
            this.cpt.add(cpt(table[i], i));
        }
//        System.out.println(cpt);
    }
    private HashMap cpt(String prob, int index) {
        HashMap ln = new HashMap();
        String value = new String();
        int amountOfValues = outcomes.size();
        for(int i = 0; i < outcomes.size(); i++){
            value = this.outcomes.get((int) (index) % amountOfValues);
            ln.put(this.name, value);
        }
        for (int i = this.parents.size()-1; i >= 0 ; i--) {
            value = this.parents.get(i).outcomes.get((int) (index / amountOfValues) % this.parents.get(i).outcomes.size());
            amountOfValues *= this.parents.get(i).outcomes.size();
            ln.put(this.parents.get(i).getName(), value);
        }
        ln.put("P",prob);
//        System.out.println(ln);
        return ln;
    }

    //to string function
    public String toString() {
        String res = "name: " + name + "\nparents = [";
        for (int i = 0; i < parents.size(); i++) {
            res += parents.get(i).getName() + ",";
        }
        res += "]\nchildren = [";
        for (int i = 0; i < children.size(); i++) {
            res += children.get(i).getName() + ",";
        }
        res += "]\n";
        return res;
    }

    //getters
    public String getName() {
        return name;
    }

    public ArrayList<bayesianNode> getParents() {
        return parents;
    }

    public ArrayList<bayesianNode> getChildren() {
        return children;
    }

    public ArrayList<String> getOutcomes() {
        return outcomes;
    }
    public void setOutcomes(ArrayList<String> outcomes){
        this.outcomes = new ArrayList<String>();
        for (int i = 0; i < outcomes.size(); i++) {
            this.outcomes.add(outcomes.get(i));
        }
    }

}
