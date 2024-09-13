package se.lexicon.dao.impl;

import se.lexicon.dao.TodoItemDAO;
import se.lexicon.exception.MySQLException;
import se.lexicon.model.Person;
import se.lexicon.model.TodoItem;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TodoItemDAOImpl implements TodoItemDAO {
    List<TodoItem> todoItems = new ArrayList<>();
    private Connection connection;
    public TodoItemDAOImpl(Connection connection) {
        this.connection = connection;
    }

    TodoItem todoItem = null;

    @Override
    public TodoItem create(TodoItem todoItem) {
        String insertQuery = "INSERT INTO TODO_ITEM (title, description, deadline, done, assignee_id) VALUES (?, ?, ?, ?, ?)";

        try (
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, todoItem.getTitle());
            preparedStatement.setString(2, todoItem.getDescription());
            preparedStatement.setDate(3, Date.valueOf(todoItem.getDeadLine()));
            preparedStatement.setBoolean(4, todoItem.isDone());
            if (todoItem.getCreator() != null && todoItem.getCreator().getId() > 0) {
                preparedStatement.setInt(5, todoItem.getCreator().getId());
            } else {
                preparedStatement.setNull(5, Types.INTEGER);
            }

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                String errorMessage = "Creating todo item failed, no rows affected.";
                throw new MySQLException(errorMessage);
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int todoId = generatedKeys.getInt(1);
                    System.out.println("Todo Item created successfully");
                    return new TodoItem(todoId, todoItem.getTitle(), todoItem.getDescription(), todoItem.getDeadLine(), todoItem.isDone(),
                            todoItem.getCreator());
                } else {
                    String errorMessage = "Creating todoItem failed, no ID obtained.";
                    throw new MySQLException(errorMessage);
                }
            }
        } catch (SQLException e) {
            String errorMessage = "An error occurred while creating a todo item.";
            throw new MySQLException(errorMessage, e);
        }
    }

    @Override
    public TodoItem findById(int id) {

        try {
            String query = "SELECT * FROM todo_item left join person on assignee_id = person_id where todo_id = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                todoItem = new TodoItem();
                todoItem.setId(rs.getInt("todo_id"));
                todoItem.setTitle(rs.getString("title"));
                todoItem.setDescription(rs.getString("description"));
                todoItem.setDone(rs.getBoolean("done"));
                todoItem.setDeadLine(rs.getDate("deadline").toLocalDate());
                Person creator = new Person();
                creator.setId(rs.getInt("assignee_id"));
                creator.setFirstName(rs.getString("first_Name"));
                creator.setLastName(rs.getString("last_name"));
                todoItem.setCreator(creator);

                System.out.println(todoItem.toString());
            }
        } catch (SQLException e) {
            String errorMessage = "An error occurred while getting a todo item by id.";
            throw new MySQLException(errorMessage, e);
        }
        return todoItem;
    }

    @Override
    public List<TodoItem> findAll() {

        List<TodoItem> todoItemList = new ArrayList<>();
        try {
            String query = "SELECT * FROM todo_item left join person on assignee_id = person_id";
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                todoItem = new TodoItem();
                todoItem.setId(rs.getInt("todo_id"));
                todoItem.setTitle(rs.getString("title"));
                todoItem.setDescription(rs.getString("description"));
                todoItem.setDone(rs.getBoolean("done"));
                todoItem.setDeadLine(rs.getDate("deadline").toLocalDate());
                Person creator = new Person();
                if(rs.getInt("assignee_id") != 0) {
                    creator.setId(rs.getInt("assignee_id"));
                    creator.setFirstName(rs.getString("first_name"));
                    creator.setLastName(rs.getString("last_name"));
                    todoItem.setCreator(creator);
                }

                todoItemList.add(todoItem);
                System.out.println(todoItem.toString());
            }
        } catch (SQLException e) {
            String errorMessage = "An error occurred while getting all todo item.";
            throw new MySQLException(errorMessage, e);
        }
        return todoItemList;
    }

    @Override
    public List<TodoItem> findAllByDoneStatus(boolean done) {
        List<TodoItem> doneItems = new ArrayList<>();
        try {
            String query = "SELECT * FROM todo_item left join person on assignee_id = person_id where done = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setBoolean(1, done);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                todoItem = new TodoItem();
                todoItem.setId(rs.getInt("todo_id"));
                todoItem.setTitle(rs.getString("title"));
                todoItem.setDescription(rs.getString("description"));
                todoItem.setDone(rs.getBoolean("done"));
                todoItem.setDeadLine(rs.getDate("deadline").toLocalDate());
                Person creator = new Person();
                if(rs.getInt("assignee_id") != 0) {
                    creator.setId(rs.getInt("assignee_id"));
                    creator.setFirstName(rs.getString("first_name"));
                    creator.setLastName(rs.getString("last_name"));
                    todoItem.setCreator(creator);
                }

                todoItems.add(todoItem);
                System.out.println(todoItem.toString());
            }
        } catch (SQLException e) {
            String errorMessage = "An error occurred while getting all todo item.";
            throw new MySQLException(errorMessage, e);
        }
        return todoItems;
    }

    @Override
    public List<TodoItem> findByAssigneeId(int personId) {
        List<TodoItem> doneItems = new ArrayList<>();
        try {
            String query = "SELECT * FROM todo_item left join person on assignee_id = person_id where assignee_id = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, personId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                todoItem = new TodoItem();
                todoItem.setId(rs.getInt("todo_id"));
                todoItem.setTitle(rs.getString("title"));
                todoItem.setDescription(rs.getString("description"));
                todoItem.setDone(rs.getBoolean("done"));
                todoItem.setDeadLine(rs.getDate("deadline").toLocalDate());
                Person creator = new Person();
                if(rs.getInt("assignee_id") != 0) {
                    creator.setId(rs.getInt("assignee_id"));
                    creator.setFirstName(rs.getString("first_name"));
                    creator.setLastName(rs.getString("last_name"));
                    todoItem.setCreator(creator);
                }

                todoItems.add(todoItem);
                System.out.println(todoItem.toString());
            }
        } catch (SQLException e) {
            String errorMessage = "An error occurred while getting all todo item.";
            throw new MySQLException(errorMessage, e);
        }
        return todoItems;
    }

    @Override
    public List<TodoItem> findByAssignee(Person person) {
        List<TodoItem> doneItems = new ArrayList<>();
        try {
            String query = "SELECT * FROM todo_item left join person on assignee_id = person_id where assignee_id = ? and first_name = ? and last_name = ? ";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, person.getId());
            stmt.setString(2, person.getFirstName());
            stmt.setString(3, person.getLastName());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                todoItem = new TodoItem();
                todoItem.setId(rs.getInt("todo_id"));
                todoItem.setTitle(rs.getString("title"));
                todoItem.setDescription(rs.getString("description"));
                todoItem.setDone(rs.getBoolean("done"));
                todoItem.setDeadLine(rs.getDate("deadline").toLocalDate());
                Person creator = new Person();
                if(rs.getInt("assignee_id") != 0) {
                    creator.setId(rs.getInt("assignee_id"));
                    creator.setFirstName(rs.getString("first_name"));
                    creator.setLastName(rs.getString("last_name"));
                    todoItem.setCreator(creator);
                }

                todoItems.add(todoItem);
                System.out.println(todoItem.toString());
            }
        } catch (SQLException e) {
            String errorMessage = "An error occurred while getting all Todo item.";
            throw new MySQLException(errorMessage, e);
        }
        return todoItems;
    }

    @Override
    public List<TodoItem> findByUnassignedTodoItems() {
        List<TodoItem> doneItems = new ArrayList<>();
        try {
            String query = "SELECT * FROM todo_item where assignee_id is null";
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                todoItem = new TodoItem();
                todoItem.setId(rs.getInt("todo_id"));
                todoItem.setTitle(rs.getString("title"));
                todoItem.setDescription(rs.getString("description"));
                todoItem.setDone(rs.getBoolean("done"));
                todoItem.setDeadLine(rs.getDate("deadline").toLocalDate());
                Person creator = new Person();
                if(rs.getInt("assignee_id") != 0) {
                    creator.setId(rs.getInt("assignee_id"));
                    creator.setFirstName(rs.getString("first_name"));
                    creator.setLastName(rs.getString("last_name"));
                    todoItem.setCreator(creator);
                }

                todoItems.add(todoItem);
                System.out.println(todoItem.toString());
            }
        } catch (SQLException e) {
            String errorMessage = "An error occurred while getting all Todo item.";
            throw new MySQLException(errorMessage, e);
        }
        return todoItems;
    }

    @Override
    public TodoItem update(TodoItem todoItem) {
        String insertQuery = "Update TODO_ITEM  set title = ?, description =?, deadline =? , done =? , assignee_id = ? where todo_id = ?";

        try (
                PreparedStatement preparedStatement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, todoItem.getTitle());
            preparedStatement.setString(2, todoItem.getDescription());
            preparedStatement.setDate(3, Date.valueOf(todoItem.getDeadLine()));
            preparedStatement.setBoolean(4, todoItem.isDone());
            if (todoItem.getCreator() != null && todoItem.getCreator().getId() > 0) {
                preparedStatement.setInt(5, todoItem.getCreator().getId());
            } else {
                preparedStatement.setNull(5, Types.INTEGER);
            }
            preparedStatement.setInt(6, todoItem.getId());

            preparedStatement.executeUpdate();
            System.out.println("Todo item updated successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return todoItem;
    }

    @Override
    public boolean deleteById(int id) {
        boolean isDeleted = false;
        try {
            String query = "DELETE FROM todo_item WHERE todo_id = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, id);
            isDeleted = stmt.executeUpdate() > 0;
            System.out.println("Deleted Todo item with id " + id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isDeleted;
    }
}
