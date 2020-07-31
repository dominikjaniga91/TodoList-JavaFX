package todolist.model;

import java.time.LocalDate;

public class TodoItem {

    private String title;
    private String description;
    private LocalDate createDate;
    private LocalDate deadline;

    public TodoItem(String title, String description, LocalDate createDate, LocalDate deadline) {
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

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    @Override
    public String toString() {
        return title;
    }
}
