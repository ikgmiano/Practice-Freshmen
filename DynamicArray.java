import java.util.NoSuchElementException;

public class DynamicArray<T> {
    private static final int DEFAULT_CAPACITY = 10;

    private Object[] elementData;
    private int capacity;
    private int size;

    // Constructs an empty dynamic array object with its capacity set to DEFAULT_CAPACITY.
    DynamicArray() {
        this.capacity = DEFAULT_CAPACITY;
        this.elementData = new Object[capacity];
        this.size = 0; // this isn't required, I just initialized it for completeness :)
    }

    // Constructs an empty dynamic array object with its capacity set to the specified capacity.
    DynamicArray(int capacity) {
        this.capacity = capacity;
        this.elementData = new Object[capacity];
        this.size = 0; // this isn't required also, I just initialized it for completeness
    }

    // Adds the specified value at the end of the list.
    public void add(T value) {
        // increases capacity if there is no more slot
        if (isFull()) {
            grow(); 
        }

        // assign the specified value to the next available slot (pointed to by size)
        elementData[size] = value;
        size++;
    }

    // Adds the specified value at the specified index.
    public void add(int index, T value) {
        // checks if the index is within the valid range, throw exception if not
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(String.format("Index %d is out of bound for length %d", index, size));
        }

        // increases capacity if there is no more slot
        if (isFull()) {
            grow(); 
        }

        // shifts all elements to the right of the specified index by one position rightward
        for (int i = (size - 1); i >= index; i--) {
            elementData[i + 1] = elementData[i];
        }

        // then assign the value at the given index, which should be "empty" by now 
        // (it's not technically empty, but we'll be overwritting it anyway with the new value)
        elementData[index] = value;
        size++;
    }

    // Removes the first instance of the specified value and return it, otherwise return null.
    public T remove(T value) {
        if (isEmpty()) {
            throw new NoSuchElementException(); // there's nothing to remove if array is empty
        }

        // iterate the array until the current index has the value we are looking for
        for (int i = 0; i < size; i++) {
            if (elementData[i].equals(value)) {
                // we can just call removeAt() so we don't have to reimplement the deleting and shifting of elements (and resizing if applicable)
                return removeAt(i); 
            }
        }

        return null;
    }

    // Removes the value at the specified index and return it.
    public T removeAt(int index) {
        // checks if the index is within the valid range, throw exception if not
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(String.format("Index %d is out of bound for length %d", index, size));
        }

        // save the value at the index so we can return it later (because it will be overwritten after shifting the elements)
        @SuppressWarnings("unchecked")
        T toRemove = (T) elementData[index];

        // shifts all elements to the right of the specified index by one position leftward
        for (int i = index; i < (size - 1); i++) {
            elementData[i] = elementData[i + 1];
        }
        size--;

        // decreases the capacity if size (number of elements) is just a third of the capacity to save memory
        if (size == (capacity / 3)) {
            shrink();
        }

        return toRemove;
    }

    // Returns the value at the specified index.
    @SuppressWarnings("unchecked")
    public T get(int index) {
        // checks if the index is within the valid range, throw exception if not
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(String.format("Index %d is out of bound for length %d", index, size));
        }

        return (T) elementData[index];
    }

    // Replaces the value of the specified index with the specified value.
    public void replace(int index, T value) {
        // checks if the index is within the valid range, throw exception if not
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(String.format("Index %d is out of bound for length %d", index, size));
        }

        elementData[index] = value;
    }

    // Returns the size of the array or the number of elements (can be confused with capacity).
    public int size() {
        return size;
    }

    // Returns true if there is no element, false otherwise.
    public boolean isEmpty() {
        return size == 0;
    }

    // Helper method used when checking if increasing the capacity is necessary before adding a new element.
    private boolean isFull() {
        return size == capacity;
    }

    // Increases the internal capacity of the array to accomodate more elements (if full).
    private void grow() { 
        capacity *= 2;
        Object[] temp = new Object[capacity];

        // copies all elements at elementData to temp
        for (int i = 0; i < size; i++) {
            temp[i] = elementData[i];
        }

        // reassign elemetData to temp that has double the capacity of elementData previously
        elementData = temp;
    } 

    // Decreases (by half) the internal capacity of the array to save memory.
    private void shrink() {
        capacity /= 2;
        Object[] temp = new Object[capacity];

        // copies all elements at elementData to temp
        for (int i = 0; i < size; i++) {
            temp[i] = elementData[i];
        }

        // reassign elemetData to temp that has half the capacity of elementData previously
        elementData = temp;
    }

    // Returns a string representation of the object.
    @Override
    public String toString() {
        if (isEmpty()) {
            return "[]";
        }

        String string = "";

        for (int i = 0; i < size; i++) {
            string += elementData[i] + ", ";
        }
        
        string = "[" + string.substring(0, string.length() - 2) + "]";
        return string;
    }
}
