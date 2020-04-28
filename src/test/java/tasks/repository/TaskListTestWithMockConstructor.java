package tasks.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import tasks.model.ArrayTaskList;
import tasks.model.CapacityWrapper;
import tasks.model.Task;
import tasks.model.TaskList;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskListTestWithMockConstructor {
    private static final int CAPACITY = 20;

    private ArrayTaskList arrayTaskList;
    private Date startDate, endDate;
    private Calendar calendar;

    private CapacityWrapper capacityWrapper;

    @BeforeEach
    void setUp() {
        capacityWrapper = mock(CapacityWrapper.class);
        Mockito.when(capacityWrapper.getCapacity()).thenReturn(CAPACITY);
        arrayTaskList = new ArrayTaskList(capacityWrapper);

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
    public void getAllTasksTest() {
        Task task1 = new Task("Read a book", startDate, endDate, 3600);
        arrayTaskList.add(task1);
        Mockito.verify(capacityWrapper, times(1)).getCapacity();

        assert true;
        assertEquals(arrayTaskList.getAll().size(), 1);
    }

    @Test
    public void addTaskToTaskListTest() {
        Task task1 = new Task("Read a book", startDate, endDate, 3600);
        Task task2 = new Task("Watch a good movie", startDate);

        arrayTaskList.add(task1);
        assertEquals(arrayTaskList.getAll().size(), 1);

        arrayTaskList.add(task2);
        assertEquals(arrayTaskList.getAll().size(), 2);

        Mockito.verify(capacityWrapper, times(1)).getCapacity();
        assert true;
    }
}
