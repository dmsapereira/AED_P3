package dataStructures;

public class BSTKeyOrderIterator<K extends Comparable<K>, V> implements Iterator<Entry<K, V>> {

    static final long serialVersionUID = 0L;
    private BSTNode<K, V> root;
    private Stack<BSTNode<K, V>> stack; //Stack that will serve as a path for iterating the nodes

    BSTKeyOrderIterator(BSTNode<K, V> root) {
        this.root = root;
        BSTNode<K, V> current = root;
        stack = new StackInList<>(); //stack serves as a PathStep class
        //Pops into the stack every node that is in the path to the minimum Entry
        while (current != null) {
            stack.push(current);
            current = current.getLeft();
        }
    }

    @Override
    public boolean hasNext() {
        return !this.stack.isEmpty();
    }

    @Override
    public Entry<K, V> next() throws NoSuchElementException {
        BSTNode<K, V> current = this.stack.pop();
        BSTNode<K, V> result = current;
        if (current.getRight() != null) {
            current = current.getRight();
            while (current != null) {
                //pushes the path to the minimum Entry on this subtree onto the stack
                stack.push(current);
                current = current.getLeft();
            }
        }
        return result.getEntry();
    }

    @Override
    public void rewind() {
        BSTNode<K, V> current = this.root;
        while (!stack.isEmpty())
            this.stack.pop();
        while (current != null) {
            this.stack.push(current);
            current = current.getLeft();
        }
    }
}
