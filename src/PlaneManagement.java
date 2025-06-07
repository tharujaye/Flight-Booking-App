import java.util.InputMismatchException;
import java.util.Scanner;
public class PlaneManagement {

    //creating tickets array to store sold tickets
    private static Ticket[] tickets;

    //creating the seating array
    static int[][] seating_array = {
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
    };

    public static void main(String[] args) {

        //initializing the tickets array to store
        tickets = new Ticket[52];

        System.out.println("\nWelcome to the Plane Management application");

        int choice;
        Scanner input = new Scanner(System.in);
        do {
            //Displaying the main menu
            System.out.println("""
                    \n*********************************************
                    *\t\t\t\t MENU OPTIONS \t\t\t\t*
                    *********************************************
                    \t1) Buy a seat
                    \t2) Cancel a seat
                    \t3) Find first available seat
                    \t4) Show seating plan
                    \t5) Print tickets information and total sales
                    \t6) Search ticket
                    \t0) Quit
                    *********************************************""");

            try {
                // Getting user input for menu access with exception handling
                System.out.println("\nPlease select an option from the menu (0-6): ");
                choice = input.nextInt();

                // Displaying if the choice number is not in the menu
                if (choice < 0 || choice > 6) {
                    throw new IllegalArgumentException("Invalid option! Please enter a number between 0 and 6.");
                }

                // Switch statement for menu options
                switch (choice) {
                    case 1:
                        //Accessing buy seat method
                        buy_seat();
                        break;
                    case 2:
                        //Accessing cancel seat method
                        cancel_seat(seating_array);
                        break;
                    case 3:
                        //Accessing find first available seat method
                        find_first_available(seating_array);
                        break;
                    case 4:
                        //Accessing show seating plan method
                        show_seating_plan();
                        break;
                    case 5:
                        //Accessing print ticket info method
                        print_ticket_info();
                        break;
                    case 6:
                        //Accessing search ticket info method
                        search_ticket();
                        break;
                    case 0:
                        System.out.println("Exiting the menu. Have a nice day!");
                        System.exit(0); // Exit the program
                        break;
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage()); // Getting message
            }
            // Displaying if the user input is not a number
            catch (InputMismatchException e) {
                System.out.println("Invalid input type! Please enter a number.");
                input.next(); // Clear the invalid input
            }

        } while (true);
    }

    // buy seat method
    public static void buy_seat() {
        Scanner input = new Scanner(System.in);

        do {
            System.out.println("Current Seating Plan:");
            System.out.println("""
                A: O O O O O O O O O O O O O O
                B: O O O O O O O O O O O O
                C: O O O O O O O O O O O O
                D: O O O O O O O O O O O O O O""");

            // getting row input
            System.out.println("\nEnter the seat row letter (A-D) : ");
            char seat_row_letter = input.next().toUpperCase().charAt(0);

            //validating row input
            if (seat_row_letter < 'A' || seat_row_letter > 'D') {
                System.out.println("The row letter is invalid. Please enter correct row letter (A-D)");
                continue;
            }

            int row_index = seat_row_letter - 'A';

            int seat_num;

            // getting seat number for B & C rows
            if ((seat_row_letter == 'B' || seat_row_letter == 'C')) {
                System.out.print("Enter seat number (1 to 12): ");
                seat_num = input.nextInt();
                // validating seat number input
                if (seat_num < 1 || seat_num > 12) {
                    System.out.println("The seat number is invalid. Enter a number from 1-12");
                    continue;
                }

            // getting seat number for A & D rows
            } else {
                System.out.print("Enter seat number (1 to 14): ");
                seat_num = input.nextInt();
                // validating seat number input
                if (seat_num < 1 || seat_num > 14) {
                    System.out.println("The seat number is invalid. Enter a number from 1-14");
                    continue;
                }
            }

            // checking if seat is available and marking as sold
            if (seating_array[row_index][seat_num - 1] == 'X') {
                System.out.println("This seat has already been purchased. Please choose another seat.");
                continue;
            }

            // getting buyer information
            System.out.println("Enter passenger's name: ");
            String name = capitalize(input.next());
            System.out.println("Enter passenger's surname: ");
            String surname = capitalize(input.next());
            System.out.println("Enter passenger's email: ");
            String email = input.next();

            Person person = new Person(name, surname, email);
            double price = calculate_ticket_price(seat_row_letter);
            Ticket ticket = new Ticket(seat_row_letter, seat_num, price, person);

            // Adding ticket to the tickets array
            for (int i = 0; i < tickets.length; i++) {
                if (tickets[i] == null) {
                    tickets[i] = ticket;
                    break;
                }
            }

            // Marking X in seating array
            seating_array[row_index][seat_num - 1] = 'X';

            // Displaying as ticket purchased for the buyer
            System.out.println("The seat " + seat_row_letter + seat_num +" has been purchased successfully...");
            System.out.println();

            //Asking if user is buying another ticket
            System.out.println("DO you want to buy another ticket? Press (Y) for 'Yes' or (N) for 'No': ");
            String answer = input.next().toUpperCase();

            // Return to main menu
            if(answer.equals("N")){
                break;
            }

        } while (true);
    }

    // auto capitalizing user input name and surname
    public static String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }

    //cancel seat method
    public static void cancel_seat(int[][] seating_array){
        Scanner input = new Scanner(System.in);

        // getting row input
        System.out.println("Enter the seat row letter (A-D) : ");
        char seat_row_letter = input.next().toUpperCase().charAt(0);

        //validating row input
        if (seat_row_letter < 'A' || seat_row_letter > 'D') {
            System.out.println("The row letter is incorrect. Please enter correct row letter");
            return;
        }

        // getting seat number input
        int maxSeatNum = (seat_row_letter == 'B' || seat_row_letter == 'C') ? 12 : 14;
        System.out.print("Enter seat number (1 to " + maxSeatNum + "): ");
        int seat_num = input.nextInt();

        // validating seat number input
        if (seat_num < 1 || seat_num > maxSeatNum) {
            System.out.println("The seat number is invalid. Enter a number from 1-" + maxSeatNum);
            return;
        }

        // canceling the seat and changing X to 0 in seating array and displaying user
        int row_index = get_rowNum(seat_row_letter);
        if (seating_array[row_index][seat_num - 1] == 'X') {
            seating_array[row_index][seat_num - 1] = 0;
            System.out.println("Successfully cancelled your seat " + seat_row_letter + seat_num);

            // removing cancelled ticket from the ticket array
            for (int i = 0; i < tickets.length; i++) {
                if (tickets[i] != null && tickets[i].getRow() == seat_row_letter && tickets[i].getSeat() == seat_num) {
                    tickets[i] = null;
                    break;
                }
            }
        } else {
            // Displaying if the seat is not purchased yet for cancel
            System.out.println("Seat " + seat_row_letter + seat_num + " is not purchased.");
        }
    }

    // Find first available seat method
    public static void find_first_available(int[][] seating_array) {
        for (int row = 0; row < seating_array.length; row++) {
            for (int seat = 0; seat < seating_array[row].length; seat++) {
                if (seating_array[row][seat] == 0) {
                    char rowLetter = (char) ('A' + row);
                    int seatNumber = seat + 1;
                    System.out.println("First available seat is " + rowLetter + (seatNumber));
                    return;
                }
            }
        }
        System.out.println("No available seats right now.");
    }

    //Seating plan method
    public static void show_seating_plan(){
        System.out.println("\nSeating Plan");

        for (int row = 0; row < seating_array.length; row++) {
            char seat_row_letter = (char) ('A' + row);
            System.out.print(seat_row_letter + ": ");

            for (int seat = 0; seat < seating_array[row].length; seat++) {
                if (seating_array[row][seat] == 0) {
                    System.out.print("O ");
                } else {
                    System.out.print("X ");
                }
            }
            System.out.println();
        }
    }

    // printing ticket info method
    public static void print_ticket_info(){

        double totalSales = 0;

        for (Ticket ticket : tickets) {
            if (ticket != null) {
                ticket.print_ticket_info();
                totalSales += ticket.getPrice();
            }
        }
        System.out.println("\nTotal Sales: " + totalSales);
    }

    // search ticket method
    public static void search_ticket() {
        Scanner input = new Scanner(System.in);

        // getting row input
        System.out.print("Enter the seat row letter (A-D): ");
        char seat_row_letter = input.next().toUpperCase().charAt(0);

        //validating row input
        if (seat_row_letter < 'A' || seat_row_letter > 'D') {
            System.out.println("The row letter is incorrect. Please enter correct row letter");
            return;
        }

        // getting seat number input
        int maxSeatNum = (seat_row_letter == 'B' || seat_row_letter == 'C') ? 12 : 14;
        System.out.print("Enter seat number (1 to " + maxSeatNum + "): ");
        int seat_num = input.nextInt();

        // validating seat number input
        if (seat_num < 1 || seat_num > maxSeatNum) {
            System.out.println("The seat number is invalid. Enter a number from 1-" + maxSeatNum);
            return;
        }

        boolean found = false;
        for (Ticket ticket : tickets) {
            if (ticket != null && ticket.getRow() == seat_row_letter && ticket.getSeat() == seat_num) {
                System.out.println("\nThis seat is already booked");
                ticket.print_ticket_info();
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("This seat is available.");
        }
    }

    // calculate ticket price method
    public static double calculate_ticket_price(char row) {
        if (row == 'A') {
            return 200;
        } else if (row == 'B') {
            return 150;
        } else if (row == 'C') {
            return 100;
        } else {
            return 100;
        }
    }

    //initializing row indexes from row Letter
    public static int get_rowNum(char row) {
        int row_index = 0;

        if (row == 'B') {
            row_index = 1;
        }
        if (row == 'C') {
            row_index = 2;
        }
        if (row == 'D') {
            row_index = 3;
        }
        return row_index;
    }

}