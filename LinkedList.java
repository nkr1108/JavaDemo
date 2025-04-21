import javax.swing.*;

class LinkedList<T extends Comparable<T>> {
    public LLNode<T> head;
    private int comparisionCount = 0;

    // Insert at the end of the list
    public void insert(T data) {
        LLNode<T> newNode = new LLNode<>(data);
        if (head == null) {
            head = newNode;
        } else {
            LLNode<T> temp = head;
            while (temp.next != null) {
                temp = temp.next;
            }
            temp.next = newNode;
        }
    }

    // Print the linked list
    public void display() {
        LLNode<T> temp = head;
        while (temp != null) {
            System.out.print(temp.data + " -> ");
            temp = temp.next;
        }
        System.out.println("null");
    }

    public LLNode<T> getHead() {
        return head;
    }

    // Get the tail node (needed for Quick Sort)
    private LLNode<T> getTail(LLNode<T> node) {
        while (node != null && node.next != null) {
            node = node.next;
        }
        return node;
    }

    private LLNode<T> mergeSortLL(LLNode<T> head) {
        if (head == null || head.next == null) {
            return head; // Base case: return if list has 0 or 1 nodes
        }

        LLNode<T> middle = getMiddle(head); // Find middle of the list
        LLNode<T> nextToMiddle = middle.next;
        middle.next = null; // Split list into two halves

        LLNode<T> left = mergeSortLL(head);  // Sort first half
        LLNode<T> right = mergeSortLL(nextToMiddle); // Sort second half

        return mergeLL(left, right); // Merge sorted halves
    }

    private LLNode<T> mergeLL(LLNode<T> left, LLNode<T> right) {
        LLNode<T> sortedList = new LLNode<>(null); // Dummy node
        LLNode<T> temp = sortedList;

        while (left != null && right != null) {
            if (left.data.compareTo(right.data) <= 0) {
                temp.next = left;
                left = left.next;
            } else {
                temp.next = right;
                right = right.next;
            }
            temp = temp.next;
        }

        temp.next = (left != null) ? left : right; // Append remaining nodes

        return sortedList.next; // Return sorted merged list
    }

    private LLNode<T> getMiddle(LLNode<T> head) {
        if (head == null) return head;

        LLNode<T> slow = head, fast = head;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        return slow; // Middle node
    }


    public void mergeSort() {
        head = mergeSortLL(head);
    }

    public void displayLinkedList(JTextArea textArea) {
        textArea.setText("");  // Clear previous text
        int count = 0;

        LLNode<T> temp = head;
        while (temp != null) {
            textArea.append(temp.data + " ");
            count++;
            if (count % 20 == 0) {  // Format: 20 items per row
                textArea.append("\n");
            }
            temp = temp.next;
        }
    }

    // Iterative search in the linked list
    public boolean search(T key) {
        LLNode<T> temp = head;
        comparisionCount = 0;
        while (temp != null) {
            comparisionCount++;
            if (temp.data.equals(key)) {
                return true; // Found the key
            }
            temp = temp.next; // Move to the next node
        }
        return false; // Key not found
    }

    public int getComparisionCount(){
        return comparisionCount;
    }

    // Method to create a copy of the linked list
    public LinkedList<T> copy() {
        LinkedList<T> newList = new LinkedList<>();
        LLNode<T> temp = head;

        while (temp != null) {
            newList.insert(temp.data); // Insert each element into the new list
            temp = temp.next;
        }

        return newList;
    }


}
