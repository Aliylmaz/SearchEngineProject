public class NodeForLinkedList {
    String word;
    int frequency;
    NodeForLinkedList next;

    public NodeForLinkedList(String item) {
        word = item;
        frequency = 1;
        next = null;
    }
}
