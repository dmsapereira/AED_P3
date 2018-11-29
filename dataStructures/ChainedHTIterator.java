package dataStructures;

import java.io.Serializable;

public class ChainedHTIterator<K, V> implements Iterator<Entry<K, V>>, Serializable {
    /**
     * Serialization
     */
    static final long serialVersionUID = 0L;

    /**
     * Instance Variables
     */
    private Dictionary<K, V>[] table;
    private int currentDictionary;
    private Iterator<Entry<K, V>> itera;

    ChainedHTIterator(Dictionary<K, V>[] table) {
        this.table = table;
        this.currentDictionary = -1;
        this.itera = searchForNextDictionary();
    }

    /**
     * searches the entries in the table array until it finds a valid OrderedDoubleList. When it does, returns its Iterator.
     *
     * @return next valid OrderedDoubleList's Iterator. Null if none are valid.
     */
    private Iterator<Entry<K, V>> searchForNextDictionary() {
        this.currentDictionary++;
        if (this.currentDictionary == table.length)
            return null;
        else if (table[currentDictionary].isEmpty())
            return this.searchForNextDictionary();
        else
            return table[currentDictionary].iterator();
    }

    @Override
    public boolean hasNext() {
        if (itera == null)
            return false;
        else if (this.itera.hasNext())
            return true;
        else {
            this.itera = searchForNextDictionary();
            return this.itera != null;
        }
    }

    @Override
    public Entry<K, V> next() throws NoSuchElementException {
        if (!this.hasNext())
            throw new NoSuchElementException();
        else
            return this.itera.next();
    }

    @Override
    public void rewind() {
        this.currentDictionary = -1;
        this.itera = searchForNextDictionary();
    }
}
