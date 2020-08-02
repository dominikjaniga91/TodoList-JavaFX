package todolist;

import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import todolist.model.TodoData;
import todolist.model.TodoItem;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Optional;

public class Controller {

    @FXML private TextArea itemTextArea;
    @FXML private ListView<TodoItem> listView;
    @FXML private Label deadlineLabel;
    @FXML private BorderPane mainBorderPane;
    @FXML private ContextMenu contextMenu;
    @FXML private ToggleButton filterToggleButton;

    public void initialize() {

        contextMenu = createContextMenu();

        listView.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        TodoItem item = listView.getSelectionModel().getSelectedItem();
                        itemTextArea.setText(item.getTitle());
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                        deadlineLabel.setText(formatter.format(item.getDeadline()));
                    }
                });
        listView.setItems(TodoData.getInstance().getItems());
        listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        listView.getSelectionModel().selectFirst();
        listView.setCellFactory(todoItemListView -> {

            ListCell<TodoItem> cell = new ListCell<>() {
                @Override
                protected void updateItem(TodoItem todoItem, boolean empty) {
                    super.updateItem(todoItem, empty);
                    if (empty) {
                        setText(null);
                    } else {
                        setText(todoItem.getTitle());
                        if (deadlineDateIsTodayOrPast(todoItem)) {
                            setTextFill(Color.RED);
                        }
                    }
                }
            };

            cell.emptyProperty().addListener((observable, wasEmpty, isEmpty) -> {
                if (isEmpty) {
                    cell.setContextMenu(null);
                } else {
                    cell.setContextMenu(contextMenu);
                }
            });

            return cell;
        });
    }

    private boolean deadlineDateIsTodayOrPast(TodoItem todoItem){
        return todoItem.getDeadline().isBefore(LocalDate.now().plusDays(1));
    }

    @FXML
    public void selectTodayEvent(){
        if (filterToggleButton.isSelected()){
            FilteredList<TodoItem> filteredList = new FilteredList<>(TodoData.getInstance().getItems(), item -> item.getDeadline().equals(LocalDate.now()));
            listView.setItems(filteredList);
            listView.getSelectionModel().selectFirst();
        } else {
            listView.setItems(TodoData.getInstance().getItems());
        }
    }
    @FXML
    public void sortItemsByDeadline(){
        SortedList<TodoItem> sortedList = new SortedList<>(TodoData.getInstance().getItems(),
                Comparator.comparing(TodoItem::getDeadline));
        listView.setItems(sortedList);
    }

    @FXML
    public void handleDeleteKeyPressed(KeyEvent event){
        TodoItem selectedItem = listView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            if (event.getCode().equals(KeyCode.DELETE)) {
                deleteItem(selectedItem);
            }
        }
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
            listView.getSelectionModel().select(newItem);
        }
    }

    public void deleteItem(TodoItem item){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete todo item");
        alert.setHeaderText("Delete item: " + item.getTitle() );
        alert.setContentText("Are you sure? ");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && (result.get() == ButtonType.OK)) {
            TodoData.getInstance().deleteTodoItem(item);
        }
    }

    private ContextMenu createContextMenu(){

        MenuItem deleteMenuItem = getDeleteOption();
        ContextMenu contextMenu = new ContextMenu();
        contextMenu.getItems().addAll(deleteMenuItem);
        return contextMenu;
    }

    private MenuItem getDeleteOption(){

        MenuItem deleteMenuItem = new MenuItem("Delete");
        deleteMenuItem.setOnAction(actionEvent -> {
            TodoItem item = listView.getSelectionModel().getSelectedItem();
            deleteItem(item);
        });

        return deleteMenuItem;
    }



}
