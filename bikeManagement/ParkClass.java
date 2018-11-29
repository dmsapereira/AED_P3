package bikeManagement;

import dataStructures.ChainedHashTable;
import dataStructures.HashTable;

class ParkClass implements SudoPark {
    static final long serialVersionUID = 0L;
    private String id, name, address;
    private HashTable<String, Bike> bikes;
    private int pickups;

    ParkClass(String parkId, String name, String address) {
        this.bikes = new ChainedHashTable<>(BikeManagementClass.DEFAULT_SIZE);
        this.id = parkId;
        this.name = name;
        this.address = address;
        this.pickups = 0;
    }

    @Override
    public String getParkId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getAddress() {
        return this.address;
    }

    @Override
    public void parkBike(Bike bike) {
        this.bikes.insert(bike.getBikeId().toUpperCase(), bike);
    }

    @Override
    public void deleteBike(String idBike) {

        this.bikes.remove(idBike.toUpperCase());
    }

    @Override
    public void pickUp(String idBike) {
        this.bikes.remove(idBike.toUpperCase());
        this.pickups++;
    }

    @Override
    public int getPickups() {
        return this.pickups;
    }

    @Override
    public int getParkedBikes() {
        return this.bikes.size();
    }

    @Override
    public boolean isBikeParked(String idBike) {
        return this.bikes.find(idBike.toUpperCase()) != null;
    }
}
