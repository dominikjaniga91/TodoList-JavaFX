package todolist;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import todolist.model.TodoData;
import todolist.model.TodoItem;

import java.time.LocalDate;

public class DialogController {

    @FXML private TextField titleField;
    @FXML private TextArea descriptionArea;
    @FXML private DatePicker deadlinePicker;

    public TodoItem createNewTodoItem(){
        String title = titleField.getText().trim();
        String description = descriptionArea.getText().trim();
        LocalDate created = LocalDate.now();
        LocalDate deadline = deadlinePicker.getValue();
        TodoItem newItem = new TodoItem(title, description, created, deadline);
        TodoData.getInstance().addTodoItem(newItem);

        return newItem;
    }
}
