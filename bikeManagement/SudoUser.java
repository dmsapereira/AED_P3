package bikeManagement;

/**
 * Package private Interface for the User. Contains methods that are capable of changing object properties
 */
public interface SudoUser extends User {
    /**
     * Adds an amount to the <code>User</code>'s balance
     *
     * @param value amount to be added
     */
    void addBalance(int value);

    /**
     * @param pickup <code>PickUp</code> to be registered
     * @pre: pickup!=null
     * Executes a <code>PickUp</code>
     */
    void pickUp(PickUp pickup);

    /**
     * @param delayCounter number of delays
     * @pre: getCurrentPickUp()!=null
     * Finalizes the <code>PickUp</code> registering it to the list of completed <code>PickUp</code>s
     */
    void pickDown(int delayCounter);
}
