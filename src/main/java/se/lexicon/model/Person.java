package se.lexicon.model;

import java.util.Objects;

public class Person {
    private int id;

    private String firstName;

    private String lastName;


    public Person(int id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Person() {

    }

    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        if (firstName == null || firstName.isEmpty()) {
            throw new IllegalArgumentException("fristName cannot be null or empty");
        }
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        if (lastName == null || lastName.isEmpty()) {
            throw new IllegalArgumentException("lastName cannot be null or empty");
        }
        this.lastName = lastName;
    }

    /*public String getSummary() {
        return "Id: " + id + ", " + "Name" + fristName + " " + lastName + ", " + "Email: " + email;
    }*/

    @Override
    public String toString() {
        return "Person{" +
                "personId= " + id +
                ", firstName= " + firstName +
                ", lastName= " + lastName +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Person person = (Person) obj;
        return firstName.equals(person.firstName) && lastName == person.lastName;
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName);
    }
}
