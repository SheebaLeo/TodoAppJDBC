package se.lexicon.dao;

import se.lexicon.model.Person;

import java.util.List;

public interface PersonDAO {
    public abstract Person create(Person person);

    public abstract Person persist(Person person);

    public abstract Person findById(int id);

    public abstract Person findByEmail(String email);

    public abstract List<Person> findAll();

    public abstract List<Person> findByName(String firstName);

    public abstract Person update(Person person);

    public abstract boolean deleteById(int id);
}
