// Written by tran1150
public class LinkedList<T extends Comparable<T>> implements List<T> {

    private Node<T> head;
    private int size;
    private boolean isSorted;

    @Override
    public boolean add(T element) {
        if (element == null)
            return false;

        Node<T> newNode = new Node<>(element);
        size++;
        if (head == null) {
            head = newNode;
            checkSorted();
            return true;
        }
        Node<T> node = head;
        while (node.getNext() != null) {
            node = node.getNext();
        }
        node.setNext(newNode);
        checkSorted();
        return true;
    }

    @Override
    public boolean add(int index, T element) {
        if (index >= size || index < 0)
            return false;

        if (element == null)
            return false;

        size++;
        Node<T> newNode = new Node<>(element);
        if (index == 0) {
            newNode.setNext(head);
            head = newNode;
            checkSorted();
            return true;
        }

        int count = 0;
        Node<T> node = head;
        while (node != null) {
            if (count == index - 1) {
                newNode.setNext(node.getNext());
                node.setNext(newNode);
            }
            node = node.getNext();
            count++;
        }
        checkSorted();
        return true;
    }

    @Override
    public void clear() {
        head = null;
        size = 0;
        isSorted = true;
    }

    @Override
    public T get(int index) {
        if (index >= size || index < 0)
            return null;

        int count = 0;
        Node<T> node = head;
        while (node != null) {
            if (count == index) {
                return node.getData();
            }
            node = node.getNext();
            count++;
        }
        return null;
    }

    @Override
    public int indexOf(T element) {
        if(element == null)
            return -1;

        int index = 0;
        Node<T> node = head;
        while (node != null) {
            if (node.getData().equals(element)) {
                return index;
            }
            node = node.getNext();
            index++;
        }
        return -1;
    }

    @Override
    public boolean isEmpty() {
        return size == 0 || head == null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void sort() {
        if (isSorted)
            return;

        // Node current will point to head
        Node<T> current = head, index = null;

        T temp;

        if (head == null) {
            return;
        } else {
            while (current != null) {
                // Node index will point to node next to
                // current
                index = current.getNext();

                while (index != null) {
                    // If current node's data is greater
                    // than index's node data, swap the data
                    // between them
                    if (current.getData().compareTo(index.getData()) > 0) {
                        temp = current.getData();
                        current.setData(index.getData());
                        index.setData(temp);
                    }
                    index = index.getNext();
                }
                current = current.getNext();
            }
        }

        isSorted = true;
    }

    @Override
    public T remove(int index) {
        if (index >= size || index < 0)
            return null;

        size--;

        if (index == 0) {
            T data = head.getData();
            head = head.getNext();
            if (!isSorted) {
                checkSorted();
            }
            return data;
        }

        int count = 0;
        Node<T> node = head;
        while (node != null) {
            if (count == index - 1) {
                Node<T> removed = node.getNext();
                node.setNext(node.getNext().getNext());
                if (!isSorted) {
                    checkSorted();
                }
                return removed.getData();
            }
            node = node.getNext();
            count++;
        }
        return null;
    }

    @Override
    public void equalTo(T element) {
        if (element == null)
            return;

        while (head != null && !head.getData().equals(element)) {
            size--;
            head = head.getNext();
        }

        if (head == null) {
            if (!isSorted) {
                checkSorted();
            }
            return;
        }

        Node<T> node = head;
        while (node.getNext() != null) {
            if (!node.getNext().getData().equals(element)) {
                size--;
                node.setNext(node.getNext().getNext());
            } else
                node = node.getNext();
        }

        if (!isSorted) {
            checkSorted();
        }
    }

    @Override
    public void reverse() {
        Node<T> previous = null;
        Node<T> current = head;
        Node<T> next;
        while (current != null) {
            next = current.getNext();
            current.setNext(previous);
            previous = current;
            current = next;
        }
        head = previous;
        checkSorted();
    }

    @Override
    public void intersect(List<T> otherList) {
        
    }

    @Override
    public T getMin() {
        return null;
    }

    @Override
    public T getMax() {
        return null;
    }


    @Override
    public String toString() {
        String result = "";
        Node<T> node = head;
        while (node.getNext() != null) {
            result += node.getData().toString() + "\n";
            node = node.getNext();
        }
        return result;
    }

    @Override
    public boolean isSorted() {
        return isSorted;
    }

    private void checkSorted() {
        isSorted = true;
        Node<T> node = head;
        while (node != null && node.getNext() != null) {
            if (node.getData().compareTo((T) node.getNext().getData()) > 0) {
                isSorted = false;
            }
            node = node.getNext();
        }
    }
}
