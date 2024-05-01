public class SingleLinkedList {
    private NodeForLinkedList head;
    private int size;

    public SingleLinkedList() {
        head = null;
        size = 0;
    }

    public void add(String word) {
        if (head == null) {
            head = new NodeForLinkedList(word);
            size++;
        } else {
            NodeForLinkedList temp = head;
            while (temp.next != null) {
                if (temp.word.equals(word)) {
                    temp.frequency++;
                    return;
                }
                temp = temp.next;
            }
            if (temp.word.equals(word)) {
                temp.frequency++;
            } else {
                temp.next = new NodeForLinkedList(word);
                size++;
            }
        }
    }


    public boolean contains(String word) {
        NodeForLinkedList temp = head;
        while (temp != null) {
            if (temp.word.equals(word)) {
                return true;
            }
            temp = temp.next;
        }
        return false;
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        NodeForLinkedList temp = head;
        while (temp != null) {
            result.append(temp.word).append(": ").append(temp.frequency).append("\n");
            temp = temp.next;
        }
        return result.toString();
    }
    public void print() {
        NodeForLinkedList temp = head;
        while (temp != null) {
            System.out.println(temp.word + ": " + temp.frequency);
            temp = temp.next;
        }
    }

}
