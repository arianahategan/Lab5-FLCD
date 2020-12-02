package parser;

import java.util.Objects;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair pair = (Pair) o;
        return firstElement.equals(pair.firstElement) &&
                secondElement.equals(pair.secondElement);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstElement, secondElement);
    }

    @Override
    public String toString() {
        return "Pair{" +
                "firstElement='" + firstElement + '\'' +
                ", secondElement='" + secondElement + '\'' +
                '}';
    }
}