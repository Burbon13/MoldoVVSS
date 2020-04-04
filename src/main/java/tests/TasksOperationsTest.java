package tests;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.model.ArrayTaskList;
import tasks.model.Task;
import tasks.model.TasksOperations;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class TasksOperationsTest {

    ObservableList<Task> tasks;
    TasksOperations tasksOperations;

    @Test
    public void F02_TC03(){
        ArrayList<Task> all=new ArrayList();
        Calendar calendar = Calendar.getInstance();
        calendar.set(2020, 4, 4, 8, 20, 0);
        Date startDate = calendar.getTime();
        calendar.set(2020, 4, 5, 8, 20, 0);
        Date stopDate= calendar.getTime();
        Task task1=new Task("Task1",startDate,stopDate,2);
        calendar.set(2020, 4, 10, 8, 20, 0);
        startDate = calendar.getTime();
        calendar.set(2020, 4, 11, 8, 20, 0);
        stopDate= calendar.getTime();
        Task task2=new Task("Task2",startDate,stopDate,2);
        all.add(task1);
        all.add(task2);
        tasks= FXCollections.observableArrayList(all);
        tasksOperations=new TasksOperations(tasks);
        calendar.set(2020, 4, 3, 8, 20, 0);
        startDate = calendar.getTime();
        calendar.set(2020, 4, 8, 8, 20, 0);
        stopDate= calendar.getTime();
        Iterable<Task> result=tasksOperations.incoming(startDate,stopDate);
        result.forEach(x->{
            if(!x.getTitle().equals("Task1"))
                assert false;
        });
        assert true;
    }

    @Test
    public void F02_TC04(){
        ArrayList<Task> all=new ArrayList();
        Calendar calendar = Calendar.getInstance();
        calendar.set(2020, 4, 1, 8, 20, 0);
        Date startDate = calendar.getTime();
        calendar.set(2020, 4, 5, 8, 20, 0);
        Date stopDate= calendar.getTime();
        Task task1=new Task("Task1",startDate,stopDate,2);
        calendar.set(2020, 4, 10, 8, 20, 0);
        startDate = calendar.getTime();
        calendar.set(2020, 4, 11, 8, 20, 0);
        stopDate= calendar.getTime();
        Task task2=new Task("Task2",startDate,stopDate,2);
        task2.setStartTime(null);
        all.add(task1);
        all.add(task2);
        tasks= FXCollections.observableArrayList(all);
        tasksOperations=new TasksOperations(tasks);
        calendar.set(2020, 4, 3, 8, 20, 0);
        startDate = calendar.getTime();
        calendar.set(2020, 4, 8, 8, 20, 0);
        stopDate= calendar.getTime();
        Iterable<Task> result=tasksOperations.incoming(startDate,stopDate);
        result.forEach(x->{
            if(x.getTitle().equals("Task1") || x.getTitle().equals("Task2"))
                assert false;
        });
        assert true;
    }


}