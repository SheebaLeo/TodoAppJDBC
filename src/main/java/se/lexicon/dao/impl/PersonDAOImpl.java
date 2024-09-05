package se.lexicon.dao.impl;

import se.lexicon.dao.PersonDAO;
import se.lexicon.exception.MySQLException;
import se.lexicon.model.Person;
import se.lexicon.sequence.PersonIdSequencer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonDAOImpl implements PersonDAO {
    List<Person> persons = new ArrayList<>();
    private Connection connection;
    public PersonDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Person create(Person person) {
        String insertQuery = "INSERT INTO PERSON (first_name, last_name) VALUES (?, ?)";

        try (
                PreparedStatement preparedStatement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, person.getFirstName());
            preparedStatement.setString(2, person.getLastName());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                String errorMessage = "Creating person failed, no rows affected.";
                throw new MySQLException(errorMessage);
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int personId = generatedKeys.getInt(1);
                    System.out.println("Person created successfully");
                    return new Person(personId, person.getFirstName(), person.getLastName());
                } else {
                    String errorMessage = "Creating person failed, no ID obtained.";
                    throw new MySQLException(errorMessage);
                }
            }

        } catch (SQLException e) {
            String errorMessage = "An error occurred while creating a person.";
            throw new MySQLException(errorMessage, e);
        }
    }

    @Override
    public Person persist(Person person) {
        if (person.getId() != 0) throw new IllegalArgumentException("Invalid state person.id was not 0 or null");
        if (findByEmail(person.getEmail()) != null) {
            throw new IllegalStateException("Email " + person.getEmail() + " is already taken");
        }
        person.setId(PersonIdSequencer.nextId());
        persons.add(person);
        return (Person) persons;
    }

    @Override
    public Person findById(int id) {
        Person person = null;
        try {
            String query = "SELECT * FROM Person WHERE person_id = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                person = new Person();
                person.setId(rs.getInt("person_id"));
                person.setFirstName(rs.getString("first_name"));
                person.setLastName(rs.getString("last_name"));
                System.out.println(person.toString());
            }
        } catch (SQLException e) {
            String errorMessage = "An error occurred while getting a person by id.";
            throw new MySQLException(errorMessage, e);
        }
        return person;
    }

    @Override
    public Person findByEmail(String email) {
        for (Person person : persons) {
            if (person.getEmail().equalsIgnoreCase(email)) {
                return person;
            }
        }
        return null;
    }

    @Override
    public List<Person> findAll() {
        List<Person> people = new ArrayList<>();
        try {
            String query = "SELECT * FROM Person";
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Person person = new Person();
                person.setId(rs.getInt("person_id"));
                person.setFirstName(rs.getString("first_name"));
                person.setLastName(rs.getString("last_name"));
                people.add(person);
                System.out.println(person.toString());
            }
        } catch (SQLException e) {
            String errorMessage = "An error occurred while getting all person.";
            throw new MySQLException(errorMessage, e);
        }
        return people;
    }

    @Override
    public List<Person> findByName(String firstName) {
        List<Person> personList = new ArrayList<>();
        try {
            String query = "SELECT * FROM PERSON WHERE first_Name = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, firstName);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Person person = new Person();
                person.setId(rs.getInt("person_id"));
                person.setFirstName(rs.getString("first_name"));
                person.setLastName(rs.getString("last_name"));
                System.out.println(person.toString());
                personList.add(person);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return personList;
    }

    @Override
    public Person update(Person person) {
        try {
            String query = "UPDATE person SET first_name = ?, last_name = ? WHERE person_id = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, person.getFirstName());
            stmt.setString(2, person.getLastName());
            stmt.setInt(3, person.getId());
            stmt.executeUpdate();
            System.out.println("Person updated successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return person;
    }

    @Override
    public boolean deleteById(int id) {
        boolean isDeleted = false;
        try {
            String query = "DELETE FROM person WHERE person_id = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, id);
            isDeleted = stmt.executeUpdate() > 0;
            System.out.println("Deleted Person with id " + id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isDeleted;
    }
}
