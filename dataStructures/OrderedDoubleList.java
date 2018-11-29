package dataStructures;

public class OrderedDoubleList<K extends Comparable<K>, V> implements OrderedDictionary<K, V> {
    static final long serialVersionUID = 0L;
    protected DListNode<Entry<K, V>> head;
    protected DListNode<Entry<K, V>> tail;
    private int size;


    public OrderedDoubleList() {
        head = null;
        tail = null;
        this.size = 0;
    }

    /**
     * @param node node to remove
     * @pre: this.size!=0 && node!=null
     */
    private void removeNode(DListNode<Entry<K, V>> node) {
        if (this.size == 1) {
            this.head = null;
            this.tail = null;
        } else if (this.head.equals(node)) {
            this.head = node.getNext();
            this.head.setPrevious(null);
        } else if (this.tail.equals(node)) {
            this.tail = node.getPrevious();
            this.tail.setNext(null);
        } else {
            node.getPrevious().setNext(node.getNext());
            node.getNext().setPrevious(node.getPrevious());
        }
        this.size--;
    }

    /**
     * @param newNode node to insert
     * @param current existing node that follow the new one
     * @pre: current!=null && newNode!=null
     * inserts a new node before a previous existing one
     */
    private void insertNodeBefore(DListNode<Entry<K, V>> newNode, DListNode<Entry<K, V>> current) {
        if (current.getPrevious() != null) {
            current.getPrevious().setNext(newNode);
            newNode.setPrevious(current.getPrevious());
        } else
            this.head = newNode;
        newNode.setNext(current);
        current.setPrevious(newNode);
    }

    /**
     * searches for a node
     *
     * @param key node's entry's key
     * @return Node with the requested key, null if none is found
     */
    private DListNode<Entry<K, V>> searchForNode(K key) {
        if (this.size == 0)
            return null;
        DListNode<Entry<K, V>> current = this.head;
        while (current != null) {
            if (current.getElement().getKey().equals(key))
                return current;
            current = current.getNext();
        }
        return null;
    }

    @Override
    public Entry<K, V> minEntry() throws EmptyDictionaryException {
        if (this.isEmpty())
            throw new EmptyDictionaryException();
        return this.head.getElement();
    }

    @Override
    public Entry<K, V> maxEntry() throws EmptyDictionaryException {
        if (this.isEmpty())
            throw new EmptyDictionaryException();
        return this.tail.getElement();
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public V find(K key) {
        DListNode<Entry<K, V>> current = this.head;
        for (int i = 0; i < this.size; i++) {
            if (current.getElement().getKey().equals(key))
                return current.getElement().getValue();
            current = current.getNext();
        }
        return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public V insert(K key, V value) {
        DListNode<Entry<K, V>> newNode = new DListNode(new EntryClass<>(key, value));
        if (this.size == 0) {
            this.head = newNode;
            this.tail = newNode;
        } else if (this.find(key) == null) {
            DListNode<Entry<K, V>> current = this.head;
            for (int i = 0; i < this.size; i++) {
                if (newNode.getElement().compareTo(current.getElement()) < 0) {
                    this.insertNodeBefore(newNode, current);
                    this.size++;
                    return null;
                }
                current = current.getNext();
            }
            this.tail.setNext(newNode);
            newNode.setPrevious(this.tail);
            this.tail = newNode;
        } else
            return this.searchForNode(key).getElement().setValue(value);
        this.size++;
        return null;
    }

    @Override
    public V remove(K key) {
        if (this.size == 0 || this.find(key) == null)
            return null;
        else {
            DListNode<Entry<K, V>> node = searchForNode(key);
            this.removeNode(node);
            return node.getElement().getValue();
        }
    }

    @Override
    public Iterator<Entry<K, V>> iterator() {
        return new DoublyLLIterator<>(this.head, this.tail);
    }
}
