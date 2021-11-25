import java.util.ArrayList;
import java.util.HashMap;

public class variableElimination {
    bayesianNode query;
    ArrayList<bayesianNode> hidden = new ArrayList<bayesianNode>();
    ArrayList<bayesianNode> evidence = new ArrayList<bayesianNode>();
    bayesianNetwork BN;
    ArrayList<factor> factors = new ArrayList<factor>();
    int multiply = 0;
    int add = 0;

    public variableElimination(String query, String[] hidden, String[] evidence, bayesianNetwork BN){
        String[] q = query.split("=");
        this.query = BN.returnByName(q[0]);
        for(int i = 0; i < hidden.length; i++){
            this.hidden.add(BN.returnByName(hidden[i]));
        }
        for(int i = 0; i < evidence.length; i++){
            String[] e = evidence[i].split("=");
            this.evidence.add(BN.returnByName(e[0]));
        }
        this.BN = BN;
        //init factors:
        generateFactors();
        System.out.println(factors);
    }

    // this function generates the relevant factor for the given question
    private void generateFactors(){
        ArrayList<bayesianNode> irrelevant = findIrrelevantNodes();
        for(int i = 0; i < BN._bayesianNetwork.size(); i++){
            if(!irrelevant.contains(BN._bayesianNetwork.get(i))){
                String[] e = new String[this.evidence.size()];
                for(int j = 0; j < this.evidence.size(); j++){
                    e[j] = this.evidence.get(j).getName();
                }
                factor f = new factor(BN._bayesianNetwork.get(i).getName(), BN._bayesianNetwork.get(i).getCPT(), e);
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
    private ArrayList<bayesianNode> findIrrelevantNodes(){
        ArrayList<bayesianNode> irrelevant = new ArrayList<bayesianNode>(); //the list the function returns

        ArrayList<bayesianNode> evi_ancestors = new ArrayList<bayesianNode>();
        ArrayList<bayesianNode> independents = new ArrayList<bayesianNode>();

        for(int i =0 ; i < this.hidden.size(); i++){
            boolean q_ancestor = isAncestor(hidden.get(i), this.query);
            for(int j = 0; j < this.evidence.size(); j++){
                if(isAncestor(hidden.get(i), this.evidence.get(j))){
                    evi_ancestors.add(this.evidence.get(j));
                }
            }
            if(BayesBall.isInd(BN, this.query, this.hidden.get(i), this.evidence).equals("yes")){
                independents.add(this.hidden.get(i));
            }
            if(!q_ancestor && !evi_ancestors.contains(this.hidden.get(i)) && independents.contains(this.hidden.get(i))){
                irrelevant.add(this.hidden.get(i));
            }
        }

        return irrelevant;
    }

    // this function checks if a hidden node is an ancestor of a given node
    private boolean isAncestor(bayesianNode hidden, bayesianNode query) {
        if(query.getParents().contains(hidden)) return true;
        boolean flag = false;
        for(int i = 0; i < query.getParents().size(); i++){
            flag = isAncestor(hidden, query.getParents().get(i));
            if(flag) return true;
        }
        return false;
    }


}
