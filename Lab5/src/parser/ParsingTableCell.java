package parser;

import java.util.ArrayList;

public class ParsingTableCell {
    private ArrayList<String> sequence;
    int step;

    public ParsingTableCell(ArrayList<String> sequence, int step) {
        this.sequence = sequence;
        this.step = step;
    }

    public ArrayList<String> getSequence() {
        return sequence;
    }

    public void setSequence(ArrayList<String> sequence) {
        this.sequence = sequence;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    @Override
    public String toString() {
        return "(" + sequence +
                ", " + step +
                ')';
    }
}
