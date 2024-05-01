class Node {
    String word;
    int frequency;
    Node left, right;
    SingleLinkedList list;

    public Node(String item,String fileName) {
        word = item;
        frequency = 1;
        left = right = null;
        this.list = new SingleLinkedList();
        this.list.add(fileName);
    }
}