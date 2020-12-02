package parser;

import grammar.Grammar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Stack;


public class Parser {
    private final Grammar grammar;
    private HashMap<String, ArrayList<String>> first;
    private HashMap<String, ArrayList<String>> follow;
    private final HashMap<Pair, ParsingTableCell> parsingTable;
    Stack<String> inputStack = new Stack<>();
    Stack<String> workingStack = new Stack<>();
    Stack<String> output = new Stack<>();

    public Parser(Grammar grammar) {
        this.grammar = grammar;
        this.first = new HashMap<>();
        this.computeFirst();
        this.follow = new HashMap<>();
        this.computeFollow();
        this.parsingTable = new HashMap<>();
        this.computeParsingTable();
    }

    private void computeFirst() {
        ArrayList<HashMap<String, ArrayList<String>>> table = new ArrayList<>();

        //initialization
        HashMap<String, ArrayList<String>> currentColumn = new HashMap<>();
        for (String nonTerminal : this.grammar.getNonTerminals()) {
            ArrayList<ArrayList<String>> productionsForNonTerminal = this.grammar.getProductions().get(nonTerminal);
            ArrayList<String> var = new ArrayList<>();
            for (ArrayList<String> productionForNonTerminal : productionsForNonTerminal)
                if (grammar.getTerminals().contains(productionForNonTerminal.get(0)) || productionForNonTerminal.get(0).equals("epsilon")) {
                    var.add(productionForNonTerminal.get(0));
                }
            currentColumn.put(nonTerminal, this.toSet(var));
        }

        table.add(currentColumn);
        int index = 0;
        //end of initialization

        //F1
        {
            HashMap<String, ArrayList<String>> newColumn = new HashMap<>();
            for (String nonTerminal : this.grammar.getNonTerminals()) {
                ArrayList<ArrayList<String>> productionsForNonTerminal = this.grammar.getProductions().get(nonTerminal);
                ArrayList<String> rhsNonTerminals = new ArrayList<>();
                String rhsTerminals = null;
                for (ArrayList<String> productionForNonTerminal : productionsForNonTerminal) {
                    for (String symbol : productionForNonTerminal)
                        if (this.grammar.getNonTerminals().contains(symbol))
                            rhsNonTerminals.add(symbol);
                        else{
                            rhsTerminals = symbol;
                            break;
                        }
                }
                ArrayList<String> toAdd = new ArrayList<>(table.get(0).get(nonTerminal));
                toAdd.addAll(toSet(multipleConcatenation(table.get(0), rhsNonTerminals, rhsTerminals)));
                newColumn.put(nonTerminal, toAdd);
            }
            index++;
            table.add(newColumn);

        }
        //end of F1

        //the other columns
        while (!table.get(index).equals(table.get(index - 1))) {
            HashMap<String, ArrayList<String>> newColumn = new HashMap<>();
            for (String nonTerminal : this.grammar.getNonTerminals()) {

                ArrayList<ArrayList<String>> productionsForNonTerminal = this.grammar.getProductions().get(nonTerminal);
                ArrayList<String> rhsNonTerminals = new ArrayList<>();
                String rhsTerminals = null;
                for (ArrayList<String> productionForNonTerminal : productionsForNonTerminal) {
                    for (String symbol : productionForNonTerminal)
                        if (this.grammar.getNonTerminals().contains(symbol))
                            rhsNonTerminals.add(symbol);
                        else{
                            rhsTerminals = symbol;
                            break;
                        }

                }
                ArrayList<String> toAdd = new ArrayList<>(table.get(index).get(nonTerminal));
                toAdd.addAll(multipleConcatenation(table.get(index), rhsNonTerminals, rhsTerminals));
                newColumn.put(nonTerminal, toSet(toAdd));
            }
            index++;
            table.add(newColumn);


        }
        this.first = table.get(table.size() - 1);
    }

    private ArrayList<String> multipleConcatenation(HashMap<String, ArrayList<String>> previousColumn, ArrayList<String> rhsNonTerminals, String rhsTerminal) {
        ArrayList<String> concatenation = new ArrayList<>();
        if(rhsNonTerminals.size() == 0)
            return concatenation;
        if(rhsNonTerminals.size() == 1){
            return previousColumn.get(rhsNonTerminals.get(0));
        }
        int step = 0;
        boolean allEpsilon = true;
        for(String nonTerminal: rhsNonTerminals)
            if(!previousColumn.get(nonTerminal).contains("epsilon"))
                allEpsilon = false;
        if(allEpsilon){
            concatenation.add(Objects.requireNonNullElse(rhsTerminal, "epsilon"));
        }

        while(step < rhsNonTerminals.size()){
            boolean thereIsOneEpsilon = false;
            for(String s: previousColumn.get(rhsNonTerminals.get(step)))
                if(s.equals("epsilon"))
                    thereIsOneEpsilon = true;
                else
                    concatenation.add(s);

            if(thereIsOneEpsilon)
                step++;
            else
                break;
        }
        return concatenation;
    }

    private ArrayList<String> firstOfSequence(ArrayList<String> rhs){
        ArrayList<String> result = new ArrayList<>();
        ArrayList<String> rhsNonTerminals = new ArrayList<>();
        String terminal = null;
        if(this.grammar.getTerminals().contains(rhs.get(0))){
            result.add(rhs.get(0));
            return result;
        }
        else{
            for (String s : rhs) {
                if (this.grammar.getNonTerminals().contains(s))
                    rhsNonTerminals.add(s);
                else {
                    terminal = s;
                    break;
                }
            }
        }
        return multipleConcatenation(this.first, rhsNonTerminals, terminal);
    }

    private ArrayList<String> toSet(ArrayList<String> var) {
        ArrayList<String> set = new ArrayList<>();
        for (String s : var)
            if (!set.contains(s))
                set.add(s);
        return set;
    }

    private void computeFollow() {
        ArrayList<HashMap<String, ArrayList<String>>> table = new ArrayList<>();

        //initialization
        HashMap<String, ArrayList<String>> currentColumn = new HashMap<>();
        for (String nonTerminal : this.grammar.getNonTerminals()) {
            ArrayList<String> data = new ArrayList<>();
            if(nonTerminal.equals(grammar.getStartingSymbol())){
                data.add("epsilon");
            }
            currentColumn.put(nonTerminal, data);
        }

        table.add(currentColumn);
        int index = 0;
        //end of initialization

        //L1
        {
            HashMap<String, ArrayList<String>> newColumn = new HashMap<>();
            //copy from the last column
            for (String nonTerminal : this.grammar.getNonTerminals()) {
                ArrayList<String> toAdd = new ArrayList<>(table.get(index).get(nonTerminal));
                newColumn.put(nonTerminal, toAdd);
            }


            for (String nonTerminal : this.grammar.getNonTerminals()) {

                ArrayList<ArrayList<String>> productionsForNonTerminal = this.grammar.getProductions().get(nonTerminal);

                for (ArrayList<String> productionForNonTerminal : productionsForNonTerminal) {
                    for(int i = 0; i < productionForNonTerminal.size(); i++){
                        String symbol = productionForNonTerminal.get(i);
                        //if the symbol is a non terminal
                        if (this.grammar.getNonTerminals().contains(symbol)){
                            // B->pA
                            // follow(A) += follow(B)
                            if(i == productionForNonTerminal.size() - 1){
                                // FOLLOW(symbol) += FOLLOW(nonTerminal)
                                ArrayList<String> var = new ArrayList<>(table.get(index).get(nonTerminal)); //FOLLOW(nonTerminal)
                                ArrayList<String> aux = new ArrayList<>(newColumn.get(symbol));
                                aux.addAll(var);
                                aux = toSet(aux);
                                newColumn.remove(symbol);
                                newColumn.put(symbol, aux);
                            }
                            //B -> p A q
                            else{
                                // if after A is a non terminal
                                if(this.grammar.getNonTerminals().contains(productionForNonTerminal.get(i+1)))
                                {
                                    // first(q)
                                    ArrayList<String> nonTerminals = new ArrayList<>();
                                    String terminal = null;
                                    for(int j = i + 1; j < productionForNonTerminal.size(); j++)
                                        if(this.grammar.getNonTerminals().contains(productionForNonTerminal.get(j)))
                                            nonTerminals.add(productionForNonTerminal.get(j));
                                        else{
                                            terminal = productionForNonTerminal.get(j);
                                            break;
                                        }

                                    ArrayList<String> firstOfWhatIsAfter = multipleConcatenation(this.first, nonTerminals, terminal);
                                    // if first(q) contains epsilon
                                    if(firstOfWhatIsAfter.contains("epsilon")) {
                                        // FOLLOW(symbol) += FOLLOW(nonTerminal)
                                        ArrayList<String> var = new ArrayList<>(table.get(index).get(nonTerminal)); //FOLLOW(nonTerminal)
                                        ArrayList<String> aux = new ArrayList<>(newColumn.get(symbol));
                                        aux.addAll(var);
                                        aux = toSet(aux);
                                        newColumn.remove(symbol);
                                        newColumn.put(symbol, aux);

                                    }
                                    // FOLLOW(symbol) += FIRST(productionForNonTerminal.get(index+1)) \ {epsilon}
                                    ArrayList<String> f = new ArrayList<>(firstOfWhatIsAfter); //FIRST(q)
                                    f.remove("epsilon");
                                    ArrayList<String> aux = new ArrayList<>(newColumn.get(symbol));
                                    aux.addAll(f);
                                    aux = toSet(aux);
                                    newColumn.remove(symbol);
                                    newColumn.put(symbol, aux);
                                }
                                // if after A is a terminal (i.e. q is a terminal), then first(q) = q
                                // follow(A) += first(q)
                                else{
                                    ArrayList<String> f = new ArrayList<>();
                                    f.add(productionForNonTerminal.get(i+1));
                                    // if that terminal is epsilon, we do not add anything
                                    f.remove("epsilon");
                                    ArrayList<String> aux = new ArrayList<>(newColumn.get(symbol));
                                    aux.addAll(f);
                                    aux = toSet(aux);
                                    newColumn.remove(symbol);
                                    newColumn.put(symbol, aux);
                                }
                            }
                        }
                    }
                }

            }
            index++;
            table.add(newColumn);
        }
        //end of L1

        //the other columns
        while (!table.get(index).equals(table.get(index - 1))) {
            HashMap<String, ArrayList<String>> newColumn = new HashMap<>();
            //copy from the last column
            for (String nonTerminal : this.grammar.getNonTerminals()) {
                ArrayList<String> toAdd = new ArrayList<>(table.get(index).get(nonTerminal));
                newColumn.put(nonTerminal, toAdd);
            }


            for (String nonTerminal : this.grammar.getNonTerminals()) {

                ArrayList<ArrayList<String>> productionsForNonTerminal = this.grammar.getProductions().get(nonTerminal);

                for (ArrayList<String> productionForNonTerminal : productionsForNonTerminal) {
                    for(int i = 0; i < productionForNonTerminal.size(); i++){
                        String symbol = productionForNonTerminal.get(i);
                        //if the symbol is a non terminal
                        if (this.grammar.getNonTerminals().contains(symbol)){
                            // B->pA
                            // follow(A) += follow(B)
                            if(i == productionForNonTerminal.size() - 1){
                                // FOLLOW(symbol) += FOLLOW(nonTerminal)
                                ArrayList<String> var = new ArrayList<>(table.get(index).get(nonTerminal)); //FOLLOW(nonTerminal)
                                ArrayList<String> aux = new ArrayList<>(newColumn.get(symbol));
                                aux.addAll(var);
                                aux = toSet(aux);
                                newColumn.remove(symbol);
                                newColumn.put(symbol, aux);
                            }
                            //B -> p A q
                            else{
                                // if after A is a non terminal
                                if(this.grammar.getNonTerminals().contains(productionForNonTerminal.get(i+1)))
                                {
                                    // first(q)
                                    ArrayList<String> nonTerminals = new ArrayList<>();
                                    String terminal = null;
                                    for(int j = i + 1; j < productionForNonTerminal.size(); j++)
                                        if(this.grammar.getNonTerminals().contains(productionForNonTerminal.get(j)))
                                            nonTerminals.add(productionForNonTerminal.get(j));
                                        else{
                                            terminal = productionForNonTerminal.get(j);
                                            break;
                                        }

                                    ArrayList<String> firstOfWhatIsAfter = multipleConcatenation(this.first, nonTerminals, terminal);
                                    // if first(q) contains epsilon
                                    if(firstOfWhatIsAfter.contains("epsilon")) {
                                        // FOLLOW(symbol) += FOLLOW(nonTerminal)
                                        ArrayList<String> var = new ArrayList<>(table.get(index).get(nonTerminal)); //FOLLOW(nonTerminal)
                                        ArrayList<String> aux = new ArrayList<>(newColumn.get(symbol));
                                        aux.addAll(var);
                                        aux = toSet(aux);
                                        newColumn.remove(symbol);
                                        newColumn.put(symbol, aux);

                                    }
                                    // FOLLOW(symbol) += FIRST(productionForNonTerminal.get(index+1)) \ {epsilon}
                                    ArrayList<String> f = new ArrayList<>(firstOfWhatIsAfter); //FIRST(q)
                                    f.remove("epsilon");
                                    ArrayList<String> aux = new ArrayList<>(newColumn.get(symbol));
                                    aux.addAll(f);
                                    aux = toSet(aux);
                                    newColumn.remove(symbol);
                                    newColumn.put(symbol, aux);
                                }
                                // if after A is a terminal (i.e. q is a terminal), then first(q) = q
                                // follow(A) += first(q)
                                else{
                                    ArrayList<String> f = new ArrayList<>();
                                    f.add(productionForNonTerminal.get(i+1));
                                    // if that terminal is epsilon, we do not add anything
                                    f.remove("epsilon");
                                    ArrayList<String> aux = new ArrayList<>(newColumn.get(symbol));
                                    aux.addAll(f);
                                    aux = toSet(aux);
                                    newColumn.remove(symbol);
                                    newColumn.put(symbol, aux);
                                }
                            }
                        }
                    }
                }

            }
            index++;
            table.add(newColumn);

        }
        this.follow = table.get(table.size() - 1);
    }


    public HashMap<String, ArrayList<String>> getFirst() {
        return first;
    }

    public HashMap<String, ArrayList<String>> getFollow() {
        return follow;
    }

    private void computeParsingTable() {
        ArrayList<String> allSymbols = (ArrayList<String>) grammar.getNonTerminals().clone();
        allSymbols.addAll(grammar.getTerminals());
        allSymbols.add("$");
        for (String nonTerminal : allSymbols) {
            for (String terminal : grammar.getTerminals()) {
                if (nonTerminal.equals(terminal)) {
                    var pop = new ArrayList<String>();
                    pop.add("pop");
                    parsingTable.put(new Pair(nonTerminal, terminal), new ParsingTableCell(pop, 0));
                } else {
                    parsingTable.put(new Pair(nonTerminal, terminal), null);
                }
            }
            if (nonTerminal.equals("$")) {
                var acc = new ArrayList<String>();
                acc.add("acc");
                parsingTable.put(new Pair(nonTerminal, "$"), new ParsingTableCell(acc, 0));
            } else {
                parsingTable.put(new Pair(nonTerminal, "$"), null);
            }
        }

        var productions = this.grammar.getProductions();

        var prodIndex = 1;

        for (String nonTerminal : grammar.getNonTerminals()) {
            var nonTerminalProds = productions.get(nonTerminal);
            for (ArrayList<String> prod : nonTerminalProds) {
                if (grammar.getTerminals().contains(prod.get(0))) {
                    var rhs = prod.get(0);
                    parsingTable.put(new Pair(nonTerminal, rhs), new ParsingTableCell(prod, prodIndex));
                }
                else if (prod.get(0).equals("epsilon")) {
                    var follow = this.follow.get(nonTerminal);
                    for (String elem : follow) {
                        parsingTable.put(new Pair(nonTerminal, elem), new ParsingTableCell(prod, prodIndex));
                    }
                }
                else if(grammar.getNonTerminals().contains(prod.get(0))){
                    var rhs = this.first.get(prod.get(0));
                    for (String elem : rhs) {
                        parsingTable.put(new Pair(nonTerminal, elem), new ParsingTableCell(prod, prodIndex));
                    }
                }
                prodIndex++;
            }
        }
    }

    public HashMap<Pair, ParsingTableCell> getParsingTable() {
        return parsingTable;
    }

    public boolean parseSequence(ArrayList<String> sequence){
        initializeStacks(sequence);

        boolean go = true;
        boolean result = true;

        while(go){
            String headOfInputStack = inputStack.peek();
            String headOfWorkingStack = workingStack.peek();

            if(headOfWorkingStack.equals("$") && headOfInputStack.equals("$"))
                return result;

            Pair pair = new Pair(headOfWorkingStack, headOfInputStack);
            ParsingTableCell cell = this.parsingTable.get(pair);
            if(cell == null){
                pair = new Pair(headOfWorkingStack, "epsilon");
                cell = this.parsingTable.get(pair);
                if(cell != null){
                    this.workingStack.pop();
                    continue;
                }
            }

            if(cell == null){
                go = false;
                result = false;
            }
            else{
                ArrayList<String> seq = cell.getSequence();
                int productionNumber = cell.getProductionNumber();

                if (productionNumber == 0 && seq.get(0).equals("acc")) {
                    go = false;
                } else if (productionNumber == 0 && seq.get(0).equals("pop")) {
                    workingStack.pop();
                    inputStack.pop();
                } else {
                    workingStack.pop();
                    if (!seq.get(0).equals("epsilon")) {
                        for(int i = seq.size() - 1; i >= 0; i--)
                            workingStack.push(seq.get(i));

                    }
                    output.push(String.valueOf(productionNumber));
                }
            }

        }

        return result;

    }


    public void  initializeStacks(ArrayList<String> sequence){
        inputStack.clear();
        inputStack.push("$");
        for(int i = sequence.size() - 1; i >= 0; i--)
            inputStack.push(sequence.get(i));

        workingStack.clear();
        workingStack.push("$");
        workingStack.push(grammar.getStartingSymbol());

        output.clear();
        output.push("epsilon");
    }
}
