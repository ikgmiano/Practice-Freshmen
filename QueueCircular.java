import java.util.NoSuchElementException;

public class QueueCircular<T> {
    private static final int DEFAULT_CAPACITY = 10;
    
    private Object[] elementData;
    private int capacity;
    private int numOfElements;
    private int front;
    private int rear;

    // Note: The front and rear starts at index 0, hence the rear indicates the NEXT AVAILABLE slot for new elements.
    // This means that when the circular buffer is full, the front and the rear are at the same index.

    // Default constructor
    QueueCircular() {
        this(DEFAULT_CAPACITY);
    }

    // Creates an empty circular queue with its capacity set to the specified capacity.
    QueueCircular(int capacity) {
        this.capacity = capacity;
        this.elementData = new Object[capacity];
        this.numOfElements = 0;
        this.front = 0;
        this.rear = 0;
    }

    // Enqueues (add) the specified value at the front of the queue.
    public void enqueue(T value) {
        if (isFull()) {
            grow();
        }

        elementData[rear] = value;
        rear = (rear + 1) % capacity;
        numOfElements++;
    }

    // Dequeues (remove) the element at the front of the queue and return it.
    // Throws an exception if there is currently no element in the queue.
    public T dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        T toDequeue = peek();
        front = (front + 1) % capacity;
        numOfElements--;

        if (numOfElements == (capacity / 3)) {
            shrink();
        }

        return toDequeue;
    }

    // Returns the element at the front of the queue without removing it.
    @SuppressWarnings("unchecked")
    public T peek() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        return (T) elementData[front];
    }

    // Returns the size (number of elements) of the queue.
    public int size() {
        return numOfElements;
    }

    // Returns true if there are no elements in the queue, otherwise false.
    public boolean isEmpty() {
        return numOfElements == 0;
    }

    // Helper method. Returns true if there are no more slots in the queue, otherwise false.
    public boolean isFull() {
        return numOfElements == capacity;
    }

    // Doubles the capacity of the queue to accomodate more elements.
    private void grow() {
        capacity *= 2;
        Object[] temp = new Object[capacity];

        int i = 0; // index counter for temp
        int j = front; // index counter for elementData

        // Copy the elements of elementData to temp
        do {
            temp[i] = elementData[j];
            i++;
            j = (j + 1) % elementData.length;
        } while (j != rear);

        // Reset the front and rear pointer
        front = 0;
        rear = numOfElements;
        elementData = temp;
    }

    // Halve the capacity of the queue to save memory space.
    private void shrink() {
        capacity /= 2;
        Object[] temp = new Object[capacity];

        int i = 0; // index counter for temp
        int j = front; // index counter for elementData

        // Copy the elements of elementData to temp
        do {
            temp[i] = elementData[j];
            i++;
            j = (j + 1) % elementData.length;
        } while (i != rear);

        // Reset the front and rear pointer
        front = 0;
        rear = numOfElements;
        elementData = temp;
    }

    // Returns a string representation of the object.
    @Override
    public String toString() {
        if (isEmpty()) {
            return "[]";
        }

        String string = "";
        int i = front;

        // It is important that we use a do_while instead of a typical while since front and rear is equal
        // in the case of full array since the loop will not run even though there are elements.
        do {
            string += elementData[i] + ", ";
            i = (i + 1) % elementData.length;
        } while (i != rear);

        string = "[" + string.substring(0, string.length() - 2) + "]";
        return string;
    }
}
