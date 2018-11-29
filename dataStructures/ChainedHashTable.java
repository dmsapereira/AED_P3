package dataStructures;

/**
 * Chained Hash table implementation
 *
 * @param <K> Generic Key, must extend comparable
 * @param <V> Generic Value
 * @author AED Team
 * @version 1.0
 */

public class ChainedHashTable<K extends Comparable<K>, V> extends HashTable<K, V> {
    /**
     * Serial Version UID of the Class.
     */
    static final long serialVersionUID = 0L;

    /**
     * The array of dictionaries.
     */
    protected Dictionary<K, V>[] table;

    /**
     * Constructor of an empty chained hash table, with the specified initial
     * capacity. Each position of the array is initialized to a new ordered list
     * maxSize is initialized to the capacity.
     *
     * @param capacity defines the table capacity.
     */
    @SuppressWarnings("unchecked")
     public ChainedHashTable(int capacity) {
        int arraySize = HashTable.nextPrime((int) (1.1 * capacity));
        // Compiler gives a warning.
        table = (Dictionary<K, V>[]) new Dictionary[arraySize];
        for (int i = 0; i < arraySize; i++)
            table[i] = new OrderedDoubleList<>();
        maxSize = capacity;
        currentSize = 0;
    }

    public ChainedHashTable() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * Returns the hash value of the specified key.
     *
     * @param key to be encoded
     * @return hash value of the specified key
     */
    protected int hash(K key) {
        return Math.abs(key.hashCode()) % table.length;
    }

    @Override
    public V find(K key) {
        return table[this.hash(key)].find(key);
    }

    @Override
    public V insert(K key, V value) {
        V result;
        if (this.isFull())
            this.rehash();
        result = table[this.hash(key)].insert(key, value);
        if (result == null)
            this.currentSize++;
        return result;
    }

    @SuppressWarnings("unchecked")
    private void rehash() {
        this.maxSize *= 2;
        Dictionary<K, V> oldTable[] = this.table;
        int arraySize = HashTable.nextPrime((int) (1.1 * this.maxSize));
        this.table = (Dictionary<K, V>[]) new Dictionary[arraySize];
        for (int i = 0; i < arraySize; i++) {
            table[i] = new OrderedDoubleList<>();
        }
        for (Dictionary<K, V> dictionary : oldTable) {
            Iterator<Entry<K, V>> itera = dictionary.iterator();
            while (itera.hasNext()) {
                Entry<K, V> current = itera.next();
                this.table[this.hash(current.getKey())].insert(current.getKey(), current.getValue());
            }
        }
    }

    @Override
    public V remove(K key) {
        V result = table[this.hash(key)].remove(key);
        if (result != null)
            this.currentSize--;
        return result;
    }

    @Override
    public Iterator<Entry<K, V>> iterator() {
        return new ChainedHTIterator<>(this.table);
    }
}
