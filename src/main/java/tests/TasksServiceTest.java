package tests;

import org.junit.jupiter.api.*;
import tasks.model.ModelException;
import tasks.model.Task;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class TasksServiceTest {

    private Date startDate;
    private Date endDate;

    private Date startDate1;
    private Date endDate1;

    @BeforeAll
    public static void startTest(){
        System.out.println("Start TasksServiceTest <======");
    }

    @AfterAll
    public static void stopTest(){
        System.out.println("Stop TasksServiceTest ======>");
    }

    @BeforeEach
    public void initDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2020, 3, 19, 8, 20, 0);
        startDate = calendar.getTime();
        calendar.set(2020, 3, 25, 8, 20, 0);
        endDate = calendar.getTime();

        calendar.set(2021, 3, 20, 8, 20, 0);
        startDate1 = calendar.getTime();
        calendar.set(2021, 3, 25, 8, 20, 0);
        endDate1 = calendar.getTime();
    }

    @AfterEach
    public void deleteAll() {
        startDate = null;
        startDate1 = null;
        endDate = null;
        endDate1 = null;
    }

    @Test
    public void TC01_ECP_valid_1() {
        int interval = 120;
        Task task = new Task("Alergat", startDate, endDate, interval);
        assert task.getTitle().length() > 0;
        assert task.getTitle().length() <= 255;
        assert task.getRepeatInterval() > 0;
        assert task.getRepeatInterval() < Integer.MAX_VALUE;
    }

    @Test
    public void TC04_ECP_nonValid() {
        int interval = 0;
        Exception exception = assertThrows(ModelException.class, () -> {
            Task task = new Task("Alergat", startDate, endDate, interval);
        });
        assert exception.getMessage().contains("interval");
    }

    @Test
    public void TC03_ECP_valid() {
        int interval = 20;
        Task task = new Task("Munca", startDate1, endDate1, interval);
        assert task.getTitle().length() > 0;
        assert task.getTitle().length() <= 255;
        assert task.getRepeatInterval() > 0;
        assert task.getRepeatInterval() < Integer.MAX_VALUE;
    }

    @Test
    public void TCO3_ECP_nonValid() {
        int interval = 20;
        Exception exception = assertThrows(ModelException.class, () -> {
            Task task = new Task("", startDate, endDate, interval);
        });
        assert exception.getMessage().contains("title");
    }

    @Test
    public void TC03_BVA_valid() {
        int interval = 60;
        Task task = new Task("M", startDate1, endDate1, interval);
        assert task.getTitle().equals("M");
        assert task.getRepeatInterval() == 60;
    }

    @Test
    public void TC05_BVA_nonValid(){
        int interval = 0;
        Exception exception = assertThrows(ModelException.class, () -> {
            Task task = new Task(null, startDate, endDate, interval);
        });
        assert exception.getMessage().contains("title");
    }


}
