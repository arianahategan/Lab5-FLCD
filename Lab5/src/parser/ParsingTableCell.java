package parser;

import java.util.ArrayList;

public class ParsingTableCell {
    private ArrayList<String> sequence;
    int productionNumber;

    public ParsingTableCell(ArrayList<String> sequence, int step) {
        this.sequence = sequence;
        this.productionNumber = step;
    }

    public ArrayList<String> getSequence() {
        return sequence;
    }

    public void setSequence(ArrayList<String> sequence) {
        this.sequence = sequence;
    }

    public int getProductionNumber() {
        return productionNumber;
    }

    public void setProductionNumber(int step) {
        this.productionNumber = step;
    }

    @Override
    public String toString() {
        return "(" + sequence +
                ", " + productionNumber +
                ')';
    }
}