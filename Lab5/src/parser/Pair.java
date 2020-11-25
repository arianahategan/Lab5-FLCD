package parser;

public class Pair {
    private String firstElement;
    private String secondElement;

    public Pair(String firstElement, String secondElement) {
        this.firstElement = firstElement;
        this.secondElement = secondElement;
    }

    public String getFirstElement() {
        return firstElement;
    }

    public void setFirstElement(String firstElement) {
        this.firstElement = firstElement;
    }

    public String getSecondElement() {
        return secondElement;
    }

    public void setSecondElement(String secondElement) {
        this.secondElement = secondElement;
    }

    @Override
    public String toString() {
        return "Pair{" +
                "firstElement='" + firstElement + '\'' +
                ", secondElement='" + secondElement + '\'' +
                '}';
    }
}
