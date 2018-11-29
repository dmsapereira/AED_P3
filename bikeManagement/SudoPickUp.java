package bikeManagement;

/**
 * Package private Interface for the PickUps. Contains the method PickDown which is the only one that is capable of altering object properties
 */

interface SudoPickUp extends PickUp {
    /**
     * @param park     <code>Park</code> where bike will be parked
     * @param duration amount of time the <code>PickUp</code> took
     * @pre: park!=null && duration>0
     * Completes the pickup, parking the <code>Bike</code> in the <code>Park</code> and registering the duration
     */
    void pickDown(SudoPark park, int duration);
}
