package se.lexicon.model;

import java.time.LocalDate;
import java.util.Objects;

public class TodoItem {
    private int id;

    private String title;

    private String taskDescription;

    private LocalDate deadLine;

    private boolean done;

    private Person creator;

    public TodoItem(int id, String title, String taskDescription, LocalDate deadLine, boolean done, Person creator) {

        if (title == null || title.isEmpty()) {
            throw new IllegalArgumentException("Title cannot be null or empty");
        }
        if (deadLine == null) {
            throw new IllegalArgumentException("Deadline cannot be null");
        }

        this.id = id;
        this.title = title;
        this.taskDescription = taskDescription;
        this.deadLine = deadLine;
        this.done = done;
        this.creator = creator;
    }

    public TodoItem(String title, String taskDescription, LocalDate deadLine, boolean done, Person creator) {

        if (title == null || title.isEmpty()) {
            throw new IllegalArgumentException("Title cannot be null or empty");
        }
        if (deadLine == null) {
            throw new IllegalArgumentException("Deadline cannot be null");
        }
        this.title = title;
        this.taskDescription = taskDescription;
        this.deadLine = deadLine;
        this.done = done;
        this.creator = creator;
    }

    public TodoItem() {

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (title == null || title.isEmpty()) {
            throw new IllegalArgumentException("Title cannot be null or empty");
        }
        this.title = title;
    }

    public String getDescription() {
        return taskDescription;
    }

    public void setDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public LocalDate getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(LocalDate deadLine) {
        if (deadLine == null) {
            throw new IllegalArgumentException("Deadline cannot be null");
        }
        this.deadLine = deadLine;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public Person getCreator() {
        return creator;
    }

    public void setCreator(Person creator) {
        this.creator = creator;
    }

    /*public String getSummary() {
        return "TodoItem ID: " + id + " ," +
                "Title: " + title + ", " +
                "Description: " + taskDescription + ", " +
                "Deadline: " + deadLine + ", " +
                "Done: " + done + ", " +
                "Creator: " + creator;
    }*/

    // Method to check if the TodoItem is overdue
    public boolean isOverdue() {
        return LocalDate.now().isAfter(deadLine);
    }

    @Override
    public String toString() {
        String todotask = "TodoItem{" +
                "todo id= " + id +
                ", title= '" + title + '\'' +
                ", description= " + taskDescription + '\'' +
                ", done= " + done +
                ", deadline= " + deadLine +
                '}';
        String assignee = null;
        if (creator != null) {
            assignee = "assignee{" +
                    " assignee id= " + creator.getId() +
                    ", assignee first name= " + creator.getFirstName() +
                    ", assignee lastName= " + creator.getLastName() +
                    '}';

        } else {
            assignee = "assignee{" + creator +"}";
        }
        return todotask + assignee;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        TodoItem todoItem = (TodoItem) obj;
        return done == todoItem.done &&
                Objects.equals(title, todoItem.title) &&
                Objects.equals(taskDescription, todoItem.taskDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, taskDescription, done);
    }
}
