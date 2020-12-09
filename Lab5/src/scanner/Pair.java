package scanner;

public class Pair {
    String token;
    int positionInST;

    public Pair(String stringValue, int intValue) {
        this.token = stringValue;
        this.positionInST = intValue;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getPositionInST() {
        return positionInST;
    }

    public void setPositionInST(int positionInST) {
        this.positionInST = positionInST;
    }

    @Override
    public String toString() {
        return
                "token='" + token + '\'' +
                ", positionInST=" + positionInST;
    }
}
