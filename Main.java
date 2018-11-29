import bikeManagement.*;
import bikeManagement.exceptions.InvalidDataException;
import bikeManagement.exceptions.bike.*;
import bikeManagement.exceptions.park.DuplicateParkException;
import bikeManagement.exceptions.park.VoidParkException;
import bikeManagement.exceptions.user.*;
import dataStructures.Entry;
import dataStructures.Iterator;
import dataStructures.List;

import java.io.*;
import java.util.Scanner;

/**
 * @author David Pereira (52890) dmsa.pereira@campus.fct.unl.pt
 */

/**
 * Enum to facilitate the recognition of user input
 */
enum Command {
    ADDUSER(), REMOVEUSER(), GETUSERINFO(), ADDPARK(), ADDBIKE(), REMOVEBIKE(), GETPARKINFO(), PICKUP(), PICKDOWN(), CHARGEUSER(), BIKEPICKUPS(), USERPICKUPS(), PARKEDBIKE(), LISTDELAYED(), FAVORITEPARKS(), XS();

    Command() {
    }
}

public class Main {
    /**
     * Constants to facilitate the printing of predefined information
     */
    private static final String LIST_FORMAT = "%s %s %s %d %d %d\n";
    private static final String USER_INFO_FORMAT = "%s: %s, %s, %s, %s, %s, %s\n";
    private static final String PARK_INFO_FORMAT = "%s: %s, %s\n";
    private static final String VOID_USER = "Utilizador inexistente.";
    private static final String DUPLICATE_USER = "Utilizador existente.";
    private static final String VOID_BIKE = "Bicicleta inexistente.";
    private static final String DUPLICATE_BIKE = "Bicicleta existente.";
    private static final String VOID_PARK = "Parque inexistente.";
    private static final String DUPLICATE_PARK = "Parque existente.";
    private static final String VETERAN_USER = "Utilizador ja utilizou o sistema.";
    private static final String USED_BIKE = "Bicicleta ja foi utilizada.";
    private static final String MOVING_BIKE = "Bicicleta em movimento.";
    private static final String BUSY_USER = "Utilizador em movimento.";
    private static final String INSUFFICIENT_BALANCE = "Saldo insuficiente.";
    private static final String STOPPED_BIKE = "Bicicleta parada.";
    private static final String INVALID_DATA = "Dados invalidos.";
    private static final String UNUSED_BIKE = "Bicicleta nao foi utilizada.";
    private static final String ONGOING_FIRST_PICKUP_BIKE = "Bicicleta em movimento em primeiro pickup.";
    private static final String NEW_USER = "Utilizador nao utilizou o sistema.";
    private static final String ONGOING_FIRST_PICKUP_USER = "Utilizador em primeiro PickUp.";

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        BikeManagement system = load();
        processCommand(in, system);
        save(system);
        System.out.println("Gravando e terminando...");
        System.out.println();
    }

    /**
     * Reads input and returns the associated Command
     *
     * @return <code>Command</code> that matches the user input
     */
    private static Command readCommand(Scanner in) {
        String input = in.next().toUpperCase();
        return Command.valueOf(input.toUpperCase());
    }

    /**
     * Processes the <code>Command</code> received from <code>readCommand(...)</code>
     */
    private static void processCommand(Scanner in, BikeManagement system) {
        Command input = readCommand(in);
        while (!input.equals(Command.XS)) {
            switch (input) {
                case ADDUSER:
                    commandAddUser(in, system);
                    break;
                case REMOVEUSER:
                    commandRemoveUser(in, system);
                    break;
                case GETUSERINFO:
                    commandGetUserInfo(in, system);
                    break;
                case ADDPARK:
                    commandAddPark(in, system);
                    break;
                case ADDBIKE:
                    commandAddBike(in, system);
                    break;
                case REMOVEBIKE:
                    commandRemoveBike(in, system);
                    break;
                case GETPARKINFO:
                    commandGetParkInfo(in, system);
                    break;
                case PICKUP:
                    commandPickup(in, system);
                    break;
                case PICKDOWN:
                    commandPickdown(in, system);
                    break;
                case CHARGEUSER:
                    commandChargeUser(in, system);
                    break;
                case BIKEPICKUPS:
                    commandBikePickups(in, system);
                    break;
                case USERPICKUPS:
                    commandUserPickups(in, system);
                    break;
                case PARKEDBIKE:
                    commandParkedBike(in, system);
                    break;
                case LISTDELAYED:
                    commandListDelayed(system);
                    break;
                case FAVORITEPARKS:
                    commandFavoriteParks(in, system);
                    break;
                case XS:
                    break;
            }
            System.out.println();
            input = readCommand(in);
        }
    }

    /**
     * Saves the execution state in a local file
     */
    private static void save(BikeManagement system) {
        try {
            ObjectOutputStream file = new ObjectOutputStream(new FileOutputStream("savefile"));
            file.writeObject(system);
            file.flush();
            file.close();
        } catch (IOException e) {
            System.out.println("Ficheiro nao foi encontrado.");
        }
    }

    /**
     * Loads a saved execution state created by <code>save(...)</code>. If none exists, a new <code>BikeManagement</code> is created.
     */
    private static BikeManagement load() {
        BikeManagement management;
        try {
            ObjectInputStream file = new ObjectInputStream((new FileInputStream("savefile")));
            management = (BikeManagement) file.readObject();
            file.close();
        } catch (IOException | ClassNotFoundException e) {
            management = new BikeManagementClass();
        }
        return management;
    }

    /**
     * Executes the favoriteParks command
     */
    private static void commandFavoriteParks(Scanner in, BikeManagement system) {
        Iterator<Entry<String,Park>> parks;
         Park park;
        in.nextLine();
        parks = system.favoriteParks();
        if (!parks.hasNext())
            System.out.println("Nao foram efetuados pickups.");
        else {
            while (parks.hasNext()) {
                park = parks.next().getValue();
                System.out.printf(PARK_INFO_FORMAT, park.getName(), park.getAddress(), park.getPickups());
            }
        }
    }

    /**
     * Executes the listDelayed command
     */
    private static void commandListDelayed(BikeManagement system) {
        User current;
        Iterator<User> userIterator;
        Iterator<Entry<ReverseInt, List<User>>> delayIterator = system.getDelays();
        if (!delayIterator.hasNext())
            System.out.println("Nao se registaram atrasos.");
        else {
            while (delayIterator.hasNext()) {
                userIterator = delayIterator.next().getValue().iterator();
                while (userIterator.hasNext()) {
                    current = userIterator.next();
                    System.out.printf(USER_INFO_FORMAT, current.getName(), current.getNIF(), current.getAddress(), current.getEmail(), current.getPhone(), current.getBalance(), current.getPoints());
                }
            }
        }
    }

    /**
     * Executes the parkedBike
     */
    private static void commandParkedBike(Scanner in, BikeManagement system) {
        String idBike = in.next();
        String idPark = in.next();
        in.nextLine();
        try {
            if (system.parkedBike(idBike, idPark))
                System.out.println("Bicicleta estacionada no parque.");
            else
                System.out.println("Bicicleta nao esta em parque.");
        } catch (VoidBikeException e) {
            System.out.println(VOID_BIKE);
        } catch (VoidParkException e) {
            System.out.println(VOID_PARK);
        }
    }

    /**
     * Prints the information required by the bikePickUps command
     */
    private static void printBikePickups(Iterator<PickUp> itera) {
        PickUp current;
        if (!itera.hasNext())
            System.out.println("Nao foram efetuados pickups.");
        else {
            while (itera.hasNext()) {
                current = itera.next();
                System.out.printf(LIST_FORMAT, current.getUser().getUserId(), current.getInitialPark().getParkId(), current.getFinalPark().getParkId(), current.getDuration(), current.getDelay(), current.getCost());
            }
        }
    }

    /**
     * Prints the information required by the userPickUps command
     */
    private static void printUserPickups(Iterator<PickUp> itera) {
        PickUp current;
        if (!itera.hasNext())
            System.out.println("Nao foram efetuados pickups.");
        else {
            while (itera.hasNext()) {
                current = itera.next();
                System.out.printf(LIST_FORMAT, current.getBike().getBikeId(), current.getInitialPark().getParkId(), current.getFinalPark().getParkId(), current.getDuration(), current.getDelay(), current.getCost());
            }
        }
    }

    /**
     * Ezecutes the userPickUps command
     */
    private static void commandUserPickups(Scanner in, BikeManagement system) {
        String idUser = in.next();
        in.nextLine();
        try {
            printUserPickups(system.listUserPickups(idUser));
        } catch (VoidUserException e) {
            System.out.println(VOID_USER);
        } catch (NewUserException e) {
            System.out.println(NEW_USER);
        } catch (OngoingFirstPickupException e) {
            System.out.println(ONGOING_FIRST_PICKUP_USER);
        }
    }

    /**
     * Executes the bikePickUps command
     */
    private static void commandBikePickups(Scanner in, BikeManagement system) {
        String idBike = in.next();
        in.nextLine();
        try {
            printBikePickups(system.listBikePickups(idBike));
        } catch (VoidBikeException e) {
            System.out.println(VOID_BIKE);
        } catch (UnusedBikeException e) {
            System.out.println(UNUSED_BIKE);
        } catch (OngoingFirstPickupException e) {
            System.out.println(ONGOING_FIRST_PICKUP_BIKE);
        }
    }

    /**
     * Executes the chargeUser command
     */
    private static void commandChargeUser(Scanner in, BikeManagement system) {
        User user;
        String idUser = in.next();
        int amount = in.nextInt();
        in.nextLine();
        try {
            user = system.chargeUser(idUser, amount);
            System.out.println("Saldo: " + user.getBalance() + " euros");
        } catch (VoidUserException e) {
            System.out.println(VOID_USER);
        } catch (InvalidDataException e) {
            System.out.println(INVALID_DATA);
        }
    }

    /**
     * Ezecutes the pickDown command
     */
    private static void commandPickdown(Scanner in, BikeManagement system) {
        User user;
        String idBike = in.next();
        String idPark = in.next();
        int duration = in.nextInt();
        in.nextLine();
        try {
            user = system.pickDown(idBike, idPark, duration);
            System.out.println("Pickdown com sucesso: " + user.getBalance() + " euros, " + user.getPoints() + " pontos");
        } catch (VoidBikeException e) {
            System.out.println(VOID_BIKE);
        } catch (StoppedBikeException e) {
            System.out.println(STOPPED_BIKE);
        } catch (VoidParkException e) {
            System.out.println(VOID_PARK);
        } catch (InvalidDataException e) {
            System.out.println(INVALID_DATA);
        }
    }

    /**
     * Executes the pickUp command
     */
    private static void commandPickup(Scanner in, BikeManagement system) {
        String idBike = in.next();
        String idUser = in.next();
        in.nextLine();
        try {
            system.pickUp(idBike, idUser);
            System.out.println("PickUp com sucesso.");
        } catch (VoidBikeException e) {
            System.out.println(VOID_BIKE);
        } catch (MovingBikeException e) {
            System.out.println(MOVING_BIKE);
        } catch (VoidUserException e) {
            System.out.println(VOID_USER);
        } catch (BusyUserException e) {
            System.out.println(BUSY_USER);
        } catch (InsufficientBalanceException e) {
            System.out.println(INSUFFICIENT_BALANCE);
        }
    }

    /**
     * Executes the getParkInfo command
     */
    private static void commandGetParkInfo(Scanner in, BikeManagement system) {
        String idPark = in.next();
        in.nextLine();
        try {
            Park park = system.getPark(idPark);
            System.out.printf(PARK_INFO_FORMAT, park.getName(), park.getAddress(), park.getParkedBikes());
        } catch (VoidParkException e) {
            System.out.println(VOID_PARK);
        }
    }

    /**
     * Executes the removeBike command
     */
    private static void commandRemoveBike(Scanner in, BikeManagement system) {
        String idBike = in.next();
        in.nextLine();
        try {
            system.removeBike(idBike);
            System.out.println("Bicicleta removida com sucesso.");
        } catch (VoidBikeException e) {
            System.out.println(VOID_BIKE);
        } catch (UsedBikeException e) {
            System.out.println(USED_BIKE);
        }
    }

    /**
     * Executes the addBike command
     */
    private static void commandAddBike(Scanner in, BikeManagement system) {
        String idBike = in.next();
        String idPark = in.next();
        String plate = in.nextLine().trim();
        try {
            system.addBike(idBike, idPark, plate);
            System.out.println("Bicicleta adicionada com sucesso.");
        } catch (DuplicateBikeException e) {
            System.out.println(DUPLICATE_BIKE);
        } catch (VoidParkException e) {
            System.out.println(VOID_PARK);
        }
    }

    /**
     * Executes the addPark command
     */
    private static void commandAddPark(Scanner in, BikeManagement system) {
        String idPark = in.next();
        String name = in.nextLine().trim();
        String address = in.nextLine().trim();
        try {
            system.addPark(idPark, name, address);
            System.out.println("Parque adicionado com sucesso.");
        } catch (DuplicateParkException e) {
            System.out.println(DUPLICATE_PARK);
        }
    }

    /**
     * Executes the getUserInfo command
     */
    private static void commandGetUserInfo(Scanner in, BikeManagement system) {
        String idUser = in.next();
        in.nextLine();
        try {
            User user = system.getUser(idUser);
            System.out.printf(USER_INFO_FORMAT, user.getName(), user.getNIF(), user.getAddress(), user.getEmail(), user.getPhone(), user.getBalance(), user.getPoints());
        } catch (VoidUserException e) {
            System.out.println(VOID_USER);
        }
    }

    /**
     * Executes the removeUser command
     */
    private static void commandRemoveUser(Scanner in, BikeManagement system) {
        String idUser = in.next();
        in.nextLine();
        try {
            system.removeUser(idUser);
            System.out.println("Utilizador removido com sucesso.");
        } catch (VoidUserException e) {
            System.out.println(VOID_USER);
        } catch (VeteranUserException e) {
            System.out.println(VETERAN_USER);
        }
    }

    /**
     * Executes the addUser command
     */
    private static void commandAddUser(Scanner in, BikeManagement system) {
        String idUser = in.next();
        String nif = in.next();
        String email = in.next();
        String phone = in.next();
        String name = in.nextLine().trim();
        String address = in.nextLine();
        try {
            system.addUser(idUser, nif, email, phone, name, address);
            System.out.println("Insercao de utilizador com sucesso.");
        } catch (DuplicateUserException e) {
            System.out.println(DUPLICATE_USER);
        }
    }
}
