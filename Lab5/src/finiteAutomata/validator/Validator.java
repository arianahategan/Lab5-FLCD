package finiteAutomata.validator;


import finiteAutomata.FiniteAutomaton;

public interface Validator<T> {
    void validate(FiniteAutomaton finiteAutomaton) throws ValidatorException;
}
