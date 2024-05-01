class Node {
    String word;
    int frequency;
    Node left, right;

    public Node(String item) {
        word = item;
        frequency = 1;
        left = right = null;
    }
}