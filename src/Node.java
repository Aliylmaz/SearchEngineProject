class Node {
    String word;

    Node left, right;
    SingleLinkedList list;


    public Node(String item,String fileName) {
        word = item;
        left = right = null;
        this.list = new SingleLinkedList();
        this.list.add(fileName);

    }






}