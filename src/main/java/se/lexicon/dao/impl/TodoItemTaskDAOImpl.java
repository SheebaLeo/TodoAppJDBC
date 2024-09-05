package se.lexicon.dao.impl;

import se.lexicon.dao.TodoItemTaskDAO;
import se.lexicon.model.TodoItemTask;

import java.util.ArrayList;
import java.util.List;

public class TodoItemTaskDAOImpl implements TodoItemTaskDAO {
    List<TodoItemTask> todoItemTasks = new ArrayList<>();
    @Override
    public TodoItemTask persist(TodoItemTask todoItemTask) {
        if(findById(todoItemTask.getId()) == todoItemTask) {
            throw new IllegalArgumentException("Todo Item Task already exist");
        }
        if(!todoItemTasks.contains(todoItemTask)) {
            todoItemTasks.add(todoItemTask);
        }
        return (TodoItemTask) todoItemTasks;
    }

    @Override
    public TodoItemTask findById(int id) {

        for (TodoItemTask todoItemTask : todoItemTasks) {
            if (todoItemTask.getId() == id) {
                return todoItemTask;
            }
        }
        return null;
    }

    @Override
    public List<TodoItemTask> findAll() {
        return new ArrayList<>(todoItemTasks);
    }

    @Override
    public List<TodoItemTask> findByAssignedStatus(boolean status) {
        List<TodoItemTask> doneItemTasks = new ArrayList<>();
        for (TodoItemTask todoItemTask : todoItemTasks) {
            if (todoItemTask.isAssigned() == status) {
                doneItemTasks.add(todoItemTask);
            }
        }
        return doneItemTasks;
    }

    @Override
    public List<TodoItemTask> findByPersonId(int personId) {
        List<TodoItemTask> todoTasksByPeronId = new ArrayList<>();
        for (TodoItemTask todoItemTask : todoItemTasks) {
            if (todoItemTask.getAssignee().getId() == personId) {
                todoTasksByPeronId.add(todoItemTask);
            }
        }
        return todoTasksByPeronId;
    }

    @Override
    public void remove(int id) {
        if(findByPersonId(id) != null) {
            todoItemTasks.remove(id);
        }
    }
}
