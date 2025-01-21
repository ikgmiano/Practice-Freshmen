import java.util.NoSuchElementException;

public class DequeLinearLinkedList<T> {
    private DoublyLinkedList<T> elementData;
    private int numOfElements;
    private int front;
    private int rear;

    // Creates an linear circular queue.
    // Implementation Note: This data structure uses a doubly linked list as its internal structure.
    DequeLinearLinkedList() {
        this.elementData = new DoublyLinkedList<>();
        this.numOfElements = 0;
        this.front = 0;
        this.rear = 0;
    }

    // Enqueues (add) the specified value at the front of the queue.
    public void enqueueFront(T value) {
        if (numOfElements == 0) {
            enqueueRear(value);
        } else {
            elementData.addAt(front, value);
            rear++;
            numOfElements++;
        }
    }

    // Enqueues (add) the specified value at the end of the queue.
    public void enqueueRear(T value) {
        elementData.add(value);
        rear++;
        numOfElements++;
    }

    // Dequeues (remove) the element at the front of the queue and return it.
    // Throws an exception if there is currently no element in the queue.
    public T dequeueFront() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        T toDequeue = peekFront();
        elementData.removeAt(front);
        rear--;
        numOfElements--;

        return toDequeue;
    }

    // Dequeues (remove) the element at the end of the queue and return it.
    // Throws an exception if there is currently no element in the queue.
    public T dequeueRear() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        T toDequeue = peekRear();
        elementData.removeAt(rear - 1);
        rear--;
        numOfElements--;

        return toDequeue;
    }

    // Returns the element at the front of the queue without removing it.
    public T peekFront() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        return elementData.get(front);
    }

    // Returns the element at the end of the queue without removing it.
    public T peekRear() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        return elementData.get(rear - 1);
    }

    // Returns the size (number of elements) of the queue.
    public int size() {
        return numOfElements;
    }

    // Returns true if there are no elements in the queue, otherwise false.
    public boolean isEmpty() {
        return numOfElements == 0;
    }

    // Returns a string representation of the object.
    @Override
    public String toString() {
        if (isEmpty()) {
            return "[]";
        }

        String string = "";
        for (int i = 0; i < numOfElements; i++) {
            string += elementData.get(i) + ", ";
        }

        string = "[" + string.substring(0, string.length() - 2) + "]";
        return string;
    }
}
