package tasks;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.model.Task;
import tasks.model.TasksOperations;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


@SuppressWarnings("Duplicates")
class TasksOperationsTest {

    private ObservableList<Task> tasks;
    private Calendar calendar;
    private TasksOperations tasksOperations;
    private List<Task> all;
    private Date startDate;
    private Date stopDate;
    private Task task1;
    private Task task2;


    @BeforeEach
    public void beforeEachTest() {
        calendar = Calendar.getInstance();
        calendar.set(2020, 4, 10, 8, 20, 0);
        startDate = calendar.getTime();
        calendar.set(2020, 4, 11, 8, 20, 0);
        stopDate = calendar.getTime();
        task2 = new Task("Task2", startDate, stopDate, 2);
        all = new ArrayList<>();
        calendar.set(2020, 4, 3, 8, 20, 0);
        startDate = calendar.getTime();
        calendar.set(2020, 4, 8, 8, 20, 0);
        stopDate = calendar.getTime();
        task1 = new Task("Task1", startDate, stopDate, 2);
    }

    @Test
    public void FO2_TCO1_WHEN_NoTasks_THEN_EmptyListReturned() {
        tasks = FXCollections.observableArrayList(all);
        tasksOperations = new TasksOperations(tasks);
        Iterable<Task> result = tasksOperations.incoming(startDate, stopDate);
        result.forEach(x -> {
            assert false;
        });
        assert true;
    }

    @Test
    public void FO2_TCO2_WHEN_OneTaskInInterval_THEN_OneTaskReturned() {
        calendar.set(2020, 4, 11, 8, 20, 0);
        all.add(task1);
        tasks = FXCollections.observableArrayList(all);
        tasksOperations = new TasksOperations(tasks);
        Iterable<Task> result = tasksOperations.incoming(startDate, stopDate);
        result.forEach(x -> {
            assert x.getTitle().equals("Task1");
        });
        assert true;
    }

    @Test
    public void F02_TC03_WHEN_TwoTasksSaved_AND_OneIsInInterval_THEN_OneTaskReturned() {
        all.add(task1);
        all.add(task2);
        tasks = FXCollections.observableArrayList(all);
        tasksOperations = new TasksOperations(tasks);
        Iterable<Task> result = tasksOperations.incoming(startDate, stopDate);
        result.forEach(x -> {
            assert x.getTitle().equals("Task1");
        });
        assert true;
    }

    @Test
    public void F02_TC04_WHEN_NoTaskInInterval_THEN_NoTasksReturned() {
        task2.setStartTime(null);
        calendar.set(2020, 5, 20, 8, 20, 0);
        startDate = calendar.getTime();
        calendar.set(2020, 5, 22, 8, 20, 0);
        stopDate = calendar.getTime();
        all.add(task1);
        all.add(task2);
        tasks = FXCollections.observableArrayList(all);
        tasksOperations = new TasksOperations(tasks);
        Iterable<Task> result = tasksOperations.incoming(startDate, stopDate);
        result.forEach(x -> {
            assert false;
        });
        assert true;
    }
}
