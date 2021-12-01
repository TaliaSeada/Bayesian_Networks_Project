import java.util.ArrayList;

/**
 * this class represents the Bayes-Ball algorithm
 */
public class BayesBall {
    private static ArrayList<bayesianNode> passed = new ArrayList<>();

    /**
     * this function convert the true answer from the bayes ball function to "yes" (string)
     * and the false answer to "no" (string)
     */
    public static String isInd(bayesianNetwork bn, bayesianNode src, bayesianNode dest, ArrayList<bayesianNode> evidence) {
        passed = new ArrayList<>(); // for each case restart the passed list
        if (bayesBall(bn, src.getName(), dest.getName(), evidence, null, passed)) {
            return "yes"; //independent
        } else {
            return "no"; //dependent
        }
    }

    //bayes ball: dependent return false, independent return true

    /**
     * This function is the algorithm Bayes-Ball
     * we will split the algorithm to four cases:
     * 1. the current node is given and came from child
     * 2. the current node is given and came from parent
     * 3. the current node is NOT given and came from child
     * 4. the current node is NOT given and came from parent
     * for each case we will act according to the algorithm
     * @param bn - the bayesian-network
     * @param StrSrc - source node, current
     * @param StrDest - destination node, the node we need to "reach"
     * @param evidence - if there are eni givens nodes, they can affect the result
     * @param StrLast - the last node we have been
     * @param passed - a list of nodes we already passed and can't go back to them
     * @return - if the two nodes from a given network are independent
     */
    private static boolean bayesBall(bayesianNetwork bn, String StrSrc, String StrDest, ArrayList<bayesianNode> evidence, String StrLast, ArrayList<bayesianNode> passed) {
        bayesianNode src = bn.returnByName(StrSrc);
        bayesianNode dest = bn.returnByName(StrDest);
        bayesianNode last = bn.returnByName(StrLast);
        if (bn.returnByName(src.getName()).equals(bn.returnByName(dest.getName()))) return false;
        //given
        if (evidence.contains(src)) {
            // came from child
            if (src.getChildren().contains(last)) {
                return true;
            }
            //came from parent
            else {
                for (int i = 0; i < src.getParents().size(); i++) {
                    /*
                        In order to prevent endless loop between the nodes, we will keep the nodes we can't
                        come back to in a memory.
                        Only if this is the first time we get to this node - go in, else move on
                     */
                    if (!passed.contains(src.getParents().get(i))) {
                        passed.add(src);
                        if (!bayesBall(bn, src.getParents().get(i).getName(), dest.getName(), evidence, src.getName(), passed))
                            return false;
                    }
                }
                return true;
            }
        }
        //not given
        else {
            //came from child
            if (src.getChildren().contains(last) || last == (null)) {
                for (int i = 0; i < src.getParents().size(); i++) {
                    // Only if this is the first time we get to this node - go in, else move on
                    if (!passed.contains(src.getParents().get(i))) {
                        passed.add(src);
                        if (!bayesBall(bn, src.getParents().get(i).getName(), dest.getName(), evidence, src.getName(), passed))
                            return false;
                    }
                }
                for (int i = 0; i < src.getChildren().size(); i++) {
                    if (!passed.contains(src.getChildren().get(i))) {
                        passed.add(src);
                        if (!bayesBall(bn, src.getChildren().get(i).getName(), dest.getName(), evidence, src.getName(), passed))
                            return false;
                    }
                }
                return true;
            }
            //came from parent
            else {
                for (int i = 0; i < src.getChildren().size(); i++) {
                    if (!passed.contains(src.getChildren().get(i))) {
                        if (!bayesBall(bn, src.getChildren().get(i).getName(), dest.getName(), evidence, src.getName(), passed))
                            return false;
                    }

                }
                return true;
            }

        }

    }

}
