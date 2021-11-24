import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Set;

public class factor {
    String name;
    ArrayList<HashMap<String, String>> factor = new ArrayList<HashMap<String, String>>();
    ArrayList<String> evidence = new ArrayList<>();

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

    // copy
    public factor(factor other){
        this.name = other.name;
        for(int i = 0; i < other.factor.size(); i++){
            this.factor.add(hashMapCopy(other.factor.get(i)));
        }
        for(int i = 0; i < other.evidence.size(); i++){
            this.evidence.add(evidence.get(i));
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
    public void removeIrrelevantRows(){
        factor res = new factor(this.name);
        HashMap<String, String> ln = new HashMap<String, String>();
        // iterate over the evidence list and check if the cpt contains this evidence, if so add it to the factor
        for(int i = 0; i < this.evidence.size(); i++){
            String[] evi = evidence.get(i).split("=");
            for(int j = 0; j < this.factor.size(); j++){
                for(String key : this.factor.get(j).keySet()){
                    int val = 0;
                    if(key.equals(evi[0]) && this.factor.get(j).values().toArray()[val].equals(evi[1])){
                        ln.put(key, evi[1]);
                        res.factor.add(ln);
                    }
                    val++;
                }
            }

        }
        this.factor = res.factor;
    }




}
