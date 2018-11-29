package bikeManagement;


import java.io.Serializable;

public interface Park extends Serializable {

    /**
     * Returns park's unique ID
     *
     * @return <code>Park</code> ID
     */
    String getParkId();

    /**
     * Returns the park's name
     *
     * @return <code>Park</code>'s name
     */
    String getName();

    /**
     * Returns the park's address
     *
     * @return <code>Park</code>'s address
     */
    String getAddress();

    /**
     * Returns the number of pickups
     *
     * @return number of pickups
     */
    int getPickups();

    /**
     * @return number of parked bikes
     */
    int getParkedBikes();




}
