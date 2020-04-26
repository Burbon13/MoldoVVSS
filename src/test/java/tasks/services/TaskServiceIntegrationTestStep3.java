package tasks.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.model.ArrayTaskList;
import tasks.model.CapacityWrapper;
import tasks.model.Task;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;


class TaskServiceIntegrationTestStep3 {
    private static final int CAPACITY = 20;

    private CapacityWrapper capacityWrapper;
    private ArrayTaskList arrayTaskList;
    private TasksService tasksService;
    private Date startDate, endDate;

    @BeforeEach
    void setUp() {
        capacityWrapper = new CapacityWrapper(CAPACITY);
        arrayTaskList = new ArrayTaskList(capacityWrapper);
        tasksService = new TasksService(arrayTaskList);

        Calendar calendar = Calendar.getInstance();
        calendar.set(2020, 3, 19, 8, 20, 0);
        startDate = calendar.getTime();
        calendar.set(2020, 3, 25, 8, 20, 0);
        endDate = calendar.getTime();
    }

    @Test
    public void getSizeTaskServiceTest() {
        Task task1 = new Task("Go to the gym", startDate, endDate, 120000);

        tasksService.add(task1.getTitle(), task1.getStartTime(), task1.getEndTime(), task1.getRepeatInterval(), false);

        assertEquals(1, arrayTaskList.getAll().size());
    }

    @Test
    public void getAllTaskServiceTest() {
        Task task1 = new Task("Go to the gym", startDate, endDate, 120000);

        tasksService.add(task1.getTitle(), task1.getStartTime(), task1.getEndTime(), task1.getRepeatInterval(), true);
        tasksService.delete(task1);

        assert true;
        assertEquals(Collections.emptyList(), arrayTaskList.getAll());
    }
}
