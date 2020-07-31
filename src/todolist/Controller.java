package todolist;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import todolist.model.TodoData;
import todolist.model.TodoItem;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

public class Controller {

    private List<TodoItem> items;
    @FXML private TextArea itemTextArea;
    @FXML private ListView<TodoItem> listView;
    @FXML private Label deadlineLabel;
    @FXML private BorderPane mainBorderPane;

    public void initialize(){

        items = TodoData.getInstance().getItems();
        listView.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                if(newValue != null){
                    TodoItem item = listView.getSelectionModel().getSelectedItem();
                    itemTextArea.setText(item.getTitle());
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    deadlineLabel.setText(formatter.format(item.getDeadline()));
                }
        });
        listView.getItems().setAll(items);
        listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        listView.getSelectionModel().selectFirst();
    }

    @FXML
    public void showNewItemDialog(){
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainBorderPane.getScene().getWindow());
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("todoitemDialog.fxml"));
        try {
            dialog.getDialogPane().setContent(loader.load());
        } catch (IOException ex) {
            System.out.println("Couldn't load the dialog");
            ex.printStackTrace();
        }
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            DialogController controller = loader.getController();
            TodoItem newItem = controller.createNewTodoItem();
            listView.getItems().setAll(items);
            listView.getSelectionModel().select(newItem);
            System.out.println("Ok pressed");
        } else {
            System.out.println("Cancel pressed");
        }
    }
}
