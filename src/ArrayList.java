// Written by tran1150
public class ArrayList<T extends Comparable<T>> implements List<T> {

    private static final int INIT_SIZE = 2;

    private T[] arr;
    private int capacity;
    private int size;
    private boolean isSorted;

    public ArrayList() {
        arr = (T[]) new Comparable[INIT_SIZE];
        capacity = INIT_SIZE;
        size = 0;
        isSorted = true;
    }

    @Override
    public boolean add(T element) {
        if (element == null)
            return false;

        checkAndExtendArray();
        arr[size] = element;
        int index = size;
        size++;
        checkSortAfterAdd(index);
        return true;
    }

    @Override
    public boolean add(int index, T element) {
        if (element == null || index < 0 || index >= size)
            return false;

        checkAndExtendArray();

        T lastValue = arr[index];
        for (int i = index + 1; i <= size; i++) {
            T data = lastValue;
            lastValue = arr[i];
            arr[i] = data;
        }
        arr[index] = element;
        size++;
        checkSortAfterAdd(index);
        return true;
    }

    @Override
    public void clear() {
        for (int i = 0; i < size; i++)
            arr[i] = null;
        arr = (T[]) new Comparable[INIT_SIZE];
        capacity = INIT_SIZE;
        size = 0;
        isSorted = true;
    }

    @Override
    public T get(int index) {
        if (!isValidIndex(index))
            return null;

        return arr[index];
    }

    @Override
    public int indexOf(T element) {
        if (element == null)
            return -1;

        if (isSorted) {
            // binary search
            int result = -1;
            int left = 0, right = size - 1;
            while (left <= right) {
                int middle = (left + right) / 2;
                int cmp = arr[middle].compareTo(element);
                if (cmp == 0) {
                    result = middle;
                    right = middle - 1;
                } else if (cmp > 0) {
                    right = middle - 1;
                } else {
                    left = middle + 1;
                }
            }
            return result;
        }

        for (int i = 0; i < size; i++) {
            if (arr[i].equals(element)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void sort() {
        for (int i = 0; i < size; i++) {
            for (int j = i + 1; j < size; j++) {
                if (arr[i].compareTo(arr[j]) > 0) {
                    T value = arr[i];
                    arr[i] = arr[j];
                    arr[j] = value;
                }
            }
        }
        isSorted = true;
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= size)
            return null;

        T result = arr[index];
        for (int i = index; i < size - 1; i++) {
            arr[i] = arr[i + 1];
        }
        arr[size - 1] = null;
        size--;
        if (!isSorted) {
            checkSort();
        }
        return result;
    }

    @Override
    public void equalTo(T element) {
        if (element == null)
            return;

        int index = 0;
        for (int i = 0; i < size; i++) {
            if (arr[i].equals(element)) {
                arr[index++] = arr[i];
            }
        }
        for (int i = index; i < size; i++) {
            arr[i] = null;
        }
        size = index;
        if (!isSorted) {
            checkSort();
        }
    }

    @Override
    public void reverse() {
        for (int i = 0; i < size / 2; i++) {
            T value = arr[i];
            arr[i] = arr[size - i - 1];
            arr[size - i - 1] = value;
        }
        checkSort();
    }

    @Override
    public void intersect(List<T> otherList) {

    }

    @Override
    public T getMin() {

    }

    @Override
    public T getMax() {

    }

    @Override
    public boolean isSorted() {
        return isSorted;
    }

    @Override
    public String toString() {
        String result = "";
        for (int i = 0; i < size; i++) {
            result += arr[i].toString() + "\n";
        }
        return result;
    }

    private boolean isNeedExtend() {
        return size >= capacity;
    }

    private boolean isValidIndex(int index) {
        return index >= 0 && index < size;
    }


    // expand array if the current size is not enough to insert new element - O(n)
    private void checkAndExtendArray() {
        if (isNeedExtend()) {
            capacity *= 2;
            T[] copy = (T[]) new Comparable[capacity];
            for (int i = 0; i < size; i++) {
                copy[i] = arr[i];
            }
            arr = copy;
        }
    }

    // check sorted array after add new element at index - O(1)
    private void checkSortAfterAdd(int indexAdded) {
        if (indexAdded < 0)
            return;

        if (indexAdded == 0) {
            if (isSorted && size > 1) {
                if (arr[indexAdded].compareTo(arr[indexAdded + 1]) > 0) {
                    isSorted = false;
                }
            }
        } else if (isSorted) {
            if (arr[indexAdded].compareTo(arr[indexAdded - 1]) < 0) {
                isSorted = false;
            }
            if (indexAdded + 1 < size && arr[indexAdded + 1] != null
                    && arr[indexAdded].compareTo(arr[indexAdded + 1]) > 0) {
                isSorted = false;
            }
        }

    }

    // check sorted array - O(n)
    private void checkSort() {
        isSorted = true;
        for (int i = 1; i < size; i++) {
            if (arr[i].compareTo(arr[i - 1]) < 0) {
                isSorted = false;
            }
        }
    }
}
