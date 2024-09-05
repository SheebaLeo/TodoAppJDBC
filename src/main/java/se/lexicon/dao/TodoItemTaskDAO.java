package se.lexicon.dao;

import se.lexicon.model.TodoItemTask;

import java.util.List;

public interface TodoItemTaskDAO {
    public abstract TodoItemTask persist(TodoItemTask todoItemTask);

    public abstract TodoItemTask findById(int id);

    public abstract List<TodoItemTask> findAll();

    public abstract List<TodoItemTask> findByAssignedStatus(boolean status);

    public abstract List<TodoItemTask> findByPersonId(int personId);

    public abstract void remove(int id);
}
