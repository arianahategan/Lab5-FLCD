package finiteAutomata;

public class Transition {
    private String startingState;
    private String endingState;
    private String symbol;

    public Transition(String startingState, String endingState, String symbol) {
        this.startingState = startingState;
        this.endingState = endingState;
        this.symbol = symbol;
    }

    public String getStartingState() {
        return startingState;
    }

    public void setStartingState(String startingState) {
        this.startingState = startingState;
    }

    public String getEndingState() {
        return endingState;
    }

    public void setEndingState(String endingState) {
        this.endingState = endingState;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return "Transition{" +
                "startingState='" + startingState + '\'' +
                ", endingState='" + endingState + '\'' +
                ", symbol='" + symbol + '\'' +
                '}';
    }
}
