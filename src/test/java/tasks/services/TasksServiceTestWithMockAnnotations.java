package tasks.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;
import tasks.model.ArrayTaskList;
import tasks.model.Task;
import tasks.model.TaskList;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class TasksServiceTestWithMockAnnotations {

    @Mock
    private ArrayTaskList taskList;

    @InjectMocks
    private TasksService tasksService;

    private Date startDate, endDate;
    private Calendar calendar;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        calendar = Calendar.getInstance();
        calendar.set(2020, 3, 19, 8, 20, 0);
        startDate = calendar.getTime();
        calendar.set(2020, 3, 25, 8, 20, 0);
        endDate = calendar.getTime();
    }

    @AfterEach
    void tearDown() {
        calendar = null;
        startDate = null;
        endDate = null;
    }

    @Test
    public void getSizeTaskServiceTest() {
        Task task1 = new Task("Go to the gym", startDate, endDate, 120000);
        Task task2 = new Task("Buy drinks", startDate, endDate, 5000);

        Mockito.doNothing().when(taskList).add(task1);
        Mockito.doNothing().when(taskList).add(task2);
        Mockito.verify(taskList, Mockito.never()).add(task2);

        tasksService.add(task1.getTitle(), task1.getStartTime(), task1.getEndTime(), task1.getRepeatInterval(), false);
        tasksService.delete(task1);

        assertEquals(taskList.getAll().size(), 0);
    }

    @Test
    public void getAllTaskServiceTest() {
        Task task1 = new Task("Go to the gym", startDate, endDate, 120000);
        Mockito.doNothing().when(taskList).add(task1);

        tasksService.add(task1.getTitle(), task1.getStartTime(), task1.getEndTime(), task1.getRepeatInterval(), false);
        tasksService.delete(task1);

        assert true;
        assertEquals(taskList.getAll(), Collections.emptyList());
    }


}
