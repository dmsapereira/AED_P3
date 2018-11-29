package bikeManagement;

import dataStructures.*;
import bikeManagement.exceptions.*;
import bikeManagement.exceptions.bike.*;
import bikeManagement.exceptions.park.DuplicateParkException;
import bikeManagement.exceptions.park.VoidParkException;
import bikeManagement.exceptions.user.*;

public class BikeManagementClass implements BikeManagement {
    /**
     * Serializable
     */
    static final long serialVersionUID = 0L;

    /**
     * Instance Variables
     */
    private HashTable<String, SudoUser> users;
    private HashTable<String, SudoBike> bikes;
    private HashTable<String, SudoPark> parks;
    private Dictionary<ReverseInt, List<User>> delays;
    private int topPickUps;

    public BikeManagementClass() {
        this.users = new ChainedHashTable<>();
        this.bikes = new ChainedHashTable<>();
        this.parks = new ChainedHashTable<>();
        this.delays = new AVLTree<>();
        this.topPickUps = 0;
    }

    @Override
    public void addUser(String idUser, String nif, String email, String phone, String name, String address) throws DuplicateUserException {
        if (this.users.find(idUser.toUpperCase()) != null)
            throw new DuplicateUserException();
        this.users.insert(idUser.toUpperCase(), new UserClass(idUser, nif, email, phone, name, address));
    }

    @Override
    public void removeUser(String idUser) throws VoidUserException, VeteranUserException {
        User user = this.users.find(idUser.toUpperCase());
        if (user == null)
            throw new VoidUserException();
        if (user.getPickUps().hasNext())
            throw new VeteranUserException();
        this.users.remove(idUser.toUpperCase());
    }

    @Override
    public void addPark(String idPark, String name, String address) throws DuplicateParkException {
        if (this.parks.find(idPark.toUpperCase()) != null)
            throw new DuplicateParkException();
        this.parks.insert(idPark.toUpperCase(), new ParkClass(idPark, name, address));
    }

    @Override
    public void addBike(String idBike, String idPark, String plate) throws DuplicateBikeException, VoidParkException {
        SudoPark park = this.parks.find(idPark.toUpperCase());
        if (this.bikes.find(idBike.toUpperCase()) != null)
            throw new DuplicateBikeException();
        if (park == null)
            throw new VoidParkException();
        BikeClass newBike = new BikeClass(idBike, park, plate);
        this.bikes.insert(idBike.toUpperCase(), newBike);
        park.parkBike(newBike);
    }

    @Override
    public void removeBike(String idBike) throws UsedBikeException, VoidBikeException {
        SudoBike bike = this.bikes.find(idBike.toUpperCase());
        if (bike == null)
            throw new VoidBikeException();
        if (bike.getPickUps().hasNext())
            throw new UsedBikeException();
        bike.getPark().deleteBike(idBike);
        this.bikes.remove(idBike.toUpperCase());
    }

    @Override
    public Park getPark(String parkId) throws VoidParkException {
        Park park = this.parks.find(parkId.toUpperCase());
        if (park == null)
            throw new VoidParkException();
        else
            return park;
    }

    @Override
    public void pickUp(String idBike, String idUser) throws VoidBikeException, MovingBikeException, VoidUserException, BusyUserException, InsufficientBalanceException {
        SudoPickUp pickup;
        SudoPark park;
        SudoBike bike = this.bikes.find(idBike.toUpperCase());
        SudoUser user = this.users.find(idUser.toUpperCase());
        if (bike == null)
            throw new VoidBikeException();
        if (bike.isMoving())
            throw new MovingBikeException();
        if (user == null)
            throw new VoidUserException();
        if (user.getCurrentPickup() != null)
            throw new BusyUserException();
        if (user.getBalance() < UserClass.BALANCE)
            throw new InsufficientBalanceException();
        pickup = new PickUpClass(user, bike, bike.getPark());
        park = bike.getPark();
        park.pickUp(idBike.toUpperCase());
        user.pickUp(pickup);
        bike.pickUp(pickup);
        if (park.getPickups() > this.topPickUps)
            this.topPickUps = park.getPickups();
    }

    private void delay(User delayedUser) {
        ReverseInt userPoints = new ReverseInt(delayedUser.getPoints() + 1);
        List<User> list = this.delays.find(userPoints);
        if (delayedUser.getPoints() != 0)
            this.delays.find(new ReverseInt(delayedUser.getPoints())).remove(delayedUser);
        if (list == null) {
            list = new DoublyLinkedList<>();
            list.addLast(delayedUser);
            this.delays.insert(userPoints, list);
        } else
            list.addLast(delayedUser);

    }

    @Override
    public User pickDown(String idBike, String idPark, int duration) throws VoidBikeException, StoppedBikeException, VoidParkException, InvalidDataException {
        SudoBike bike = this.bikes.find(idBike.toUpperCase());
        SudoPark park = this.parks.find(idPark.toUpperCase());
        SudoUser user;
        int delayCounter = 0;
        if (bike == null)
            throw new VoidBikeException();
        if (!bike.isMoving())
            throw new StoppedBikeException();
        if (park == null)
            throw new VoidParkException();
        if (duration <= 0)
            throw new InvalidDataException();
        SudoPickUp pickup = bike.getOngoingPickUp();
        pickup.pickDown(park, duration);
        user = this.users.find(pickup.getUser().getUserId().toUpperCase());
        if (duration > PickUpClass.MAX_DELAY) {
            delayCounter = ((duration - PickUpClass.MAX_DELAY - 1) / PickUpClass.DELAY_COST_TIME) + 1;
            this.delay(user);
        }
        user.pickDown(delayCounter);
        park.parkBike(bike);
        bike.pickDown();
        return user;
    }

    @Override
    public User chargeUser(String idUser, int amount) throws VoidUserException, InvalidDataException {
        SudoUser user = this.users.find(idUser.toUpperCase());
        if (user == null)
            throw new VoidUserException();
        if (amount <= 0)
            throw new InvalidDataException();
        user.addBalance(amount);
        return user;
    }

    @Override
    public Iterator<PickUp> listBikePickups(String idBike) throws VoidBikeException, UnusedBikeException, OngoingFirstPickupException {
        SudoBike bike = this.bikes.find(idBike.toUpperCase());
        if (bike == null)
            throw new VoidBikeException();
        if (!bike.getPickUps().hasNext()) {
            if (bike.isMoving())
                throw new OngoingFirstPickupException();
            else
                throw new UnusedBikeException();
        }
        return bike.getPickUps();
    }

    @Override
    public Iterator<PickUp> listUserPickups(String idUser) throws VoidUserException, NewUserException, OngoingFirstPickupException {
        User user = this.users.find(idUser.toUpperCase());
        if (user == null)
            throw new VoidUserException();
        if (!user.getPickUps().hasNext()) {
            if (user.getCurrentPickup() != null)
                throw new OngoingFirstPickupException();
            else
                throw new NewUserException();
        }
        return user.getPickUps();
    }

    @Override
    public boolean parkedBike(String idBike, String idPark) throws VoidBikeException, VoidParkException {
        SudoPark park = this.parks.find(idPark.toUpperCase());
        if (this.bikes.find(idBike.toUpperCase()) == null)
            throw new VoidBikeException();
        else if (park == null)
            throw new VoidParkException();
        else {
            return park.isBikeParked(idBike);
        }
    }

    @Override
    public Iterator<Entry<String,Park>> favoriteParks() {
        Park currentPark;
        Dictionary<String,Park> bestParks = new AVLTree<>();
        if (topPickUps != 0) {
            Iterator<Entry<String, SudoPark>> parkIterator = this.parks.iterator();
            while (parkIterator.hasNext()) {
                currentPark = parkIterator.next().getValue();
                if (currentPark.getPickups() == this.topPickUps)
                    bestParks.insert(currentPark.getName(),currentPark);
            }
        }
        return bestParks.iterator();
    }

    @Override
    public User getUser(String userId) throws VoidUserException {
        User user = this.users.find(userId.toUpperCase());
        if (user == null)
            throw new VoidUserException();
        return user;
    }


    @Override
    public Iterator<Entry<ReverseInt, List<User>>> getDelays() {
        return this.delays.iterator();
    }

}
