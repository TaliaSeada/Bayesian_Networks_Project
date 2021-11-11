import java.util.ArrayList;
import java.util.Stack;

public class BayesBall {
    /**
     * @param evidence - givens
     * @return
     */

    public static String isInd(bayesianNetwork bn, bayesianNode src, bayesianNode dest, ArrayList<bayesianNode> evidence) {
//        System.out.println(evidence);
        if (bayesBall(bn, src, dest, evidence,null)) {
            return "yes"; //independent
        } else {
            return "no"; //dependent
        }
    }
    //bayes ball: dependent return false, independent return true
    private static boolean bayesBall(bayesianNetwork bn, bayesianNode src, bayesianNode dest, ArrayList<bayesianNode> evidence, bayesianNode last) {
        if (src.equals(dest)) return false;
        //given
        if(evidence.contains(src)){
            //came from child
            if(src.getChildren().contains(last)){
                return true;
            }
            //came from parent
            else{
                for(int i=0;i<src.getParents().size();i++) {
                    if(!bayesBall(bn,src.getParents().get(i),dest,evidence,src))
                        return false;
                }
                return true;
            }
        }
        //not given
        else{
            if(src.getParents().contains(last)){
                for(int i=0;i<src.getChildren().size();i++) {
                    if(src.getChildren().get(i) != last && !bayesBall(bn,src.getChildren().get(i),dest,evidence,src))
                        return false;
                }
                return true;
            }
            else{
                for(int i=0;i<src.getParents().size();i++) {
                    if(src.getParents().get(i) != last && !bayesBall(bn,src.getParents().get(i),dest,evidence,src))
                        return false;
                }
                for(int i=0;i<src.getChildren().size();i++) {
                    if(src.getChildren().get(i) != last && !bayesBall(bn,src.getChildren().get(i),dest,evidence,src))
                        return false;
                }
                return true;

            }
        }
    }

}
