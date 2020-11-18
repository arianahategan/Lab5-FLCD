package grammar;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Grammar {
    private ArrayList<String> nonTerminals;
    private ArrayList<String> terminals;
    private HashMap<String, ArrayList<ArrayList<String>>> productions;
    private String startingSymbol;

    public Grammar(String fileName) {
        this.nonTerminals = new ArrayList<>();
        this.terminals = new ArrayList<>();
        this.productions = new HashMap<>();
        this.startingSymbol = "";
        readFromFile(fileName);
    }

    @Override
    public String toString() {
        return "Grammar{" +
                "nonTerminals=" + nonTerminals +
                ", terminals=" + terminals +
                ", productions=" + productions +
                ", startingSymbol='" + startingSymbol + '\'' +
                '}';
    }

    private void readFromFile(String fileName){
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(fileName));
            String line = reader.readLine();
            while(line != null){
                String set = line.split("=")[0];
                String[] util = line.substring(line.indexOf("{") + 1, line.indexOf("}")).split(",");
                switch (set){
                    case "N":
                        this.nonTerminals = new ArrayList<>(Arrays.asList(util));
                        break;
                    case "E":
                        this.terminals = new ArrayList<>(Arrays.asList(util));
                        break;
                    case "P":
                        for(String production: util) {
                            String[] aux = production.split("->");
                            String starting = aux[0];
                            String[] ending = aux[1].split("\\|");
                            ArrayList<ArrayList<String>> elements = new ArrayList<>();
                            for(String elem: ending){
                                String[] symbols = elem.split(" ");
                                elements.add(new ArrayList<>(Arrays.asList(symbols)));
                            }
                            this.productions.put(starting, elements);
                        }
                        break;
                    case "S":
                        this.startingSymbol = util[0];
                        break;

                }
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public ArrayList<String> getNonTerminals() {
        return nonTerminals;
    }

    public void setNonTerminals(ArrayList<String> nonTerminals) {
        this.nonTerminals = nonTerminals;
    }

    public ArrayList<String> getTerminals() {
        return terminals;
    }

    public void setTerminals(ArrayList<String> terminals) {
        this.terminals = terminals;
    }

    public HashMap<String, ArrayList<ArrayList<String>>> getProductions() {
        return productions;
    }

    public void setProductions(HashMap<String, ArrayList<ArrayList<String>>> productions) {
        this.productions = productions;
    }

    public String getStartingSymbol() {
        return startingSymbol;
    }

    public void setStartingSymbol(String startingSymbol) {
        this.startingSymbol = startingSymbol;
    }
}
