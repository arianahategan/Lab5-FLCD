package scanner;

import java.util.ArrayList;

public class Scanner {

    ArrayList<String> separators;
    ArrayList<String> operators;

    public Scanner(ArrayList<String> separators, ArrayList<String> operators) {
        this.separators = separators;
        this.operators = operators;
    }

    public ArrayList<String> getTokensFromLine(String line){
        ArrayList<String> tokens = new ArrayList<>();
        int index = 0;
        StringBuilder currentToken = new StringBuilder();
        while(index < line.length()){
            //string
            if(String.valueOf(line.charAt(index)).equals("\"")){
                if (!currentToken.toString().equals(""))
                    tokens.add(currentToken.toString());
                int index1 = indexAfterString(line, index);
                tokens.add(line.substring(index, index1+1));
                index = index1 + 1;
                currentToken = new StringBuilder();
            }
            else{
                //char
                if(String.valueOf(line.charAt(index)).equals("'")) {
                    if (!currentToken.toString().equals(""))
                        tokens.add(currentToken.toString());
                    int index1 = indexAfterChar(line, index);
                    tokens.add(line.substring(index, index1+1));
                    index = index1 + 1;
                    currentToken = new StringBuilder();
                }

                else{
                    //operator
                    if(isSymbolOfOperator(line.charAt(index))){
                        if (!currentToken.toString().equals(""))
                            tokens.add(currentToken.toString());
                        int index1 = indexAfterOperator(line, index);

                        if(String.valueOf(line.charAt(index1)).matches("[1-9]") && (line.substring(index, index1).equals("+") || line.substring(index, index1).equals("-")))
                            currentToken.append(line.substring(index, index1));
                        else{
                            tokens.add(line.substring(index, index1));
                            currentToken = new StringBuilder();
                        }

                        index = index1;

                    }

                    else{
                        //separator
                        if(separators.contains(String.valueOf(line.charAt(index)))) {
                            if (!currentToken.toString().equals(""))
                                tokens.add(currentToken.toString());

                            tokens.add(String.valueOf(line.charAt(index)));
                            index++;
                            currentToken = new StringBuilder();
                        }
                        //other
                        else{
                            if(!String.valueOf(line.charAt(index)).equals("\t")){
                                currentToken.append(line.charAt(index));
                            }
                            index++;


                        }
                    }
                }
            }

        }
        if (!currentToken.toString().equals(""))
            tokens.add(currentToken.toString());
        return tokens;
    }

    private int indexAfterChar(String line, int index) {
        int counter = 0;
        while(index < line.length() && counter < 2){
            if(String.valueOf(line.charAt(index)).equals("'"))
                counter++;
            index++;
        }
        return index - 1;
    }

    private int indexAfterString(String line, int index) {
        int counter = 0;
        while(index < line.length() && counter < 2){
            if(String.valueOf(line.charAt(index)).equals("\""))
                counter++;
            index++;
        }
        return index - 1;
    }

    private int indexAfterOperator(String line, int index) {
        if(index < line.length() && isSymbolOfOperator(line.charAt(index)) && isSymbolOfOperator(line.charAt(index+1)) && isOperator(line.substring(index, index + 2))){
            return index + 2;
        }
        return index + 1;
    }

    private boolean isSymbolOfOperator(char charAt) {
        for(String operator: operators)
            if(operator.indexOf(charAt) != -1)
                return true;
        return false;
    }

    private boolean isOperator(String possibleOperator) {
        for(String operator: operators)
            if(operator.equals(possibleOperator))
                return true;
        return false;
    }


}
