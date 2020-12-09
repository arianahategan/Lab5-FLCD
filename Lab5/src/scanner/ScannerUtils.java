package scanner;

import symbolTable.SymbolTable;

import java.io.*;
import java.util.ArrayList;

public class ScannerUtils {
    public static void writeToSTFile(SymbolTable symbolTable) {
        try {
            FileWriter myWriter = new FileWriter("ST.out");
            myWriter.write(symbolTable.getNodesByIndex());
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred when writing in ST.out file.");
            e.printStackTrace();
        }
    }

    public static void writeToPIFFile(PIF pif) {
        try {
            FileWriter myWriter = new FileWriter("PIF.out");
            myWriter.write(pif.toString());
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred when writing in PIF.out file.");
            e.printStackTrace();
        }
    }

    public static String chooseInputProgram() throws IOException {
        System.out.println("Choose the input program from above:");
        System.out.println("\t 1. p1.in\n \t 2. p1err.in\n \t 3. p2.in\n \t 4. p3.in");

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        String name = reader.readLine();
        if(name.equals("1")){
            return "p1.in";
        }
        if(name.equals("2")){
            return "p1err.in";
        }
        if(name.equals("3")){
            return "p2.in";
        }
        if(name.equals("4")){
            return "p3.in";
        }
        throw new IOException("Input not available1");

    }

    public static String[] readReservedWordsFromFile() {
        String[] reservedWords = null;
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader("token.in"));
            String line = reader.readLine();
            int lineIndex = 1;
            while (line != null) {
                if(lineIndex == 3){
                    reservedWords = line.split(",");
                }
                line = reader.readLine();
                lineIndex++;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return reservedWords;
    }

    public static String[] readOperatorsFromFile() {
        String[] operators = null;
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader("token.in"));
            String line = reader.readLine();
            int lineIndex = 1;
            while (line != null) {
                if(lineIndex == 1){
                    operators = line.split(",");
                }
                line = reader.readLine();
                lineIndex++;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return operators;
    }

    public static String[] readSeparatorsFromFile() {
        String[] separators = null;
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader("token.in"));
            String line = reader.readLine();
            int lineIndex = 1;
            while (line != null) {
                if(lineIndex == 2){
                    separators = line.split(",");
                }
                line = reader.readLine();
                lineIndex++;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return separators;
    }

    public static boolean isConstant(String token) {

//        //integer
//        if(token.matches("[+-]?[1-9][0-9]*") || token.matches("0"))
//            return true;

        String lastCharacter = String.valueOf(token.charAt(token.length() - 1));

        //string
        if(String.valueOf(token.charAt(0)).equals("\"") && lastCharacter.equals("\""))
            if(token.substring(1, token.length() - 1).matches("[0-9A-Za-z_ ]*")){
                return true;
            }


        //char
        if(String.valueOf(token.charAt(0)).equals("'") && lastCharacter.equals("'"))
            if(token.matches("[0-9A-Za-z ]"))
                return true;

        //boolean
        return token.equals("TRUE") || token.equals("FALSE");
    }

    public static boolean isIdentifier(String token) {
        return token.matches("[a-zA-Z]+[a-zA-Z0-9]*");
    }

    public static boolean isReservedWord(String token, ArrayList<String> reservedWords) {
        for(String reservedWord: reservedWords)
            if(token.equals(reservedWord))
                return true;
        return false;
    }

    public static boolean isSeparator(String token, ArrayList<String> separators) {
        for(String separator: separators)
            if(token.equals(separator) && !token.equals(" "))
                return true;
        return false;
    }

    public static boolean isOperator(String token, ArrayList<String> operators) {
        for(String operator: operators)
            if(token.equals(operator))
                return true;
        return false;
    }
}
