package se.lexicon.dao;

import se.lexicon.model.Person;
import se.lexicon.model.TodoItem;

import java.time.LocalDate;
import java.util.List;

public interface TodoItemDAO {

    public abstract TodoItem create(TodoItem todoItem);

    public abstract TodoItem findById(int id);

    public abstract List<TodoItem> findAll();

    public abstract List<TodoItem> findAllByDoneStatus(boolean done);

    public abstract List<TodoItem> findByAssigneeId(int personId);

    public abstract List<TodoItem> findByAssignee(Person person);

    public abstract List<TodoItem> findByUnassignedTodoItems();

    public abstract TodoItem update(TodoItem todoItem);

    public abstract boolean deleteById(int id);
}
