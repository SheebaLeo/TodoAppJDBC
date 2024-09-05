package se.lexicon.model;

import java.util.Objects;

public class Person {
    private int id;

    private String firstName;

    private String lastName;

    private String email;

    private AppUser credentials;

    public Person(String firstName, String lastName, String email) {
        if (firstName == null || firstName.isEmpty()) {
            throw new IllegalArgumentException("fristName cannot be null or empty");
        }
        if (lastName == null || lastName.isEmpty()) {
            throw new IllegalArgumentException("lastName cannot be null or empty");
        }
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("email cannot be null or empty");
        }
        setFirstName(firstName);
        setLastName(lastName);
        setEmail(email);

        setCredentials(credentials);
    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("email cannot be null or empty");
        }
        this.email = email;
    }

    public AppUser getCredentials() {
        return credentials;
    }

    public void setCredentials(AppUser credentials) {
        this.credentials = credentials;
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
