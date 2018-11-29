package bikeManagement;

import java.io.Serializable;

/**
 * Redundant int class, with the exception that ir sorts Integers in reverse. Used for the ListDelayed method
 */
public class ReverseInt implements Comparable<ReverseInt>, Serializable {
    static final long serialVersionUID = 0L;
    private int value;

    ReverseInt(int value) {
        this.value = value;

    }

    public int getValue() {
        return this.value;
    }

    @Override
    public int compareTo(ReverseInt o) {
        return o.getValue() - this.value;
    }
}
