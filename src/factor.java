import java.util.*;

public class factor  implements Comparable<factor>{
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
    public void removeIrrelevantRows() {
        // iterate over the columns and check every line, and take only the ones that are givens
        ArrayList<String> evi = new ArrayList<>();
        for(int i = 0; i < this.evidence.size(); i++){
            String[] e = this.evidence.get(i).split("=");
            for(int j = 0; j < e.length; j++){
                evi.add(e[j]);
            }
        }
        for(int i = 0; i < this.factor.size(); i++) {
            int val = 0;
            for (String key : this.factor.get(i).keySet()) {
                if(!key.equals("P")) {
                    for (int j = 0; j < evi.size()-1; j++) {
                        if (evi.get(j).equals(key) && !evi.get(j+1).equals(this.factor.get(i).values().toArray()[val])) {
                            this.factor.remove(i);
                            if(i > 0) i--;
                        }
                    }
                }
                val++;
            }
        }
    }

    /*
        this function compare between two factors
        first by the size of their CPT's
        then if they are equal it compares between their ascii value
    */
    @Override
    public int compareTo(factor f) {
        int flag = 0;
        if(this.factor.size() > f.factor.size()){
            flag = 1;
        }
        else if(this.factor.size() < f.factor.size()) {
            flag = -1;
        }
        else {
            if(this.lexiSize() > f.lexiSize()){
                flag = 1;
            }
            else if(this.lexiSize() < f.lexiSize()){
                flag = -1;
            }
            else {
                flag = 0;
            }
        }
        return flag;
    }

    public String toString() {
        return "name='" + name + '\'' +
                ", factor=" + factor +
                ", evidence=" + evidence + "\n";
    }
}
