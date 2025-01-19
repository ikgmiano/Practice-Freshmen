import java.util.NoSuchElementException;

public class DoublyLinkedList<T> {

    // Class structure for the node.
    class Node {
        T value; // the value stored in this node
        Node next; // the node next to this node
        Node prev; // the node before this node

        Node(T value) {
            this.value = value;
            this.next = null; // this isn't necessary, i just initialized it for completeness :)
            this.prev = null; // this one too
        }
    }

    /**
     * It is essential that we have a separate method when dealing with the insertion or deletion at both end nodes
     * because we have a pointer for head and tail. We cannot just add and delete without updating where the head
     * and tail must point to after insertion or deletion.
     */

    private Node head;
    private Node tail;
    private int size;

    // Constructs an empty singly linked list object.
    DoublyLinkedList() {}

    // Constructs a singly linked list object with the specified value set as the head (and tail, since there's only one element by this point).
    DoublyLinkedList(T value) {
        Node newNode = new Node(value);
        this.head = newNode;
        this.tail = newNode;
        this.size = 0; // again, this is just for completeness
    }

    public T getFirst() {
        return (head == null) ? null : head.value;
    }

    public T getLast() {
        return (tail == null) ? null : tail.value;
    }

    // Adds the specified value at the front of the list, updating the head pointer accordingly.
    private void addFirst(T value) {
        Node newNode = new Node(value);

        newNode.next = head;
        if (head == null) {
            tail = newNode;
        } else {
            head.prev = newNode;
        }
        head = newNode;
        size++;
    }

    // Adds the specified value at the end of the list, updating the tail pointer accordingly.
    private void addLast(T value) {
        Node newNode = new Node(value);

        newNode.prev = tail;
        if (tail == null) {
            head = newNode;
        } else {
            tail.next = newNode;
        }
        tail = newNode;
        size++;
    }

    // Adds the specified value at the end of the list.
    public void add(T value) {
        addLast(value);
    }

    // Adds the specified value at the specified index.
    public void addAt(int index, T value) {
        // checks if the index is within the valid range, throw exception if not
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(String.format("Index %d is out of bound for length %d", index, size));
        }

        if (index == 0) {
            addFirst(value); // we use addFirst() to adjust the head pointer properly
        } else {
            Node newNode = new Node(value);
            Node curr = head;

            for (int i = 0; i < index; i++) {
                curr = curr.next;
            }

            Node prev = curr.prev;
            newNode.next = curr;
            curr.prev = newNode;
            prev.next = newNode;
            newNode.prev = prev;
            size++;
        }
    }

    // Removes the value at the front of the list and return it, updating the head pointer accordingly.
    private T removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException(); // there's nothing to remove if array is empty
        }

        T toRemove = head.value;
        head = head.next;
        if (head == null) {
            tail = null;
        } else {
            head.prev = null;
        }
        size--;

        return toRemove;
    }

    // Removes the value at the end of the list and return it, updating the tail pointer accordingly.
    private T removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException(); // there's nothing to remove if array is empty
        }

        T toRemove = tail.value;
        tail = tail.prev;
        if (tail == null) {
            head = null;
        } else {
            tail.next = null;
        }
        size--;

        return toRemove;
    }

    // Removes the first instance of the specified value from the list and return it, otherwise return null.
    public T remove(T value) {
        if (isEmpty()) {
            throw new NoSuchElementException(); // there's nothing to remove if array is empty
        }

        Node curr = head;
        while (curr != null) {
            if (curr.value.equals(value)) {
                if (curr == head) {
                    return removeFirst(); // we use removeFirst() to adjust the head pointer properly
                } else if (curr == tail) {
                    return removeLast(); // we use removeLast() to adjust the tail pointer properly
                } else {
                    Node prev = curr.prev;
                    Node next = curr.next;
                    prev.next = next;
                    next.prev = prev;
                    size--;
                    return curr.value;
                }
            }
            curr = curr.next;
        }

        return null;
    }

    // Removes the value at the specified index from the list and return it.
    public T removeAt(int index) {
        // checks if the index is within the valid range, throw exception if not
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(String.format("Index %d is out of bound for length %d", index, size));
        }

        if (index == 0) {
            return removeFirst(); // we use removeFirst() to adjust the head pointer properly
        } else if (index == (size - 1)) {
            return removeLast(); // we use removeLast() to adjust the tail pointer properly
        } else {
            Node curr = head;

            for (int i = 0; i < index; i++) {
                curr = curr.next;
            }

            Node prev = curr.prev;
            Node next = curr.next;
            prev.next = next;
            next.prev = prev;
            size--;
            return curr.value;
        }
    }

    // Returns the value at the specified index.
    public T get(int index) {
        // checks if the index is within the valid range, throw exception if not
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(String.format("Index %d is out of bound for length %d", index, size));
        }

        Node curr = head;

        for (int i = 0; i < index; i++) {
            curr = curr.next;
        }

        return curr.value;
    }

    // Replaces the value at the specified index with the specified value.
    public void replace(int index, T value) {
        // checks if the index is within the valid range, throw exception if not
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(String.format("Index %d is out of bound for length %d", index, size));
        }

        Node curr = head;

        for (int i = 0; i < index; i++) {
            curr = curr.next;
        }

        curr.value = value;
    }

    // Returns the size (number of elements) of the array.
    public int size() {
        return size;
    }

    // Returns true if there is no element in the array, false otherwise.
    public boolean isEmpty() {
        return size == 0;
    }

    // Returns a string representation of the object.
    @Override
    public String toString() {
        if (isEmpty()) {
            return "[]";
        }

        String string = "";
        Node curr = head;

        while (curr != null) {
            string += curr.value + ", ";
            curr = curr.next;
        }

        string = "[" + string.substring(0, string.length() - 2) + "]";
        return string;
    }
}