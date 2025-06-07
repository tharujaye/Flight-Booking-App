import java.io.FileWriter;
import java.io.IOException;

public class Ticket {

    private char row;
    private int seat;
    private double price;
    private Person person;

    //constructor
    public Ticket(char row, int seat, double price, Person person) {
        this.row = row;
        this.seat = seat;
        this.price = price;
        this.person = person;

        save();
    }
    
    //getters
    public char getRow() {
        return row;
    }

    public int getSeat() {
        return seat;
    }

    public double getPrice() {
        return price;
    }

    public Person getPerson() {
        return person;
    }

    //setters
    public void setRow(char row) {
        this.row = row;
    }

    public void setSeat(int seat) {
        this.seat = seat;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    //printing ticket information
    public void print_ticket_info() {
        System.out.println("\nTicket info: ");
        System.out.println("Ticket No: " + row + seat);
        System.out.println("Seat row   : "+ row);
        System.out.println("Seat number: "+ seat);
        System.out.println("Price: " + price);

        //printing person information
        person.print_info();
    }

    public void save() {
        String fileName = row + String.valueOf(seat) + ".txt";
        try (FileWriter fileWriter = new FileWriter(fileName)) {
            fileWriter.write("Ticket Information:\n");
            fileWriter.write("Row: " + row + "\n");
            fileWriter.write("Seat: " + seat + "\n");
            fileWriter.write("Price: $" + price + "\n");
            fileWriter.write("Passenger Information:\n");
            fileWriter.write("Name: " + person.getName() + "\n");
            fileWriter.write("Surname: " + person.getSurname() + "\n");
            fileWriter.write("Email: " + person.getEmail() + "\n");
            System.out.println("Ticket information saved to " + fileName);
        } catch (IOException e) {
            System.out.println("An error occurred while saving the ticket information.");
            e.printStackTrace();
        }
    }

}