import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Set;

public class factor {
    String name;
    ArrayList<HashMap<String, String>> factor;
    ArrayList<String> evidence;

    // new factor to build
    public factor(String name){
        this.name = name;
        this.factor = new ArrayList<HashMap<String, String>>();
        this.evidence = new ArrayList<>(evidence);
    }

    // initial factor
    public factor(String name, ArrayList<HashMap<String, String>> cpt, String[] evidence){
        this.name = name;
        for(int i = 0; i < cpt.size(); i++){
            this.factor.add(hashMapCopy(cpt.get(i)));
        }
        for(int i = 0; i < evidence.length; i++){
            this.evidence.add(evidence[i]);
        }
    }
    private HashMap hashMapCopy(HashMap line){
        HashMap res = new HashMap();
        for(int i = 0; i < line.size(); i++){
            res = (HashMap) line.clone();
        }
        return res;
    }

    //this function returns the ascii value size of a factor (in order to compare it later)
    public int lexiSize(){
        int ascii = 0;
        for(String key : this.factor.get(0).keySet()){
            for(int i = 0; i < key.length(); i++){
                ascii += key.charAt(i);
            }
        }
        return ascii;
    }

    // this function removes the irrelevant rows out of the factor
    public void removeIrrelevantRows(ArrayList<String> outcomes){

    }




}
