public class Person {

    private String name;
    private String surname;
    private String email;

    //constructor
    public Person(String name, String surname, String email) {
        this.name = name;
        this.surname = surname;
        this.email = email;
    }

    //getters
    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    //setters
    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    //printing the information
    public void print_info() {
        System.out.println("Name: " + name + " " + surname);
        System.out.println("Email: " + email);
    }

}