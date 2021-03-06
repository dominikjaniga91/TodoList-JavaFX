package todolist.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;

public class TodoData {

    private static TodoData instance = new TodoData();
    private static String fileName = "TodoListItems.txt";
    private ObservableList<TodoItem> items;

    public static TodoData getInstance(){
        return instance;
    }

    private TodoData(){
    }

    public ObservableList<TodoItem> getItems() {
        return items;
    }

    public void addTodoItem(TodoItem todoItem) {
        items.add(todoItem);
    }

    public void loadTodoItemsFromFile() {
        items = FXCollections.observableArrayList();
        Path path = Paths.get(fileName);
        String line;
        try(BufferedReader reader = Files.newBufferedReader(path)) {
            while ((line = reader.readLine()) != null){
                String[] fileItems = line.split("\t");
                String title = fileItems[0];
                String description = fileItems[1];
                LocalDate created = LocalDate.parse(fileItems[2]);
                LocalDate deadline = LocalDate.parse(fileItems[3]);
                TodoItem item = new TodoItem(title, description, created, deadline);
                items.add(item);
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void storeTodoItemsInFile(){
        Path path = Paths.get(fileName);

        try(BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (TodoItem item : items) {
                writer.write(String.format("%s\t%s\t%s\t%s", item.getTitle(),
                        item.getDescription(),
                        item.getCreateDate(),
                        item.getDeadline()));
                writer.newLine();
            }
        } catch (IOException ex){
            System.out.println(ex.getMessage());
        }
    }

    public void deleteTodoItem(TodoItem item) {
        items.remove(item);
    }
}
