import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Set;

/**
 * this class represents the Bayesian Network Node
 */
public class bayesianNode {
    private String name;
    private ArrayList<bayesianNode> parents = new ArrayList<bayesianNode>();
    private ArrayList<bayesianNode> children = new ArrayList<bayesianNode>();
    private ArrayList<String> outcomes = new ArrayList<String>();
    private bayesianNetwork BN; //links between the BN to its nodes
    private ArrayList<HashMap<String, String>> cpt;
    private factor f = new factor();

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
        this.cpt = new ArrayList<HashMap<String, String>>();
        for (int i = 0; i < other.cpt.size(); i++) {
            this.cpt.add(other.cpt.get(i));
        }
        outcomes = new ArrayList<String>();
        for (int i = 0; i < other.outcomes.size(); i++) {
            this.outcomes.add(other.outcomes.get(i));
        }
    }

    /**
     * this constructor gets its information from the xml file
     * @param name - the name of the node
     * @param parents - the givens nodes
     * @param BN - the network we build
     * @param outcomes - list of possible result (True, False, ect.)
     */
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
        // initial the cpt array list
        this.cpt = new ArrayList<HashMap<String, String>>();
    }

    /**
     * this function builds the cpt
     * the function calls to a help function that creates a row by sending it the index in the table and the value of it in the table
     * then it add the result of the help function to the general cpt (it returns one row)
     * @param table - list of the values for the cpt
     */
    public void build(String[] table) {
        for (int i = 0; i < table.length; i++) {
            this.cpt.add(cpt(table[i], i));
        }

    }

    /**
     * this function is a help function that actually builds the cpt
     * first it runs over the outcomes of the bayesian node then puts the value of the outcome in the cpt
     * then we run over the bayesian node's parents to add them to the cpt
     * so, we run over each parent outcomes and take the relevant outcome using the given index,
     * the calculation is:
     * 1. the given index / the amount of outcomes of the current parent (the amount of outcomes grows by the next parent outcomes)
     * 2. we do it in a cycle (the outcomes list length only contains the possible outcome - and we have more cases so, we always go back to the start)
     * 3. then we increase the amount of values we have
     * 4. then at last we add the key and the value to the current row
     * at the end we add the relevant probability to the row
     * @param prob - the value of probability we got from the table
     * @param index - the index of the given value of probability in the table
     * @return - the row we created
     */
    private HashMap cpt(String prob, int index) {
        HashMap ln = new HashMap(); // new row
        String value = new String(); // outcome
        int amountOfValues = outcomes.size();
        for(int i = 0; i < outcomes.size(); i++){
            // run over the outcomes in a cycle
            value = this.outcomes.get((int) (index) % amountOfValues);
            ln.put(this.name, value);
        }
        for (int i = this.parents.size()-1; i >= 0 ; i--) {
            value = this.parents.get(i).outcomes.get((int) (index / amountOfValues) % this.parents.get(i).outcomes.size());
            amountOfValues *= this.parents.get(i).outcomes.size();
            ln.put(this.parents.get(i).getName(), value);
        }
        ln.put("P",prob);
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
        res += this.cpt;
        return res;
    }

    //getters and setters
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

    public ArrayList<HashMap<String, String>> getCPT(){
        return this.cpt;
    }

    public void setFactor(factor a){
        this.f = a;
    }
    public factor getFactor(){
        return this.f;
    }
}
