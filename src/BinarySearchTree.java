import java.util.ArrayList;
import java.util.List;



public class BinarySearchTree {
    Node root;

    public BinarySearchTree() {
        root = null;
    }

    // Kelimeyi ikili arama ağacına ekleme
    public void insert(String word) {
        root = insertRec(root, word);
    }

    // Yardımcı metot: Kelimeyi ikili arama ağacına ekleme
    private Node insertRec(Node root, String word) {
        if (root == null) {
            root = new Node(word);
            return root;
        }

        // Kelimeyi alfabetik sıraya göre ekle
        int compareResult = word.compareTo(root.word);
        if (compareResult < 0) {
            root.left = insertRec(root.left, word);
        } else if (compareResult > 0) {
            root.right = insertRec(root.right, word);
        } else {
            // Eğer kelime zaten varsa, frekansı arttır
            root.frequency++;
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
        preOrderTraversalRec(root, result);
        return result.toString();
    }

    // Yardımcı metot: Belirli bir kelimenin pre-order dolaşımı
    private void preOrderTraversalRec(Node root, StringBuilder result) {
        if (root != null) {
            result.append(root.word).append(": ").append(root.frequency).append("\n");
            preOrderTraversalRec(root.left, result);
            preOrderTraversalRec(root.right, result);
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

    // Diğer metotlar buraya eklenebilir

}