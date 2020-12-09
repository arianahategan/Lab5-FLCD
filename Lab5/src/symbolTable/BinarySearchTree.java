package symbolTable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class BinarySearchTree {
    private Node root;

    private int length;

    public BinarySearchTree() {
        length = 0;
    }

    public Node getRoot() {
        return root;
    }

    public int search(String value){
        Node currentNode = root;
        int position = -1;
        while(currentNode != null && position == -1)
            if(currentNode.getValue().equals(value))
                position = currentNode.getIndex();
            else
                if(currentNode.getValue().compareTo(value) < 0)
                    currentNode = currentNode.getRight();
                else
                    currentNode = currentNode.getLeft();
        return position;
    }

    public void insert(String value) {
        root = insertRecursive(root, value);
    }

    private Node insertRecursive(Node current, String value) {

        if (current == null) {
            length++;
            return new Node(value, length - 1);
        }

        if (current.getValue().compareTo(value) > 0) {
            current.setLeft(insertRecursive(current.getLeft(), value));
        } else if (current.getValue().compareTo(value) < 0) {
            current.setRight(insertRecursive(current.getRight(), value));
        }

        return current;
    }

    private ArrayList<Node> getAll(Node root, ArrayList<Node> nodes) {
        if (root != null) {
            nodes.add(root);
            getAll(root.getLeft(), nodes);
            getAll(root.getRight(), nodes);
        }
        return nodes;
    }

    public String getNodesByIndex() {
        ArrayList<Node> nodes = getAll(root, new ArrayList<Node>());
        nodes.sort(new Comparator<Node>() {
            @Override
            public int compare(Node o1, Node o2) {
                return Integer.compare(o1.getIndex(), o2.getIndex());
            }
        });

        StringBuilder str = new StringBuilder();
        for(Node node: nodes)
            str.append(node.getValue()).append(" ");
        return str.toString();

    }

}
