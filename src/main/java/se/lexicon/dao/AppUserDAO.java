package se.lexicon.dao;

import se.lexicon.model.AppUser;

import java.util.List;

public interface AppUserDAO {
    public abstract AppUser persist(AppUser appUser);

    public abstract AppUser findByUsername(String username);

    public abstract List<AppUser> findAll();

    public abstract void remove(String username);
}
