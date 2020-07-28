package todolist.model;

import java.time.LocalDateTime;

public class TodoItem {

    private String title;
    private String description;
    private LocalDateTime createDate;
    private LocalDateTime deadline;

    public TodoItem(String title, String description, LocalDateTime createDate, LocalDateTime deadline) {
        this.title = title;
        this.description = description;
        this.createDate = createDate;
        this.deadline = deadline;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }
}
