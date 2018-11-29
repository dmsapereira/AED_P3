package bikeManagement;
import dataStructures.Entry;
import dataStructures.Iterator;
import dataStructures.List;
import bikeManagement.exceptions.*;
import bikeManagement.exceptions.bike.*;
import bikeManagement.exceptions.park.DuplicateParkException;
import bikeManagement.exceptions.park.VoidParkException;
import bikeManagement.exceptions.user.*;

import java.io.Serializable;

public interface BikeManagement extends Serializable {

    /**
     * Adds a new <code>User</code>
     * @param idUser <code>User</code>'s UNIQUE ID
     * @param nif <code>User</code> NIF
     * @param email <code>User</code> email
     * @param phone <code>User</code> phone
     * @param name <code>User</code> name
     * @param address <code>User</code> address
     * @throws DuplicateUserException if there's already a <code>User</code> with the same ID
     */
    void addUser(String idUser, String nif, String email, String phone, String name, String address) throws DuplicateUserException;

    /**
     * Removes a <code>User</code>
     * @param idUser ID of the <code>User</code> to be removed
     * @throws VoidUserException if no <code>User</code> is found with the ID
     * @throws VeteranUserException if <code>User</code> has ever started a <code>PickUp</code>
     */
    void removeUser(String idUser) throws VoidUserException, VeteranUserException;

    /**
     * Adds a new <code>Park</code>
     * @param idPark <code>Park</code> ID
     * @param name <code>Park</code> name
     * @param address <code>Park</code> address
     * @throws DuplicateParkException if there's an existent <code>Park</code> with the same ID
     */
    void addPark(String idPark, String name, String address) throws DuplicateParkException;

    /**
     * Adds a new <code>Bike</code>
     * @param idBike <code>Bike</code> ID
     * @param idPark <code>Park</code> where the <code>Bike</code> will be parked's ID
     * @param plate <code>Bike</code> license plate
     * @throws DuplicateBikeException if there's an existing <code>Bike</code> with the same ID
     * @throws VoidParkException if there's no <code>Park</code> with the provided ID
     */
    void addBike(String idBike, String idPark, String plate) throws DuplicateBikeException, VoidParkException;

    /**
     * Deletes a  <code>Bike</code>
     * @param idBike ID of the <code>Bike</code> to be deleted
     * @throws UsedBikeException if the <code>Bike</code> has already been used
     * @throws VoidBikeException if the <code>Bike</code> doesn't exist
     */
    void removeBike(String idBike) throws UsedBikeException, VoidBikeException;

    /**
     * Returns the <code>Park</code>
     * @param parkId <code>Park</code> ID
     * @return <code>Park</code> with the ID
     * @throws VoidParkException if the <code>Park</code> doesn't exist
     */
    Park getPark(String parkId) throws VoidParkException;

    /**
     * Picks up a <code>Bike</code>
     * @param idBike <code>Bike</code>'s ID
     * @param idUser <code>User</code>'s ID
     * @throws VoidBikeException if the <code>Bike</code> doesn't exist
     * @throws MovingBikeException if the <code>Bike</code> has already been picked up
     * @throws VoidUserException if the <code>User</code> doesn't exist
     * @throws BusyUserException if the <code>User</code> is already on a <code>PickUp</code>
     * @throws InsufficientBalanceException if the <code>User</code> doesn't have enough money for a <code>PickUp</code>
     */
    void pickUp(String idBike, String idUser) throws VoidBikeException, MovingBikeException, VoidUserException, BusyUserException, InsufficientBalanceException;

    /**
     * Delivers the <code>Bike</code>
     * @param idBike <code>Bike</code>'s ID
     * @param idPark <code>Park</code> where <code>Bike</code> will be parked's ID
     * @param duration duration of the <code>PickUp</code>
     * @return <code>User</code> responsible for the <code>PickUp</code>
     * @throws VoidBikeException if the <code>Bike</code> doesn't exist
     * @throws StoppedBikeException if the <code>Bike</code> is stopped
     * @throws VoidParkException if the <code>Park</code> doesn't exist
     * @throws InvalidDataException if duration<1
     */
    User pickDown(String idBike, String idPark, int duration) throws VoidBikeException, StoppedBikeException, VoidParkException, InvalidDataException;

    /**
     * Adds an amount to a <code>User</code>'s balance
     * @param idUser <code>User</code> ID
     * @param amount amount to be added
     * @return <code>User</code> that's been affected
     * @throws VoidUserException if the <code>User</code> doesn't exist
     * @throws InvalidDataException if amount<1
     */
    User chargeUser(String idUser, int amount) throws VoidUserException, InvalidDataException;

    /**
     * Returns an <code>Iterator</code> for the <code>PickUp</code>s of a <code>Bike</code>
     * @param idBike <code>Bike</code> ID
     * @return <code>Iterator</code> for the <code>Bike</code>'s <code>PickUp</code>s
     * @throws VoidBikeException if the <code>Bike</code> doesn't exist
     * @throws UnusedBikeException if the <code>Bike</code> has no registered <code>PickUp</code>s
     * @throws OngoingFirstPickupException if the <code>Bike</code> has already been picked up, but for the first time
     */
    Iterator<PickUp> listBikePickups(String idBike) throws VoidBikeException, UnusedBikeException, OngoingFirstPickupException;

    /**
     * Returns an <code>Iterator</code> for the <code>PickUp</code>s of a <code>User</code>
     * @param idUser <code>User</code> ID
     * @return <code>Iterator</code> for the <code>User</code>'s <code>PickUp</code>s
     * @throws VoidUserException if the <code>User</code> doesn't exist
     * @throws NewUserException if the <code>User</code> has never picked up a <code>Bike</code>
     * @throws OngoingFirstPickupException if the <code>User</code> is still on his first <code>PickUp</code>
     */
    Iterator<PickUp> listUserPickups(String idUser) throws VoidUserException, NewUserException, OngoingFirstPickupException;

    /**
     *Checks if a <code>Bike</code> is parked/stopped in a certain <code>Park</code> or not
     * @param idBike <code>Bike</code> ID
     * @param idPark <code>Park</code> ID
     * @return <code>true</code> if it's parked in the <code>Park</code>, <code>false</code> otherwise
     * @throws VoidBikeException if the <code>Bike</code> doesn't exist
     * @throws VoidParkException if the <code>Park</code> doesn't exist
     */
    boolean parkedBike(String idBike, String idPark) throws VoidBikeException, VoidParkException;

    /**
     * Returns an Iterator for the most used <code>Parks</code>
     * @return Iterator for most used <code>Parks</code>
     */
    Iterator<Entry<String,Park>> favoriteParks();

    /**
     * @param userId <code>User</code> ID
     * @return <code>User</code> with the given ID
     * @throws VoidUserException if the <code>User</code> doesn't exist
     */
    User getUser(String userId) throws VoidUserException;

    /**
     * Lists delays, ordered from highest to lowest points
     * @return Iterator for the delays
     */
    Iterator<Entry<ReverseInt, List<User>>> getDelays();
}
