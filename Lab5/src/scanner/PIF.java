package scanner;


import java.util.ArrayList;

public class PIF {
    ArrayList<Pair> elements;

    public PIF() {
        this.elements = new ArrayList<>();
    }

    public void add(Pair pair){
        elements.add(pair);
    }

    @Override
    public String toString() {
        String str = "PIF: \n";
        for(Pair pair: elements){
            str = str.concat(pair.toString());
            str = str.concat("\n");
        }
        return str;
    }
}
