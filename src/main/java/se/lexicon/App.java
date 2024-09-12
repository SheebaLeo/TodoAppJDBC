package se.lexicon;

import se.lexicon.model.Person;

import java.sql.Connection;
import java.time.LocalDate;

import se.lexicon.dao.db.TodoDbConnection;
import se.lexicon.dao.impl.PersonDAOImpl;
import se.lexicon.dao.impl.TodoItemDAOImpl;
import se.lexicon.dao.impl.TodoItemTaskDAOImpl;
import se.lexicon.model.TodoItem;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) {
        Connection connection = TodoDbConnection.getConnection();
        PersonDAOImpl personDAO = new PersonDAOImpl(connection);
        TodoItemDAOImpl todoItemDAO = new TodoItemDAOImpl(connection);
        TodoItemTaskDAOImpl todoItemTaskDAO = new TodoItemTaskDAOImpl();

        Person person = new Person(4, "qsds", "sdsdx" );
         System.out.println( "Hello World!" );
        System.out.println("==========Create person===========");
        personDAO.create(person);
        System.out.println("=============Find Person By Id=============");
        personDAO.findById(1);
        System.out.println("===========Find All=================");
        personDAO.findAll();
        System.out.println("==========Find By Name============");
        personDAO.findByName("Amira");
        System.out.println("==========Update Person===========");
        person.setId(3);
        personDAO.update(person);
        System.out.println("=========Delete Person By Id==========");
        personDAO.deleteById(3);

        TodoItem todoItem = new TodoItem(6,"task4", "description4", LocalDate.parse("2024-12-01"), false, person);
        System.out.println("==========create Todo Item============");
        todoItemDAO.create(todoItem);
        System.out.println("===========Find todo by id============");
        todoItemDAO.findById(5);
        System.out.println("============Find all todo Item============");
        todoItemDAO.findAll();
        System.out.println("============Find By Done status============");
        todoItemDAO.findAllByDoneStatus(false);
        System.out.println("============Find by assignee id============");
        todoItemDAO.findByAssigneeId(2);
        System.out.println("============Find by assignee object============");
        todoItemDAO.findByAssignee(person);
        System.out.println("============Find by unassignee todo items============");
        todoItemDAO.findByUnassignedTodoItems();
        System.out.println("============update todo items============");
        todoItemDAO.update(todoItem);
        System.out.println("============delete by todo id============");
        todoItemDAO.deleteById(6);


    }
}
