import java.util.*;

public class variableElimination {
    bayesianNode query;
    ArrayList<bayesianNode> hidden = new ArrayList<bayesianNode>();
    ArrayList<bayesianNode> evidence = new ArrayList<bayesianNode>();
    ArrayList<String> evi = new ArrayList<String>();
    bayesianNetwork BN;
    ArrayList<factor> factors = new ArrayList<factor>();
    int multiply = 0;
    int add = 0;

    public variableElimination(String query, String[] hidden, String[] evidence, bayesianNetwork BN) {
        String[] q = query.split("=");
        this.query = BN.returnByName(q[0]);
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
        generateFactors();
        removeOneSize();
        sort(this.factors);
//        System.out.println(factors);
    }

    // this function generates the relevant factor for the given question
    private void generateFactors() {
        ArrayList<bayesianNode> irrelevant = findIrrelevantNodes();
        // remove the factors that the irrelevant nodes are in
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
                f.removeIrrelevantRows();
                this.factors.add(f);
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
            boolean q_ancestor = isAncestor(hidden.get(i), this.query);
            for (int j = 0; j < this.evidence.size(); j++) {
                if (isAncestor(hidden.get(i), this.evidence.get(j))) {
                    evi_ancestors.add(this.evidence.get(j));
                }
            }
            if (BayesBall.isInd(BN, this.query, this.hidden.get(i), this.evidence).equals("yes")) {
                independents.add(this.hidden.get(i));
            }
            if (!q_ancestor && !evi_ancestors.contains(this.hidden.get(i)) && independents.contains(this.hidden.get(i))) {
                irrelevant.add(this.hidden.get(i));
            }
        }

        return irrelevant;
    }

    // this function checks if a hidden node is an ancestor of a given node
    private boolean isAncestor(bayesianNode hidden, bayesianNode query) {
        if (query.getParents().contains(hidden)) return true;
        boolean flag = false;
        for (int i = 0; i < query.getParents().size(); i++) {
            flag = isAncestor(hidden, query.getParents().get(i));
            if (flag) return true;
        }
        return false;
    }

    // this function runs over the factors and removes the factors that have only one line
    public void removeOneSize() {
        for (int i = 0; i < this.factors.size(); i++) {
            if (this.factors.get(i).factor.size() < 2) {
                this.factors.remove(i);
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
        for (int i = 0; i < this.hidden.size(); i++) {
            if (query.getParents().contains(hidden.get(i))) {
                return false;
            }
        }
        for (int i = 0; i < this.evidence.size(); i++) {
            if (!query.getParents().contains(evidence.get(i))) {
                return false;
            }
        }
        return true;
    }

    // this function join two factors to one, it joins the lines by the given hidden node
    public factor join(factor a, factor b, bayesianNode hid) {
        // the bigger should be first, so we won't miss lines
        if (a.factor.size() < b.factor.size()) {
            factor tmp = a;
            a = b;
            b = tmp;
        }

        factor res = new factor();
        HashMap<String, String> ln = new HashMap<String, String>();
        double p = 0;
        for (int i = 0; i < a.factor.size(); i++) {
            Iterator<String> iter_a = a.factor.get(i).values().iterator();
            for (String a_key : a.factor.get(i).keySet()) {
                String a_val = iter_a.next();
                if (!a_key.equals("P")) {
                    for (int j = 0; j < b.factor.size(); j++) {
                        Iterator<String> iter_b = b.factor.get(j).values().iterator();
                        for (String b_key : b.factor.get(j).keySet()) {
                            String b_val = iter_b.next();
                            if (!b_key.equals("P")) {
                                if (a_key.equals(b_key) && a_val.equals(b_val) && a_key.equals(hid.getName())) {
                                    double a_p = Double.parseDouble(a.factor.get(i).get("P"));
                                    double b_p = Double.parseDouble(b.factor.get(j).get("P"));
                                    p = a_p * b_p;
                                    if (!ln.containsKey("P")) {
                                        ln.put("P", String.valueOf(p));
                                    }
                                    ln.put(a_key, a_val);
                                }
                                if (!ln.containsKey(b_key)) {
                                    ln.put(b_key, b_val);
                                }
                                if (!ln.containsKey(a_key)) {
                                    ln.put(a_key, a_val);
                                }

                            }

                        }

                    }
                }

            }
            res.factor.add(ln);
            ln = new HashMap<String, String>();
        }
        res.evidence = a.evidence;
        return res;
    }

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
//                    System.out.println(p);
                    a.factor.get(i).put("P", String.valueOf(p));
                    a.factor.remove(j--);
                }
            }
        }
        return a;
    }

    private boolean isEqual(HashMap<String, String> row1, Object[] i_iter, HashMap<String, String> row2, Object[] j_iter) {
        boolean res = true;
        int i = 0;
        for (String key1 : row1.keySet()) {
            Object i_val = i_iter[i];
            if (key1.equals("P")) {
                i++;
                continue;
            }
            int j = 0;
            for (String key2 : row2.keySet()) {
                Object j_val = j_iter[j];
                if (key2.equals("P")) {
                    j++;
                    continue;
                }
                if (key1.equals(key2) && !i_val.equals(j_val)) {
                    res = false;
                }
                j++;
            }
            i++;
        }
        return res;
    }

    public factor normalize(factor a) {
        double p = 0;
        for (int i = 0; i < a.factor.size(); i++) {
            for (String key : a.factor.get(i).keySet()) {
                if (key.equals("P")) {
                    p += Double.parseDouble(a.factor.get(i).get("P"));
                }
            }
        }
        for (int i = 0; i < a.factor.size(); i++) {
            for (String key : a.factor.get(i).keySet()) {
                if (key.equals("P")) {
                    a.factor.get(i).put("P", String.valueOf(Double.parseDouble(a.factor.get(i).get("P")) / p));
                }
            }
        }
        return a;
    }

    /*
        In this function we will implement the variable elimination algorithm
        first we will check if the factor already have the answer in it - if so we will return the answer
        else, we will iterate over the hidden nodes and start the elimination, step by step
        the first step will be to search the factors that contains the hidden node
        then join the factors that have the hidden node in them, we will do it by the size of the factors
        and until there is only one factor that contains the hidden node
        then, we will eliminate the node from that factor
        after we eliminate the hidden node we will normalize the values in the factor
        then finally we will return the value of the query
     */
    public String variableElimination() {
        // check if the factor already have the answer in it
//        if(answerInFactor(this.query)){
//
//        }
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
            System.out.println(hidFactors);
            // join the factors that have the hidden node in them
            int k = 0;
            while(hidFactors.size() > 1){
                factor join = join(hidFactors.get(k), hidFactors.get(k+1), this.hidden.get(i));
                hidFactors.remove(hidFactors.get(k)); // remove k
                hidFactors.remove(hidFactors.get(k)); // remove k+1
                hidFactors.add(join);
                sort(hidFactors);
                this.multiply++;
            }
            // then, we will eliminate the node from that factor
            eliminate(hidFactors.get(0), this.hidden.get(i));
            this.add++;
            System.out.println(hidFactors);

        }


        return "";
    }

}
