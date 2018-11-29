package bikeManagement;

/**
 * Package private Interface for the Park. Contains methods that change object properties
 */
interface SudoPark extends Park {
    /**
     * @param bike <code>Bike</code> to be parked
     * @pre: bike!=null
     * Parks a bike in the park
     */
    void parkBike(Bike bike);

    /**
     * Deletes the <code>Bike</code> from the Park
     */
    void deleteBike(String idBike);

    /**
     * Picks up the <code>Bike</code> that's currently in the park
     */
    void pickUp(String idBike);

    /**
     * Returns the <code>Bike</code> that's parked
     *
     * @return parked <code>Bike</code>
     */
    boolean isBikeParked(String idBike);


}
