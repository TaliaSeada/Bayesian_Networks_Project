import java.util.ArrayList;
import java.util.Stack;

public class BayesBall {

    /**
     * @param parent   1 - parent, 0 - child
     * @param evidence - givens
     * @return
     */

    public static String isInd(bayesianNetwork bn, bayesianNode src, String parent, bayesianNode dest, ArrayList<bayesianNode> evidence) {
//        System.out.println(evidence);
        if (bayesBall(bn, src, parent, dest, evidence)) {
            return "yes"; //independent
        } else {
            return "no"; //dependent
        }
    }

    //bayes ball
    private static boolean bayesBall(bayesianNetwork bn, bayesianNode src, String parent, bayesianNode dest, ArrayList<bayesianNode> evidence) {
        if (src.equals(dest)) return false;
        if (parent != null) {
            //src is given and came from parent
            if (evidence.contains(src) && parent == "1") {
                //can only go to other parents but not to the children
                for (bayesianNode p : src.getParents()) {
                    if (!bayesBall(bn, p, "0", dest, evidence))
                        System.out.println("e p");
                    return true;
                }
            }
            //src is given and came from child
            if (evidence.contains(src) && parent == "0") {
                //can't go anywhere
                System.out.println("e c");
                return true;
            }
            //src came from parent
            if (parent == "1") {
                //can only go to children
                for (bayesianNode c : src.getChildren()) {
                    if (!bayesBall(bn, c, "1", dest, evidence))
                        System.out.println("p");
                    return true;
                }
            }
            //src came from child
            if (parent == "0") {
                //can go everywhere
                for (bayesianNode p : src.getParents()) {
                    if (!bayesBall(bn, p, "0", dest, evidence))
                        System.out.println("p c");
                    return true;
                }
                for (bayesianNode c : src.getChildren()) {
                    if (!bayesBall(bn, c, "1", dest, evidence))
                        System.out.println("c c");
                    return true;
                }
            }

            System.out.println("pass all");
            return false;

        }

        else { //if we at the beginning go to all the neighbors
            for (bayesianNode p : src.getParents()) {
                if (!bayesBall(bn, p, "0", dest, evidence))
                    System.out.println("start p");
                return true;
            }
            for (bayesianNode c : src.getChildren()) {
                if (!bayesBall(bn, c, "1", dest, evidence))
                    System.out.println("start c");
                return true;
            }
            System.out.println("start pass all");
            return false;
        }
    }


}
