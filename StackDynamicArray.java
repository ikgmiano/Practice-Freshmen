import java.util.EmptyStackException;

public class StackDynamicArray<T> {
    private DynamicArray<T> elementData;
    private int top;

    // Creates an empty stack stack.
    // Implementation note: The internal data structure used to implement this
    // stack is a dynamic array.
    StackDynamicArray() {
        this.elementData = new DynamicArray<>();
        this.top = -1;
    }

    // Pushes (add) the specified value at the top of the stack.
    public void push(T value) {
        top++;
        elementData.add(value);
    }

    // Pops (remove) the element at the top of the stack and return it. 
    // Throws an exception if there is currently no element in the stack.
    public T pop() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }

        T toRemove = peek();
        elementData.removeAt(top);
        top--;
        return toRemove;
    }

    // Returns the element at the top of the stack without removing it.
    public T peek() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        
        return elementData.get(top);
    }

    // Returns the size (number of elements) of the stack.
    public int size() {
        return top + 1;
    }

    // Returns true if there is no element in the stack, otherwise false.
    public boolean isEmpty() {
        return top == -1;
    }

    // Returns a string representation of the object.
    @Override
    public String toString() {
        if (isEmpty()) {
            return "[]";
        }

        String string = "";
        for (int i = 0; i <= top; i++) {
            string += elementData.get(i) + ", ";
        }

        string = "[" + string.substring(0, string.length() - 2) + "]";
        return string;
    }
}
