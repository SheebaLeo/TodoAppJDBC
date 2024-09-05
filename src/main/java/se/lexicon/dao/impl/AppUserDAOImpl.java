package se.lexicon.dao.impl;

import se.lexicon.dao.AppUserDAO;
import se.lexicon.model.AppUser;

import java.util.ArrayList;
import java.util.List;

public class AppUserDAOImpl implements AppUserDAO {
    private final List<AppUser> users = new ArrayList<>();

    @Override
    public AppUser persist(AppUser appUser) {
        if (findByUsername(appUser.getUserName()) != null) {
            throw new IllegalArgumentException("Username " + appUser.getUserName() + "is already taken");
        }
        if (!users.contains(appUser)) {
            users.add(appUser);
        }
        return (AppUser) users;
    }

    @Override
    public AppUser findByUsername(String username) {
        for (AppUser user : users) {
            if (user.getUserName().equals(username)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public List<AppUser> findAll() {
        return new ArrayList<>(users);
    }

    @Override
    public void remove(String username) {
        AppUser appUser = findByUsername(username);
        if (appUser != null) {
            users.remove(username);
        }
    }
}
