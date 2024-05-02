import java.util.ArrayList;
import java.util.List;



public class BinarySearchTree {
    Node root;

    public BinarySearchTree() {
        root = null;
    }

    // Kelimeyi ikili arama ağacına ekleme
    public void insert(String word, String fileName) {
        root = insertRec(root, word, fileName);
    }

    private Node insertRec(Node root, String word, String fileName) {
        if (root == null) {
            root = new Node(word, fileName);
            return root;
        }
        int compareResult = word.compareTo(root.word);
        if (compareResult < 0) {
            root.left = insertRec(root.left, word, fileName);
        } else if (compareResult > 0) {
            root.right = insertRec(root.right, word, fileName);
        } else {
            // If word already exists, increase the frequency and update file names
            root.frequency++;
            if (!root.list.contains(fileName)) {
                root.list.add(fileName);
            }
        }
        return root;
    }


    // Belirli bir kelimenin frekansını döndürme
    public int search(String word) {
        return searchRec(root, word);
    }

    // Yardımcı metot: Belirli bir kelimenin frekansını döndürme
    private int searchRec(Node root, String word) {
        if (root == null) {
            return 0;
        }

        // Kelimeyi alfabetik sıraya göre ara
        int compareResult = word.compareTo(root.word);
        if (compareResult < 0) {
            return searchRec(root.left, word);
        } else if (compareResult > 0) {
            return searchRec(root.right, word);
        } else {
            return root.frequency;
        }
    }

    // Belirli bir kelimenin post-order dolaşımı
    public String postOrderTraversal() {
        StringBuilder result = new StringBuilder();
        postOrderTraversalRec(root, result);
        return result.toString();
    }

    // Yardımcı metot: Belirli bir kelimenin post-order dolaşımı
    private void postOrderTraversalRec(Node root, StringBuilder result) {
        if (root != null) {
            postOrderTraversalRec(root.left, result);
            postOrderTraversalRec(root.right, result);
            result.append(root.word).append(": ").append(root.frequency).append("\n");
        }
    }

    // Belirli bir kelimenin pre-order dolaşımı
    public String preOrderTraversal() {
        StringBuilder result = new StringBuilder();
        preOrderTraversalRec(root, result);  // Pass the StringBuilder to each recursive call
        return result.toString();
    }

    // Helper method: Pre-order traversal of the tree
    private void preOrderTraversalRec(Node node, StringBuilder result) {
        if (node != null) {
            // Append the current node's word and frequency to the result
            result.append(node.word).append(": ").append(node.frequency).append("\n");

            // Recursively process the left subtree
            preOrderTraversalRec(node.left, result);

            // Recursively process the right subtree
            preOrderTraversalRec(node.right, result);
        }
    }


    // Belirli bir kelimenin in-order dolaşımı
    public String inOrderTraversal() {
        StringBuilder result = new StringBuilder();
        inOrderTraversalRec(root, result);
        return result.toString();
    }

    // Yardımcı metot: Belirli bir kelimenin in-order dolaşımı
    private void inOrderTraversalRec(Node root, StringBuilder result) {
        if (root != null) {
            inOrderTraversalRec(root.left, result);
            result.append(root.word).append(": ").append(root.frequency).append("\n");
            inOrderTraversalRec(root.right, result);
        }
    }
    public void print() {
        printRec(root);
    }
    private void printRec(Node root) {
        if (root != null) {
            printRec(root.left);
            System.out.println(root.word + ": " + root.frequency);
            printRec(root.right);
        }
    }



    // Diğer metotlar buraya eklenebilir

}