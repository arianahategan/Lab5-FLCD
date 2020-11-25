import grammar.Grammar;
import parser.Parser;

public class Main {

    public static void main(String[] args) {
        Grammar grammar = new Grammar("grammar.in");
        System.out.println(grammar);
        Parser parser = new Parser(grammar);
        System.out.println(parser.getFirst());
        System.out.println(parser.getFollow());
    }
}
