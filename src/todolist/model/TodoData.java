package todolist.model;

import javafx.collections.FXCollections;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TodoData {

    private static TodoData instance = new TodoData();
    private static String fileName = "TodoListItems.txt";
    private List<TodoItem> items;

    public static TodoData getInstance(){
        return instance;
    }
    private TodoData(){

    }

    public List<TodoItem> getItems() {
        return items;
    }

    public void setItems(List<TodoItem> items) {
        this.items = items;
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
                LocalDateTime created = LocalDateTime.parse(fileItems[2]);
                LocalDateTime deadline = LocalDateTime.parse(fileItems[3]);
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
}
