package finiteAutomata;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class FiniteAutomaton {
    private ArrayList<String> states;
    private ArrayList<String> alphabet;
    private HashMap<Pair, ArrayList<String>> transitions;
    private String initialState;
    private ArrayList<String> finalStates;

    public FiniteAutomaton(ArrayList<String> states, ArrayList<String> alphabet, HashMap<Pair, ArrayList<String>> transitions, String initialState, ArrayList<String> finalStates) {
        this.states = states;
        this.alphabet = alphabet;
        this.transitions = transitions;
        this.initialState = initialState;
        this.finalStates = finalStates;
    }

    public ArrayList<String> getStates() {
        return states;
    }

    public void setStates(ArrayList<String> states) {
        this.states = states;
    }

    public ArrayList<String> getAlphabet() {
        return alphabet;
    }

    public void setAlphabet(ArrayList<String> alphabet) {
        this.alphabet = alphabet;
    }

    public HashMap<Pair, ArrayList<String>> getTransitions() {
        return transitions;
    }

    public void setTransitions(HashMap<Pair, ArrayList<String>> transitions) {
        this.transitions = transitions;
    }

    public String getInitialState() {
        return initialState;
    }

    public void setInitialState(String initialState) {
        this.initialState = initialState;
    }

    public ArrayList<String> getFinalStates() {
        return finalStates;
    }

    public void setFinalStates(ArrayList<String> finalStates) {
        this.finalStates = finalStates;
    }

    public boolean isDeterministic() {
        for(Map.Entry<Pair, ArrayList<String>> e : transitions.entrySet()){
            if(e.getValue().size() > 1)
                return false;
        }

        return true;
    }

    public boolean sequenceAccepted(String sequence){
        if(sequence.length() > 0)
            return isAccepted(sequence, getInitialState());
        else
            return false;
    }

    private ArrayList<String> getPair(String state, String symbol){
        for(Map.Entry<Pair, ArrayList<String>> e : transitions.entrySet()){
            if(e.getKey().startingState.equals(state) && e.getKey().symbol.equals(symbol))
                return e.getValue();
        }

        return null;
    }

    public boolean isAccepted(String sequence, String currentState){
        String letterFromAlphabet = String.valueOf(sequence.charAt(0));
        ArrayList<String> next = getPair(currentState, letterFromAlphabet);
        if(next != null){
            for(String s: next){
                if(finalStates.contains(s)){
                    return true;
                }
                if(sequence.length() > 1 && isAccepted(sequence.substring(1), s))
                    return true;
            }
        }
        return false;


//        for(Map.Entry<Pair, ArrayList<String>> e : transitions.entrySet()){
//            if(e.getKey().startingState.equals(startingState) && e.getKey().symbol.equals(letterFromAlphabet)){
//                for(String nextState: e.getValue()){
//                    if(sequence.length() == 1 && !finalStates.contains(nextState))
//                        return false;
//                    if(sequence.length() == 1 && finalStates.contains(nextState))
//                        return true;
//                    if(isAccepted(sequence.substring(1), nextState)) return true;
//                }
//            }
//        }
//        return false;
    }

    @Override
    public String toString() {
        return "FiniteAutomaton{" +
                "states=" + states +
                ", alphabet=" + alphabet +
                ", transitions=" + transitions +
                ", initialState='" + initialState + '\'' +
                ", finalStates=" + finalStates +
                '}';
    }
}
