package todolist;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import todolist.model.TodoData;
import todolist.model.TodoItem;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Controller {

    private List<TodoItem> items;
    @FXML private TextArea itemTextArea;
    @FXML private ListView<TodoItem> listView;
    @FXML private Label deadlineLabel;

    public void initialize(){

        items = TodoData.getInstance().getItems();
        listView.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                if(newValue != null){
                    TodoItem item = listView.getSelectionModel().getSelectedItem();
                    itemTextArea.setText(item.getTitle());
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    deadlineLabel.setText(formatter.format(item.getDeadline()));
                }
        });
        listView.getItems().setAll(items);
        listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        listView.getSelectionModel().selectFirst();
    }
}
