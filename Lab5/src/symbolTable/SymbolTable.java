package symbolTable;

public class SymbolTable {
    private final BinarySearchTree binarySearchTree;

    public SymbolTable(BinarySearchTree binarySearchTree) {
        this.binarySearchTree = binarySearchTree;
    }

    public void insert(String value){
        binarySearchTree.insert(value);
    }

    public int search(String value){
        return binarySearchTree.search(value);
    }

    public String getNodesByIndex(){
        return binarySearchTree.getNodesByIndex();
    }


}
