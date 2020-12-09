package finiteAutomata;

public class Pair {
    String startingState;
    String symbol;

    public Pair(String startingState, String symbol) {
        this.startingState = startingState;
        this.symbol = symbol;
    }

    public String getStartingState() {
        return startingState;
    }

    public void setStartingState(String startingState) {
        this.startingState = startingState;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return "(" +
                startingState + ',' +
                symbol +
                ')';
    }
}
