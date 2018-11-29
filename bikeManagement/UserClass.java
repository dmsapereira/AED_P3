package bikeManagement;

import bikeManagement.exceptions.user.InsufficientBalanceException;
import dataStructures.*;

import java.util.Objects;

class UserClass implements SudoUser {
    static final int BALANCE=5;
    static final long serialVersionUID = 0L;
    private String id, nif, email, phone, name, address;
    private int balance, points;
    private PickUp currentPickup;
    private List<PickUp> pickups;

    UserClass(String id, String nif, String email, String phone, String name, String address) {
        this.id = id;
        this.nif = nif;
        this.email = email;
        this.phone = phone;
        this.name = name;
        this.address = address;
        this.balance = BALANCE;
        this.points = 0;
        pickups = new DoublyLinkedList<>();
    }

    @Override
    public String getUserId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getNIF() {
        return this.nif;
    }

    @Override
    public String getAddress() {
        return this.address;
    }

    @Override
    public String getEmail() {
        return this.email;
    }

    @Override
    public String getPhone() {
        return this.phone;
    }

    @Override
    public int getPoints() {
        return this.points;
    }

    @Override
    public int getBalance() {
        return this.balance;
    }

    @Override
    public void addBalance(int value) {
        this.balance += value;
    }

    @Override
    public PickUp getCurrentPickup() {
        return currentPickup;
    }

    @Override
    public void pickUp(PickUp pickup) throws InsufficientBalanceException {
        if (this.balance<BALANCE)
            throw new InsufficientBalanceException();
        this.currentPickup = pickup;
    }

    @Override
    public void pickDown(int delayCounter) {
        this.balance-=delayCounter;
        if(delayCounter>0)
            this.points++;
        pickups.addLast(this.currentPickup);
        this.currentPickup = null;
    }

    @Override
    public Iterator<PickUp> getPickUps()  {
        return pickups.iterator();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserClass userClass = (UserClass) o;
        return Objects.equals(id, userClass.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
