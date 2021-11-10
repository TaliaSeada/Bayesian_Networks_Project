import java.util.ArrayList;
import java.util.Stack;

public class BayesBall {

    /**
     * @param parent 1 - parent, 0 - child
     * @param evidence - givens
     * @return
     */
    public static String isInd(bayesianNetwork bn, bayesianNode src, String parent, bayesianNode dest, ArrayList<bayesianNode> evidence) {
        if (!bayesBall(bn, src,null, dest, evidence)) {
            return "yes"; //independent
        } else {
            return "no"; //dependent
        }
    }

    //DFS
    private static boolean bayesBall(bayesianNetwork bn, bayesianNode src, String parent, bayesianNode dest, ArrayList<bayesianNode> evidence) {
        if (src == dest) return true;

        if(parent != null) {
            //src is given and came from parent
            if (evidence.contains(src) && parent == "1") {
                //can only go to other parents but not to the children
                for(bayesianNode p : src.getParents()){
                    if(!bayesBall(bn, p, "0", dest, evidence))
                        return false;
                }
            }
            //src is given and came from child
            else if (evidence.contains(src) && parent == "0") {
                //can't go anywhere
                return false;
            }
            //src came from parent
            else if (parent == "1") {
                //can only go to children
                for(bayesianNode c : src.getChildren()){
                    if(!bayesBall(bn, c, "1", dest, evidence))
                        return false;
                }
            }
            //src came from child
            else if (parent == "0") {
                //can go everywhere
                for(bayesianNode p : src.getParents()){
                    if(!bayesBall(bn, p, "0", dest, evidence))
                        return false;
                }
                for(bayesianNode c : src.getChildren()){
                    if(!bayesBall(bn, c, "1", dest, evidence))
                        return false;
                }
            }
            return true;
        }

        else{ //if we at the beginning go to all the neighbors
            for(bayesianNode p : src.getParents()){
                if(!bayesBall(bn, p, "0", dest, evidence))
                    return false;
            }
            for(bayesianNode c : src.getChildren()){
                if(!bayesBall(bn, c, "1", dest, evidence))
                    return false;
            }
            return true;
        }
    }



}
