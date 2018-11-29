package bikeManagement;

import dataStructures.Iterator;
import bikeManagement.exceptions.user.OngoingFirstPickupException;
import bikeManagement.exceptions.user.VoidPickupsException;

import java.io.Serializable;

public interface User extends Serializable {

    /**
     * Returns the <code>User</code>'s unique ID
     *
     * @return <code>User</code>'s unique ID
     */
    String getUserId();

    /**
     * @return <code>User</code>'s name
     */
    String getName();

    /**
     * @return <code>User</code>'s NIF
     */
    String getNIF();

    /**
     * @return <code>User</code>'s address
     */
    String getAddress();

    /**
     * @return <code>User</code>'s email
     */
    String getEmail();

    /**
     * @return <code>User</code>'s phone number
     */
    String getPhone();

    /**
     * Returns the number of delay points the <code>User</code> has
     *
     * @return number of delay points
     */
    int getPoints();

    /**
     * Returns the <code>User</code>'s balance
     *
     * @return <code>User</code> balance
     */
    int getBalance();

    /**
     * Returns the <code>PickUp</code> the <code>User</code> is currently on
     *
     * @return <code>PickUP</code> user is on, null if he's doing none
     */
    PickUp getCurrentPickup();


    /**
     * Returns an <code>Iterator</code> of this <code>User</code>'s successful <code>PickUp</code>s
     *
     * @return <code>Iterator</code> for the <code>PickUp</code>s
     * @throws OngoingFirstPickupException if <code>User</code> hasn't called <code>pickDown()</code> after calling <code>pickUp(...)</code> for the first time
     * @throws VoidPickupsException        if  no <code>PickUp</code>s have been completed yet
     */
    Iterator<PickUp> getPickUps();


}
