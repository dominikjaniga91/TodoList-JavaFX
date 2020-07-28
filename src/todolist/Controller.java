package todolist;

import todolist.model.TodoItem;
import java.time.LocalDateTime;
import java.util.List;

public class Controller {

    private List<TodoItem> items;

    public void initialize(){
        TodoItem item1 = new TodoItem("Create monthly sales report for boss", null, LocalDateTime.now(), LocalDateTime.now().plusDays(1));
        TodoItem item2 = new TodoItem("Buy some food", "Milk, water, spinach, cheese", LocalDateTime.now(), LocalDateTime.now().plusHours(4));
        TodoItem item3 = new TodoItem("Call to mother", null, LocalDateTime.now(), LocalDateTime.now().plusHours(6));
        TodoItem item4 = new TodoItem("Plan weekend in Tatra mountains", null, LocalDateTime.now(), LocalDateTime.now().plusDays(3));
        TodoItem item5 = new TodoItem("Buy staff for weeding", null, LocalDateTime.now(), LocalDateTime.now().plusDays(9));
        TodoItem item6 = new TodoItem("Clean yur room", null, LocalDateTime.now(), LocalDateTime.now().plusDays(6));

        items = List.of(item1, item2, item3, item4, item5, item6);
    }
}
