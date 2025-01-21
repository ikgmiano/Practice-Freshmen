import java.util.NoSuchElementException;

public class QueueLinearDynamicArray<T> {
    private DynamicArray<T> elementData;
    private int front;
    private int rear;

    // Creates an empty dynamic queue.
    // Implementation note: This implementation uses a dynamic array internally.
    QueueLinearDynamicArray() {
        this.elementData = new DynamicArray<>();
        this.front = 0;
        this.rear = 0;
    }

    // Enqueues (add) the specified value at the front of the queue.
    public void enqueue(T value) {
        elementData.add(value);
        rear++;
    }

    // Dequeues (remove) the element at the front of the queue and return it.
    // Throws an exception if there is currently no element in the queue.
    public T dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Unable to dequeue more elements. The queue is empty!");
        }

        T toRemove = peek();
        elementData.removeAt(front);
        rear--;
        return toRemove;
    }

    // Returns the element at the front of the queue without removing it.
    public T peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("There are no elements in the queue.");
        }

        return elementData.get(front);
    }

    // Returns the size (number of elements) of the queue.
    public int size() {
        return rear;
    }

    // Returns true if there are no elements in the queue, otherwise false.
    public boolean isEmpty() {
        return front == rear;
    }

    // Returns a string representation of the object.
    @Override
    public String toString() {
        if (isEmpty()) {
            return "[]";
        }

        String string = "";
        for (int i = 0; i < rear; i++) {
            string += elementData.get(i) + ", ";
        }

        string = "[" + string.substring(0, string.length() - 2) + "]";
        return string;
    }
}
