package bikeManagement;

import dataStructures.DoublyLinkedList;
import dataStructures.Iterator;
import dataStructures.List;

import java.util.Objects;

class BikeClass implements SudoBike {
    static final long serialVersionUID = 0L;
    private String id, plate;
    private SudoPark park;
    private SudoPickUp pickup;
    private List<PickUp> pickups;

     BikeClass(String idBike, SudoPark park, String plate) {
        this.id = idBike;
        this.park = park;
        this.plate = plate;
        pickups = new DoublyLinkedList<>();
    }

    @Override
    public String getBikeId() {
        return this.id;
    }

    @Override
    public String getPlate() {
        return this.plate;
    }

    @Override
    public SudoPark getPark() {
        return this.park;
    }

   @Override
    public SudoPickUp getOngoingPickUp(){
         return this.pickup;
    }

    @Override
    public void pickUp(SudoPickUp pickup) {
        this.pickup = pickup;
        this.park = null;
    }

    @Override
    public void pickDown() {
        this.park = (SudoPark) this.pickup.getFinalPark();
        pickups.addLast(this.pickup);
        pickup = null;
    }

    @Override
    public Iterator<PickUp> getPickUps() {
        return pickups.iterator();
    }

    @Override
    public boolean isMoving() {
        return this.pickup != null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BikeClass bikeClass = (BikeClass) o;
        return Objects.equals(id, bikeClass.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
}
