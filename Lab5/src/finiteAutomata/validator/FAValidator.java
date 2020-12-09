package finiteAutomata.validator;

import finiteAutomata.FiniteAutomaton;
import finiteAutomata.Pair;

import java.util.ArrayList;
import java.util.Map;

public class FAValidator implements Validator{

    private boolean isSet(ArrayList<String> arrayList){
        for(int i = 0; i < arrayList.size() - 1; i++){
            for(int j = i + 1; j < arrayList.size(); j++)
                if(arrayList.get(i).equals(arrayList.get(j)))
                    return false;
        }
        return true;
    }

    private boolean isIn(String str, ArrayList<String> list){
        for(String element: list){
            if(element.equals(str))
                return true;
        }
        return false;
    }


    @Override
    public void validate(FiniteAutomaton finiteAutomaton) throws ValidatorException {
        if(!isSet(finiteAutomaton.getAlphabet())) throw new ValidatorException("The alphabet has to be a set, i.e. every element appears only once!");
        if(!isSet(finiteAutomaton.getFinalStates())) throw new ValidatorException("The final states have to be a set, i.e. every element appears only once!");
        if(!isSet(finiteAutomaton.getStates())) throw new ValidatorException("The states have to be a set, i.e. every element appears only once!");

        for(Map.Entry<Pair, ArrayList<String>> e : finiteAutomaton.getTransitions().entrySet()){
            if(!isIn(e.getKey().getStartingState(), finiteAutomaton.getStates()))
                throw new ValidatorException("One of the starting states of the transitions is not belonging to the list of states");
            for(String element: e.getValue())
                if(!isIn(element, finiteAutomaton.getStates()))
                    throw new ValidatorException("One of the ending states of the transitions is not belonging to the list of states");
            if(!isSet(e.getValue()))
                throw new ValidatorException("One of the transitions does not have a set as ending states!");
            if(!isIn(e.getKey().getSymbol(), finiteAutomaton.getAlphabet()))
                throw new ValidatorException("One of the transitions has a symbol which is not belonging to the alphabet");
        }

        for(String element: finiteAutomaton.getFinalStates())
            if(!isIn(element, finiteAutomaton.getStates()))
                throw new ValidatorException("One of the final states is not belonging to the list of states");
        if(!isIn(finiteAutomaton.getInitialState(), finiteAutomaton.getStates()))
            throw new ValidatorException("The initial state is not belonging to the list of states");




    }




}
