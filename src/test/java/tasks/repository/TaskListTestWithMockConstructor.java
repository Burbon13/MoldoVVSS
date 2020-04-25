package tasks.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import tasks.model.Task;
import tasks.model.TaskList;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskListTestWithMockConstructor {

    private TaskList taskList;
    private Date startDate, endDate;
    private Calendar calendar;

    @BeforeEach
    void setUp() {
        taskList = mock(TaskList.class);

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
        Mockito.when(taskList.getAll()).thenReturn(Arrays.asList(task1));
        Mockito.verify(taskList, never()).getAll();

        assert true;
        assertEquals(taskList.getAll().size(), 1);
        Mockito.verify(taskList, times(1)).getAll();
    }

    @Test
    public void addTaskToTaskListTest() {
        Task task1 = new Task("Read a book", startDate, endDate, 3600);
        Task task2 = new Task("Watch a good movie", startDate);

        Mockito.doAnswer((Answer<Void>) invocation -> {
            Object[] arguments = invocation.getArguments();
            if (arguments != null && arguments.length == 1 && arguments[0] != null) {
                Task t = (Task) arguments[0];
                assertEquals(t.getRepeatInterval(), 3600);
                assertEquals(t.getTitle(), "Read a book");
            }
            return null;
        }).when(taskList).add(task1);

        taskList.add(task1);
        taskList.add(task2);

        Mockito.verify(taskList, times(1)).add(task1);
        Mockito.verify(taskList, times(1)).add(task2);
        assert true;
    }
}
