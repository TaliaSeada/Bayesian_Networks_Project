import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;

/**
 * this class represents the Variable Elimination algorithm
 */
public class variableElimination {
    bayesianNode query;
    String str_query;
    ArrayList<bayesianNode> hidden = new ArrayList<bayesianNode>();
    ArrayList<bayesianNode> evidence = new ArrayList<bayesianNode>();
    ArrayList<String> evi = new ArrayList<String>();
    bayesianNetwork BN;
    ArrayList<factor> factors = new ArrayList<factor>();
    int multiply = 0;
    int add = 0;

    // this constructor build the relevant factors to a given query
    public variableElimination(String query, String[] hidden, String[] evidence, bayesianNetwork BN) {
        String[] q = query.split("=");
        this.query = BN.returnByName(q[0]);
        this.str_query = query;
        for (int i = 0; i < hidden.length; i++) {
            this.hidden.add(BN.returnByName(hidden[i]));
        }
        for (int i = 0; i < evidence.length; i++) {
            this.evi.add(evidence[i]);
            String[] e = evidence[i].split("=");
            this.evidence.add(BN.returnByName(e[0]));
        }
        this.BN = BN;
        //init factors:
        generateFactors(); // init function
        removeOneSize(this.factors); // remove factors with only one row in them
        sort(this.factors); // sort the factors by their size, if equal sort by ascii value
    }

    // this function generates the relevant factor for the given question
    private void generateFactors() {
        ArrayList<bayesianNode> irrelevant = findIrrelevantNodes(); // find the irrelevant nodes
        // remove the factors that the irrelevant nodes are in - we don't need them
        for (int i = 0; i < BN._bayesianNetwork.size(); i++) {
            for (int j = 0; j < BN._bayesianNetwork.get(i).getCPT().size(); j++) {
                for (String key : BN._bayesianNetwork.get(i).getCPT().get(j).keySet()) {
                    if (irrelevant.contains(BN.returnByName(key))) {
                        irrelevant.add(BN._bayesianNetwork.get(i));
                    }
                }
            }
        }
        // create only the relevant nodes
        for (int i = 0; i < BN._bayesianNetwork.size(); i++) {
            if (!irrelevant.contains(BN._bayesianNetwork.get(i))) {
                String[] e = new String[this.evi.size()];
                for (int j = 0; j < this.evi.size(); j++) {
                    e[j] = this.evi.get(j);
                }
                factor f = new factor(BN._bayesianNetwork.get(i).getCPT(), e);
                f.removeIrrelevantRows(); // remove the irrelevant rows from a factor
                f.removeEvidances(); // remove the evidences from a factor
                BN._bayesianNetwork.get(i).setFactor(f); // add the factor we build to the relevant node
                this.factors.add(f); // then finally add the factor to the list of the factors
            }
        }
    }

    /*
        this function returns an arraylist of the irrelevant nodes
        a node is irrelevant if
        1. the node is independent the query node
        2. the node not an ancestor of the query node or the given nodes
     */
    private ArrayList<bayesianNode> findIrrelevantNodes() {
        ArrayList<bayesianNode> irrelevant = new ArrayList<bayesianNode>(); //the list the function returns

        ArrayList<bayesianNode> evi_ancestors = new ArrayList<bayesianNode>();
        ArrayList<bayesianNode> independents = new ArrayList<bayesianNode>();

        for (int i = 0; i < this.hidden.size(); i++) {
            // if the hidden node is independent the query - its irrelevant
            if (BayesBall.isInd(BN, this.query, this.hidden.get(i), this.evidence).equals("yes")) {
                irrelevant.add(this.hidden.get(i));
                continue;
            }
            // if it is dependent, check if it is an ancestor of the query or of one of the evidences
            boolean q_ancestor = isAncestor(hidden.get(i), this.query);

            for (int j = 0; j < this.evidence.size(); j++) {
                if (isAncestor(hidden.get(i), this.evidence.get(j))) {
                    evi_ancestors.add(this.hidden.get(i));
                }
            }
            if (!q_ancestor && !evi_ancestors.contains(this.hidden.get(i))) {
                irrelevant.add(this.hidden.get(i));
            }
        }
        return irrelevant;
    }

    // this function checks if a hidden node is an ancestor of a given node
    private boolean isAncestor(bayesianNode hidden, bayesianNode query) {
        for(int i = 0; i < query.getParents().size(); i++){
            if(hidden.getName().equals(query.getParents().get(i).getName())) return true;
        }

        boolean flag = false;
        for (int i = 0; i < query.getParents().size(); i++) {
            flag = isAncestor(hidden, query.getParents().get(i));
            if (flag) return true;
        }
        return false;
    }

    // this function runs over the factors and removes the factors that have only one line
    public void removeOneSize(ArrayList<factor> f) {
        for (int i = 0; i < f.size(); i++) {
            if (f.get(i).factor.size() == 1) {
                f.remove(i);
                if (i > 0) i--;
            }
        }
    }

    // sort the factors by the implementations of compareTo in the factor class
    public void sort(ArrayList<factor> factors) {
        Collections.sort(factors);
    }

    // this function checks if the answer is one of the cells in the query factor
    public boolean answerInFactor(bayesianNode query) {
        // if the hidden node is in the parent list
        for (int i = 0; i < this.hidden.size(); i++) {
            if (query.getParents().contains(hidden.get(i))) {
                return false; // we need to eliminate it - then the query's factor don't hold the answer
            }
        }
        // if the evidence is NOT on the query parents list
        for (int i = 0; i < this.evidence.size(); i++) {
            if (!query.getParents().contains(evidence.get(i))) {
                return false; // we need to change the query's factor
            }
        }
        return true; // else the answer is already in the query's factor
    }

    // this function join two factors to one, it joins the lines by the given hidden node
    public factor join(factor a, factor b) {
        ArrayList<String> commonVars = getCommonVars(a, b); // first we will get the common variables in the two given factors
        factor res = new factor();
        HashMap<String, String> new_row;
        // run over a_factor
        for (int i = 0; i < a.factor.size(); i++) {
            // get the common variable and put them in a hash table
            Hashtable a_row_common_vars = new Hashtable();
            for (String name : commonVars) {
                a_row_common_vars.put(name, a.factor.get(i).get(name));
            }
            // then run over b_factor
            for (int j = 0; j < b.factor.size(); j++) {
                boolean flag = true;
                for (String key : commonVars) {
                    // if the key in b is NOT in the common variables list - the flag will be false, and we will stop this iteration
                    if (!b.factor.get(j).get(key).equals(a_row_common_vars.get(key))) {
                        flag = false;
                        break;
                    }
                }
                // else if the flag is true the key is in the common values
                if (flag) {
                    new_row = get_new_row(a.factor.get(i), b.factor.get(j), commonVars); // we multiply the two rows (help function)
                    res.factor.add(new_row); // then add it to the result factor
                }
            }
        }
        return res;
    }

    /*
        this function actually make the multiply action for the join function
        it looks for the common variables and by them make the multiply action
     */
    private HashMap<String, String> get_new_row(HashMap<String, String> a_row, HashMap<String, String> b_row, ArrayList<String> commonVars) {
        HashMap<String, String> row = new HashMap<>();
        // add all of a_factor rows to the result factor
        for (String key : a_row.keySet()) {
            row.put(key, a_row.get(key));
        }
        // then add all of b_factor rows that are not common to the a_factor rows (prevent multiplication)
        for (String key : b_row.keySet()) {
            if (!commonVars.contains(key))
                row.put(key, b_row.get(key));
        }

        double p = Double.parseDouble(a_row.get("P")) * Double.parseDouble(b_row.get("P"));
        this.multiply++; // increase the counter of the multiplication
        row.put("P", String.valueOf(p)); // create the row that contains the result of the multiplication
        return row;
    }
    // this function compare between two factors and returns the common variables in them
    private ArrayList<String> getCommonVars(factor a, factor b) {
        ArrayList<String> common = new ArrayList<String>();
        for (String key : a.factor.get(0).keySet()) {
            if (b.factor.get(0).containsKey(key) && !key.equals("P")) {
                common.add(key);
            }
        }
        return common;
    }

    // this function eliminate the hidden node from a given factor
    public factor eliminate(factor a, bayesianNode hid) {
        double p = 0;
        // run over the rows of the factor
        for (int i = 0; i < a.factor.size(); i++) {
            // remove the hidden column for it is not relevant anymore
            a.factor.get(i).remove(hid.getName());
        }
        for (int i = 0; i < a.factor.size(); i++) {
            Object[] i_iter = a.factor.get(i).values().toArray();
            // look for an equal row
            for (int j = i + 1; j < a.factor.size(); j++) {
                Object[] j_iter = a.factor.get(j).values().toArray();
                // if two rows are equal (not "P") then add the probabilities - then remove one of them
                HashMap<String, String> row1 = a.factor.get(i);
                HashMap<String, String> row2 = a.factor.get(j);
                if (isEqual(row1, i_iter, row2, j_iter)) {
                    p = Double.parseDouble(a.factor.get(i).get("P")) + Double.parseDouble(a.factor.get(j).get("P"));
                    this.add++; // increase the counter of the adaption
                    a.factor.get(i).put("P", String.valueOf(p));
                    a.factor.remove(j--);
                }
            }
        }
        return a;
    }
    //this function is a help function of the eliminate function, it compares two given rows
    private boolean isEqual(HashMap<String, String> row1, Object[] i_iter, HashMap<String, String> row2, Object[] j_iter) {
        boolean res = true; // result is true
        int i = 0;
        // run over row1
        for (String key1 : row1.keySet()) {
            Object i_val = i_iter[i];
            if (key1.equals("P")) {
                i++;
                continue;
            }
            int j = 0;
            // run over row2
            for (String key2 : row2.keySet()) {
                Object j_val = j_iter[j];
                if (key2.equals("P")) {
                    j++;
                    continue;
                }
                // their key and values are NOT equal the result will be false
                if (key1.equals(key2) && !i_val.equals(j_val)) {
                    res = false;
                }
                j++;
            }
            i++;
        }
        // if it passed all the keys and didn't change the result will be true
        return res;
    }

    //this function normalize the values in a given factor
    public factor normalize(factor a) {
        double p = 0;
        // sum all values
        for (int i = 0; i < a.factor.size(); i++) {
            for (String key : a.factor.get(i).keySet()) {
                if (key.equals("P")) {
                    p += Double.parseDouble(a.factor.get(i).get("P"));
                    this.add++; // for each sum action increase the adaption counter
                }
            }
        }
        // then divide all values by that summation (to get the final sum to be equal 1)
        for (int i = 0; i < a.factor.size(); i++) {
            for (String key : a.factor.get(i).keySet()) {
                if (key.equals("P")) {
                    double prob = Double.parseDouble(a.factor.get(i).get("P")) / p;
                    NumberFormat formatter = new DecimalFormat("#0.00000");
                    String ans1 = formatter.format(prob);
                    a.factor.get(i).put("P", String.valueOf(ans1));
                }
            }
        }
        this.add--; // we counted one more for the last iteration (1+2+3 - will be 2 add action)
        return a;
    }

    /**
        In this function we will implement the variable elimination algorithm.
        first we will check if the factor already have the answer in it - if so we will return the answer
        else, we will iterate over the hidden nodes and start the elimination, step by step
        the first step will be to search the factors that contains the hidden node
        then join the factors that have the hidden node in them, we will do it by the size of the factors
        and until there is only one factor that contains the hidden node
        then, we will eliminate the node from that factor
        after we eliminate the hidden node we will join the query
        and normalize the values in the factor
        then finally we will return the value of the query from the final factor
     */
    public String variableElimination() {
        // check if the query factor already have the answer in it
        String[] q = this.str_query.split("=");
        if (answerInFactor(this.query)) {
            for (int i = 0; i < this.query.getFactor().factor.size(); i++) {
                for (String key : this.query.getFactor().factor.get(i).keySet()) {
                    if (key.equals(q[0]) && this.query.getFactor().factor.get(i).get(key).equals(q[1])) {
                        // if the answer is in return the value
                        return this.query.getFactor().factor.get(i).get("P");
                    }
                }
            }
        }
        // else, if the answer is not in the query factor:
        ArrayList<factor> hidFactors = new ArrayList<factor>();
        // iterate over the hidden nodes
        for (int i = 0; i < this.hidden.size(); i++) {
            // search the factors that contains the hidden node
            hidFactors = new ArrayList<factor>();
            for (int j = 0; j < this.factors.size(); j++) {
                for (int k = 0; k < this.factors.get(j).factor.size(); k++) {
                    for (String key : this.factors.get(j).factor.get(k).keySet()) {
                        // if the factor contains the hidden node
                        if (key.equals(this.hidden.get(i).getName())) {
                            // add this factor to the array list
                            if (!hidFactors.contains(this.factors.get(j))) {
                                hidFactors.add(this.factors.get(j));
                            }
                        }
                    }
                }
            }
            // sort them by their size
            sort(hidFactors);
            // join the factors that have the hidden node in them
            int k = 0;
            while (hidFactors.size() > 1) {
                factor join = join(hidFactors.get(k), hidFactors.get(k + 1));
                for (int t = 0; t < this.factors.size(); t++) {
                    if (this.factors.get(t).equals(hidFactors.get(k))) {
                        this.factors.remove(t--); // remove k from factor list
                    }
                }
                hidFactors.remove(hidFactors.get(k)); // remove k
                for (int t = 0; t < this.factors.size(); t++) {
                    if (this.factors.get(t).equals(hidFactors.get(k))) {
                        this.factors.remove(t--); // remove k+1 from factor list
                    }
                }
                hidFactors.remove(hidFactors.get(k)); // remove k+1

                this.factors.add(join);
                hidFactors.add(join);

                removeOneSize(this.factors);
                removeOneSize(hidFactors);

                sort(this.factors);
                sort(hidFactors);
            }
            // then, we will eliminate the node from that factor
            if (hidFactors.size() != 0) {
                factor elim = eliminate(hidFactors.get(0), this.hidden.get(i));
                for (int t = 0; t < this.factors.size(); t++) {
                    if (this.factors.get(t).equals(hidFactors.get(0))) {
                        this.factors.remove(t--); // remove from factor list
                    }
                }
                hidFactors.remove(0);
                this.factors.add(elim);
                hidFactors.add(elim);

                removeOneSize(this.factors);
                removeOneSize(hidFactors);

                sort(this.factors);
                sort(hidFactors);
            }
        }
        //join the query
        factor res = new factor();
        int k = 0;
        while (this.factors.size() > 1) {
            res = join(this.factors.get(k), this.factors.get(k + 1));
            this.factors.remove(this.factors.get(k));
            this.factors.remove(this.factors.get(k));
            this.factors.add(res);

            removeOneSize(this.factors);
            sort(this.factors);
        }
        // only then normalize
        res = normalize(this.factors.get(0));
        String answer = "";
        for (int i = 0; i < res.factor.size(); i++) {
            for (String key : res.factor.get(i).keySet()) {
                if (key.equals(q[0]) && res.factor.get(i).get(key).equals(q[1])) {
                    // if the answer is in return the value
                    answer = res.factor.get(i).get("P");
                }
            }
        }
        answer += "," + this.add + "," + this.multiply; // add the counters to the final answer
        return answer;
    }

}
