import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    private static final Scanner input = new Scanner(System.in);

    public static void main(String[] args) {

        System.out.println("\n                                     WELCOME TO OUR CRUISE SHIP! ");

        Queue q = new Queue();

        Cabin[][] cabin = new Cabin[12][3];
        //Making the array
        for (int x = 0; x < 12; x++) {
            for (int y = 0; y < 3; y++) {
                cabin[x][y] = new Cabin();
            }
        }

        Passenger[] passenger = new Passenger[36];
        //Making the passenger array
        for (int a = 0; a < 36; a++) {
            passenger[a] = new Passenger("Empty", "Empty", 0.0);
        }

        //initialising the cabin array and the passenger array as empty
        initialise(cabin);

        while (true) {

            System.out.println("\n=====================================================================================================");
            System.out.println("|" + "                                           Menu                                                    " + "|");
            System.out.println("=====================================================================================================");
            System.out.println("| " + "       A        |                To add customers to cabin                                       " + " |");
            System.out.println("| " + "       V        |                View all cabins                                                 " + " |");
            System.out.println("| " + "       E        |                Display empty cabins                                            " + " |");
            System.out.println("| " + "       D        |                Delete customer from cabin                                      " + " |");
            System.out.println("| " + "       F        |                Find cabin from customer name                                   " + " |");
            System.out.println("| " + "       S        |                Store program data into a file                                  " + " |");
            System.out.println("| " + "       L        |                Load program data from a file                                   " + " |");
            System.out.println("| " + "       O        |                View passengers Ordered alphabetically by name                  " + " |");
            System.out.println("| " + "       T        |                View expenses                                                   " + " |");
            System.out.println("| " + "       Q        |                Quit                                                            " + " |");
            System.out.println("=====================================================================================================");

            System.out.println("\nPlease enter your selection:");
            String selection = input.next().toUpperCase();

            switch (selection) {
                case "A" -> addCustomer(cabin, passenger, q);
                case "V" -> viewAllCabin(cabin);
                case "E" -> displayEmptyCabin(cabin);
                case "D" -> deleteCustomer(cabin, passenger, q);
                case "F" -> findCustomer(cabin);
                case "S" -> storeData(passenger);
                case "L" -> loadDataFromFile();
                case "O" -> sort(passenger);
                case "T" -> passengerExpenses(passenger);
                case "Q" -> {
                    System.out.println("_____________________________________________________________________________________________________");
                    System.out.println("                                      Thank you have a nice day :)");
                    System.out.println("_____________________________________________________________________________________________________");
                    System.out.println("\n                                              ***                                                     ");
                    return;
                }
                default -> {
                    System.out.println("_____________________________________________________________________________________________________");
                    System.out.println("                                           Invalid input!");
                    System.out.println("_____________________________________________________________________________________________________");
                    System.out.println("\n                                              ***                                                     ");
                }
            }
        }
    }

    /**
     * Initialising the cabin pMainName to empty.
     *
     * @ para cabin[][]
     */
    public static void initialise(Cabin[][] cabinRef) {

        for (int x = 0; x < 12; x++) {
            for (int y = 0; y < 3; y++) {
                cabinRef[x][y].setupMainName("Empty");
            }
        }
    }

    /**
     * This algorithm is used to add 3 passengers to the cabin and passenger array.
     * Can add each customer's first name, second name and expenses.
     *
     * @ para cabin[][], passenger[]
     */
    public static void addCustomer(Cabin[][] cabin, Passenger[] passengerRef, Queue q) {
        boolean isCabinFull = false;
        int cabinNumber;
        String customerFirstName;
        String customerSurName;
        double exp;
        int count = 0;
        int qCount = 0;
        int fullCount = 0;

        for (Cabin[] cabins : cabin) {
            for (int x = 0; x < 3; x++) {
                if (!cabins[x].getMainName().equals("Empty")) {
                    fullCount++;
                    break;
                }
            }
        }
        if (fullCount == 12) {
            if (!q.isFull()) {
                System.out.println("_____________________________________________________________________________________________________");
                System.out.println("                                        All cabins are occupied!");
                System.out.println("_____________________________________________________________________________________________________");
                while (true) {
                    q.show();
                    System.out.println("\nDo you want to add a passenger to queue (Y/N)?:");
                    String ans = input.next();
                    if (ans.equalsIgnoreCase("Y")) {
                        System.out.println("\nEnter the passenger's first name:");
                        customerFirstName = input.next();

                        System.out.println("\nEnter the passenger's surname:");
                        customerSurName = input.next();

                        do {
                            try {
                                System.out.println("\nEnter the passenger's expenses:");
                                exp = Double.parseDouble(input.next());
                                break;
                            } catch (NumberFormatException e) {
                                System.out.println("\n                  Please check the entered passenger expenses and try again.");
                                System.out.println("_____________________________________________________________________________________________________");
                            }
                        } while (true);

                        q.enQueue(new Passenger(customerFirstName, customerSurName, exp));
                        System.out.println("_____________________________________________________________________________________________________");
                        System.out.println("                                Passenger " + customerFirstName + " " + customerSurName + " added to the queue");
                        System.out.println("_____________________________________________________________________________________________________");
                        qCount++;

                        if (qCount == 5) {
                            System.out.println("_____________________________________________________________________________________________________");
                            System.out.println("                               Queue is occupied by all passengers!");
                            System.out.println("_____________________________________________________________________________________________________");
                            q.show();
                            System.out.println("\n                                              ***                                                     ");
                            break;
                        }
                    } else if (ans.equalsIgnoreCase("N")) {
                        System.out.println("_____________________________________________________________________________________________________");
                        System.out.println("                                        Leaving from queue....");
                        System.out.println("_____________________________________________________________________________________________________");
                        System.out.println("\n                                              ***                                                     ");

                        break;
                    } else {
                        System.out.println("                                           Invalid input!");
                        System.out.println("_____________________________________________________________________________________________________");
                    }
                }
            } else {
                System.out.println("_____________________________________________________________________________________________________");
                System.out.println("                               Queue is occupied by all passengers!");
                System.out.println("_____________________________________________________________________________________________________");
                q.show();
                System.out.println("\n                                              ***                                                     ");
            }
        } else {
            do {
                try {
                    System.out.println("\nEnter the cabin number (1-12) or enter 13 to exit:");
                    cabinNumber = Integer.parseInt(input.next()) - 1;
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("\n                              Invalid cabin number try again.");
                    System.out.println("_____________________________________________________________________________________________________");
                }
            } while (true);

            for (int i = 0; i < 3; i++) {
                if (!cabin[cabinNumber][i].getMainName().equals("Empty")) {
                    isCabinFull = true;
                    break;
                }
            }

            if (isCabinFull) {
                System.out.println("_____________________________________________________________________________________________________");
                System.out.println("                                        cabin " + (cabinNumber + 1) + " is full!");
                System.out.println("_____________________________________________________________________________________________________");
                System.out.println("\n                                              ***                                                     ");

            } else if (cabinNumber != 12) {
                view(cabin, cabinNumber);
                while (true) {
                    System.out.println("\nDo you want to add a passenger to cabin " + (cabinNumber + 1) + " (Y/N)?:");
                    String ans = input.next();

                    if (ans.equalsIgnoreCase("Y")) {
                        int passNum;
                        do {
                            try {
                                System.out.println("\nEnter the passenger number (1-3) or enter 4 to view availability:");
                                passNum = Integer.parseInt(input.next()) - 1;
                                break;
                            } catch (NumberFormatException e) {
                                System.out.println("\n                          Invalid passenger number try again.");
                                System.out.println("_____________________________________________________________________________________________________");
                            }
                        } while (true);

                        if (passNum == 3) {
                            view(cabin, cabinNumber);
                        } else {
                            if (cabin[cabinNumber][passNum].getMainName().equals("Empty")) {
                                System.out.println("\nEnter the passenger's first name:");
                                customerFirstName = input.next();

                                System.out.println("\nEnter the passenger's surname:");
                                customerSurName = input.next();

                                do {
                                    try {
                                        System.out.println("\nEnter the passenger's expenses:");
                                        exp = Double.parseDouble(input.next());
                                        break;
                                    } catch (NumberFormatException e) {
                                        System.out.println("\n                  Please check the entered passenger expenses and try again.");
                                        System.out.println("_____________________________________________________________________________________________________");
                                    }
                                } while (true);

                                cabin[cabinNumber][passNum].setupMainName(customerFirstName);
                                System.out.println("_____________________________________________________________________________________________________");
                                System.out.println("                                Passenger " + customerFirstName + " " + customerSurName + " added to the cabin " + (cabinNumber + 1));
                                System.out.println("_____________________________________________________________________________________________________");
                                count++;

                                for (int x = 0; x < 36; x++) {
                                    if (passengerRef[x].getFirstName().equals("Empty") && passengerRef[x].getSurName().equals("Empty") && passengerRef[x].getExpenses() == 0.0) {
                                        passengerRef[x].setFirstName(customerFirstName);
                                        passengerRef[x].setSurName(customerSurName);
                                        passengerRef[x].setExpenses(exp);
                                        passengerRef[x].setCabinNumber(cabinNumber + 1);
                                        break;
                                    }
                                }
                                if (count == 3) {
                                    System.out.println("\n                                              ***                                                     ");
                                    break;
                                }
                            } else {
                                System.out.println("\n                                         Already occupied by " + cabin[cabinNumber][passNum].getMainName() + " !");
                                view(cabin, cabinNumber);
                            }
                        }

                    } else if (ans.equalsIgnoreCase("N")) {
                        System.out.println("_____________________________________________________________________________________________________");
                        System.out.println("                               Leaving from add customers to cabin....");
                        System.out.println("_____________________________________________________________________________________________________");
                        System.out.println("\n                                              ***                                                     ");
                        break;
                    } else {
                        System.out.println("\n                                   Invalid input try again!");
                        System.out.println("_____________________________________________________________________________________________________");
                    }
                }
            }
        }
    }

    /**
     * This algorithm print empty if the cabin has not occupied by any customer.
     *
     * @ para cabin[][]
     */
    public static void viewAllCabin(Cabin[][] cabin) {

        System.out.println("\n");

        for (int i = 0; i < 12; i++) {
            for (int x = 0; x < 3; x++) {
                if (cabin[i][x].getMainName().equals("Empty")) {
                    System.out.println("Cabin " + (i + 1) + " passenger " + (x + 1) + " is Empty");
                } else {
                    System.out.println("Cabin " + (i + 1) + " is occupied by " + (x + 1) + " passenger " + cabin[i][x].getMainName());
                }
            }
            System.out.println("-----------------------------------------------------------------------------------------------------");
        }
        System.out.println("\n                                              ***                                                     ");
    }

    /**
     * This algorithm is used to display empty cabins.
     *
     * @ para cabin[][]
     */
    public static void displayEmptyCabin(Cabin[][] cabin) {

        System.out.println("\n");

        for (int x = 0; x < 12; x++) {
            for (int y = 0; y < 3; y++) {
                if (cabin[x][y].getMainName().equals("Empty")) {
                    System.out.println("Cabin " + (x + 1) + " passenger " + (y + 1) + " is Empty");
                }
            }
            System.out.println("-----------------------------------------------------------------------------------------------------");
        }
        System.out.println("\n                                              ***                                                     ");
    }

    /**
     * This algorithm is used to delete customer from cabin.
     * This algorithm ask the first name and surname of the customer.
     *
     * @ para cabin[][]
     */
    public static void deleteCustomer(Cabin[][] cabin, Passenger[] passenger, Queue q) {

        int cabinNum;
        Passenger deQueuePassenger;

        System.out.println("\nPlease enter the first name of the passenger:");
        String fName = input.next();

        System.out.println("\nPlease enter the surname of the passenger:");
        String sName = input.next();

        do {
            try {
                System.out.println("\nPlease enter the cabin number of the passenger:");
                cabinNum = Integer.parseInt(input.next());
                break;
            } catch (NumberFormatException e) {
                System.out.println("\nInvalid input!");
            }
        } while (true);

        System.out.println("\nAre you sure you want to delete " + fName + " " + sName + " from cabin " + cabinNum + " (Y/N)?:");
        String ans = input.next();

        int count = 0;

        if (ans.equalsIgnoreCase("Y")) {
            for (int x = 0; x < 3; x++) {
                if (cabin[cabinNum - 1][x].getMainName().equals(fName)) {

                    cabin[cabinNum - 1][x].setupMainName("Empty");

                    for (int i = 0; i < 36; i++) {
                        if (passenger[i].getFirstName().equals(fName) && passenger[i].getSurName().equals(sName)) {
                            passenger[i].setFirstName("Empty");
                            passenger[i].setSurName("Empty");
                            passenger[i].setExpenses(0.0);
                            passenger[i].setCabinNumber(0);
                            break;
                        }
                    }
                    System.out.println("_____________________________________________________________________________________________________");
                    System.out.println("                                    Passenger " + fName + " " + sName + " deleted from cabin " + cabinNum);
                    System.out.println("_____________________________________________________________________________________________________");
                    q.show();
                    System.out.println("\nDo you want to add passenger from queue (Y/N)?:");
                    String a = input.next();
                    if (a.equalsIgnoreCase("Y")) {
                        if (!q.isEmpty()) {
                            deQueuePassenger = q.deQueue();
                            cabin[cabinNum - 1][x].setupMainName(deQueuePassenger.getFirstName());
                            for (int i = 0; i < 36; i++) {
                                if (passenger[i].getFirstName().equals("Empty") && passenger[i].getSurName().equals("Empty")) {
                                    passenger[i].setFirstName(deQueuePassenger.getFirstName());
                                    passenger[i].setSurName(deQueuePassenger.getSurName());
                                    passenger[i].setExpenses(deQueuePassenger.getExpenses());
                                    passenger[i].setCabinNumber(cabinNum);
                                    break;
                                }
                            }
                            System.out.println("_____________________________________________________________________________________________________");
                            System.out.println("                               Passenger " + deQueuePassenger.getFirstName() + " " + deQueuePassenger.getSurName() + " added from queue");
                        } else {
                            System.out.println("_____________________________________________________________________________________________________");
                            System.out.println("                                         Queue is empty!");
                        }
                    }
                } else {
                    count++;
                }
            }
            if (count == 3) {
                System.out.println("_____________________________________________________________________________________________________");
                System.out.println("                    Mismatch! Please check the passenger name or the cabin number");

            }
        } else if (ans.equalsIgnoreCase("N")) {
            System.out.println("_____________________________________________________________________________________________________");
            System.out.println("                                             Leaving....");
        } else {
            System.out.println("\n                                   Invalid input try again!");
        }
        System.out.println("_____________________________________________________________________________________________________\n");
        System.out.println("                                              ***                                                     ");
    }

    /**
     * This algorithm is used to find the cabin of the customers from their first name and sure name.
     *
     * @ para cabin[][]
     */
    public static void findCustomer(Cabin[][] cabin) {

        System.out.println("\nEnter the first name of the passenger:");
        String fName = input.next();

        int index;
        int count = 0;

        for (int x = 0; x < 12; x++) {
            for (int y = 0; y < 3; y++) {
                if (cabin[x][y].getMainName().equalsIgnoreCase(fName)) {

                    index = x;
                    System.out.println("_____________________________________________________________________________________________________");
                    System.out.println("                              Cabin number of passenger " + fName + " is " + (index + 1));
                    System.out.println("_____________________________________________________________________________________________________");
                    System.out.println("\n                                             ***                                                     ");
                } else {
                    count++;
                }
            }
        }
        if (count == 36) {
            System.out.println("_____________________________________________________________________________________________________");
            System.out.println("                                Cannot find a cabin from " + fName);
            System.out.println("_____________________________________________________________________________________________________\n");
            System.out.println("                                             ***                                                     ");
        }
    }

    /**
     * This algorithm is used to store data to a file called filename.txt.
     *
     * @ para cabin[][]
     */
    public static void storeData(Passenger[] passengerRef) {
        DecimalFormat df = new DecimalFormat("#,###.00");

        try {
            FileWriter myWriter = new FileWriter("filename.txt");

            for (Passenger passenger : passengerRef) {
                if (!passenger.getFirstName().equals("Empty")) {

                    myWriter.write("Name      :- " + passenger.getFirstName() + " " + passenger.getSurName() + "\n");
                    myWriter.write("Cabin no. :- " + passenger.getCabinNumber() + "\n");
                    myWriter.write("Expenses  :- " + df.format(passenger.getExpenses()) + "\n");
                    myWriter.write("\n");

                }
            }
            myWriter.close();
            System.out.println("_____________________________________________________________________________________________________");
            System.out.println("                                Data Successfully added to the file!");
            System.out.println("_____________________________________________________________________________________________________\n");
            System.out.println("                                              ***                                                     ");

        } catch (IOException e) {
            System.out.println("_____________________________________________________________________________________________________");
            System.out.println("                                        An error occurred!");
            System.out.println("_____________________________________________________________________________________________________");
            System.out.println("\n                                             ***                                                     ");
        }
    }

    /**
     * This algorithm is used to load data from a file.
     */
    public static void loadDataFromFile() {

        System.out.println("\n");

        try {
            File inputFile = new File("filename.txt");
            Scanner rf = new Scanner(inputFile);
            String fileLine;
            while (rf.hasNext()) {
                fileLine = rf.nextLine();
                System.out.println(fileLine);
            }
            rf.close();
            System.out.println("\n                                              ***                                                     ");
        } catch (FileNotFoundException e) {
            System.out.println("_____________________________________________________________________________________________________");
            System.out.println("                                        File not found!");
            System.out.println("_____________________________________________________________________________________________________");
            System.out.println("\n                                             ***                                                     ");
        }
    }

    /**
     * This is used in addCustomer method.
     * This algorithm help user to get an idea about availability of the cabin.
     *
     * @ para cabin[][], cabinNumber
     */
    public static void view(Cabin[][] cabin, int cabinNumber) {

        System.out.println("_____________________________________________________________________________________________________");
        for (int x = 0; x < 3; x++) {
            if (cabin[cabinNumber][x].getMainName().equals("Empty")) {
                System.out.println("                                    Cabin " + (cabinNumber + 1) + " passenger " + (x + 1) + " is Empty");
            } else {
                System.out.println("                                    Cabin " + (cabinNumber + 1) + " passenger " + (x + 1) + " is occupied by " + cabin[cabinNumber][x].getMainName());
            }
        }
        System.out.println("_____________________________________________________________________________________________________");
    }

    public static void sort(Passenger[] passengerRef) {

        String[] names = new String[36];
        Arrays.fill(names, "Empty");
        int a = 0;

        for (Passenger passenger : passengerRef) {
            if (!passenger.getFirstName().equals("Empty")) {
                names[a] = passenger.getFirstName() + " " + passenger.getSurName();
                a++;
            }
        }

        String temp;
        int count = 1;
        int eCount = 0;

        for (int x = 0; x < names.length - 1; x++) {
            for (int y = 0; y < names.length - x - 1; y++) {
                if (names[y].compareTo(names[y + 1]) > 0) {
                    temp = names[y];
                    names[y] = names[y + 1];
                    names[y + 1] = temp;
                }
            }
        }
        System.out.println("\n");
        for (String name : names) {
            if (!name.equals("Empty")) {
                System.out.println(count + ". " + name);
                count++;
            } else {
                eCount++;
            }
        }
        if (eCount == names.length) {
            System.out.println("_____________________________________________________________________________________________________");
            System.out.println("                                     No data available!");
            System.out.println("_____________________________________________________________________________________________________");
        }
        System.out.println("\n                                              ***                                                     ");
    }

    public static void passengerExpenses(Passenger[] passengerRef) {

        double total = 0;
        DecimalFormat df = new DecimalFormat("#,###.00");
        int ans;

        while (true) {
            System.out.println("\n_____________________________________________________________________________________________________");
            System.out.println("| " + "       1        |                View expenses per passenger                                     " + " |");
            System.out.println("| " + "       2        |                View total expenses                                             " + " |");
            System.out.println("| " + "       3        |                Exit                                                            " + " |");
            System.out.println("_____________________________________________________________________________________________________");

            do {
                try {
                    System.out.println("\nPlease Enter your selection:");
                    ans = Integer.parseInt(input.next());
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("\n                              Invalid cabin number try again.");
                    System.out.println("_____________________________________________________________________________________________________");
                }
            } while (true);


            if (ans == 1) {
                System.out.println("\n");

                for (int x = 0; x < 36; x++) {
                    if (!passengerRef[x].getFirstName().equals("Empty") && !passengerRef[x].getSurName().equals("Empty")) {
                        System.out.println("Name      :- " + passengerRef[x].getFirstName() + " " + passengerRef[x].getSurName());
                        System.out.println("Cabin no. :- " + passengerRef[x].getCabinNumber());
                        System.out.println("Expenses  :- " + df.format(passengerRef[x].getExpenses()));
                        System.out.println("-----------------------------------------------------------------------------------------------------");
                    }
                }
                System.out.println("\n                                             ***                                                     ");
            } else if (ans == 2) {
                for (int i = 0; i < 36; i++) {
                    if (!passengerRef[i].getFirstName().equals("Empty") && !passengerRef[i].getSurName().equals("Empty")) {
                        total = total + passengerRef[i].getExpenses();
                    }
                }
                System.out.println("\nTotal expenses :- " + df.format(total));
                System.out.println("\n                                              ***                                                     ");
            } else if (ans == 3) {
                System.out.println("_____________________________________________________________________________________________________");
                System.out.println("                                     Leaving from view expenses....");
                System.out.println("_____________________________________________________________________________________________________");
                System.out.println("\n                                              ***                                                     ");
                break;
            } else {
                System.out.println("_____________________________________________________________________________________________________");
                System.out.println("                                           Invalid input!");
                System.out.println("_____________________________________________________________________________________________________");
                System.out.println("\n                                              ***                                                     ");
            }
        }
    }
}
