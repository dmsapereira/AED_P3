package dataStructures;

public class EntryClass<K extends Comparable<K>,V> implements Entry<K,V>,Comparable<Entry<K,V>> {
    private K key;
    private V value;

    public EntryClass(K key, V value){
        this.key=key;
        this.value=value;
    }
    @Override
    public K getKey() {
        return this.key;
    }

    @Override
    public V getValue() {
        return this.value;
    }

    @Override
    public V setValue(V value) {
        V oldValue=this.value;
        this.value=value;
        return oldValue;
    }

    @Override
    public K setKey(K key) {
        K oldKey=this.key;
        this.key=key;
        return oldKey;
    }

    @Override
    public int compareTo(Entry<K,V> element) {
        return 1;
    }
}
