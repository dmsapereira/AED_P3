package bikeManagement;

/**
 * Package private Interface for the Bike. Contains methods which change object properties
 */
 interface SudoBike extends Bike {

      /**
      * Picks up bike, unparking it and registering the pickup
      *
      * @param pickup Pre-created pick up with infortmation of the user and the bike being used
      */
     void pickUp(SudoPickUp pickup);

     /**
      * Delivers the bike, parking it and registering the successful pickup
      */
     void pickDown();

     /**
      * @return PickUp the bike is currently on. Null if there's none
      */
     SudoPickUp getOngoingPickUp();

     /**
      * Returns park where bike is currently parked
      *
      * @return <code>Park</code> where bike is parked, <code>null</code> if moving
      */
     SudoPark getPark();

 }
