import java.util.ArrayList;
import java.util.HashMap;

public class BayesBall {
    private static ArrayList<bayesianNode> passed = new ArrayList<>();
//    private static HashMap<String,bayesianNode> passed = new HashMap<String,bayesianNode>();

    public static String isInd(bayesianNetwork bn, bayesianNode src, bayesianNode dest, ArrayList<bayesianNode> evidence) {
        passed = new ArrayList<>();
        if (bayesBall(bn, src, dest, evidence, null, passed)) {
            return "yes"; //independent
        } else {
            return "no"; //dependent
        }
    }

    //bayes ball: dependent return false, independent return true
    private static boolean bayesBall(bayesianNetwork bn, bayesianNode src, bayesianNode dest, ArrayList<bayesianNode> evidence, bayesianNode last, ArrayList<bayesianNode> passed) {
        if (bn.returnByName(src.getName()).equals(bn.returnByName(dest.getName()))) return false;
        //given
        if (evidence.contains(src)) {
            //came from child
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
                        passed.add(src); //true
                        if (!bayesBall(bn, src.getParents().get(i), dest, evidence, src, passed))
                            return false;
                    }
                }
                return true;
            }
        }
        //not given
        else {
            //came from parent
            if (src.getParents().contains(last)) {
                for (int i = 0; i < src.getChildren().size(); i++) {
                    if (!passed.contains(src.getChildren().get(i))) {
//                        passed.add(src);
                        if (!bayesBall(bn, src.getChildren().get(i), dest, evidence, src, passed))
                            return false;
                    }

                }
                return true;
            }
            //came from child
            else {
                for (int i = 0; i < src.getParents().size(); i++) {
                    // Only if this is the first time we get to this node - go in, else move on
                    if (!passed.contains(src.getParents().get(i))) {
                        passed.add(src); //true
                        if (!bayesBall(bn, src.getParents().get(i), dest, evidence, src, passed))
                            return false;
                    }
                }
                for (int i = 0; i < src.getChildren().size(); i++) {
                    if (!passed.contains(src.getChildren().get(i))) {
                        passed.add(src); //true
                        if (!bayesBall(bn, src.getChildren().get(i), dest, evidence, src, passed))
                            return false;
                    }
                }
                return true;

            }

        }

    }

}
