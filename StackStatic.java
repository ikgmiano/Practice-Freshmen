import java.util.EmptyStackException;

public class StackStatic<T> {
    private static final int DEFAULT_CAPACITY = 10;

    private Object[] elementData;
    private int capacity;
    private int top;

    // Default constructor.
    StackStatic() {
        this(DEFAULT_CAPACITY);
    }
    
    // Creates an empty stack with its capacity set to the specified capacity.
    StackStatic(int capacity) {
        this.capacity = capacity;
        this.elementData = new Object[capacity];
        this.top = -1;
    }

    // Pushes (add) the specified value at the top of the stack.
    public void push(T value) {
        if (isFull()) {
            throw new IllegalStateException("Stack is full");
        }

        top++;
        elementData[top] = value;
    }

    // Pops (remove) the element at the top of the stack and return it. 
    // Throws an exception if there is currently no element in the stack.
    public T pop() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }

        T toRemove = peek();
        top--;
        return toRemove;
    }

    // Returns the element at the top of the stack without removing it.
    @SuppressWarnings("unchecked")
    public T peek() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        
        return (T) elementData[top];
    }

    // Returns the size (number of elements) of the stack.
    public int size() {
        return top + 1;
    }

    // Returns true if there is no element in the stack, otherwise false.
    public boolean isEmpty() {
        return top == -1;
    }

    // Helpter method. Returns true if the number of elements is equal to the capacity.
    private boolean isFull() {
        return top == (capacity - 1);
    }

    // Returns a string representation of the object.
    @Override
    public String toString() {
        if (isEmpty()) {
            return "[]";
        }

        String string = "";
        for (int i = 0; i <= top; i++) {
            string += elementData[i] + ", ";
        }

        string = "[" + string.substring(0, string.length() - 2) + "]";
        return string;
    }
}
