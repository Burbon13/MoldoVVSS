package tests;

import org.junit.jupiter.api.*;
import tasks.model.ArrayTaskList;
import tasks.model.ModelException;
import tasks.model.Task;
import tasks.services.TasksService;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class TasksServiceTest {

    private Date startDate;
    private Date endDate;

    private Date startDate1;
    private Date endDate1;

    private TasksService tasksService;

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

        ArrayTaskList list=new ArrayTaskList();
        tasksService=new TasksService(list);
    }

    @AfterEach
    public void deleteAll() {
        startDate = null;
        startDate1 = null;
        endDate = null;
        endDate1 = null;
        tasksService=null;
    }

    @Test
    public void TC01_ECP_valid_1() {
        int interval = 120;
        //Task task = new Task("Alergat", startDate, endDate, interval);
        tasksService.add("Alergat", startDate, endDate, interval,true);
        assert tasksService.getObservableList().get(0).getTitle().length() > 0;
        assert tasksService.getObservableList().get(0).getTitle().length() <= 255;
        assert tasksService.getObservableList().get(0).getRepeatInterval() > 0;
        assert tasksService.getObservableList().get(0).getRepeatInterval() < Integer.MAX_VALUE;
        tasksService.delete(new Task("Alergat", startDate, endDate, interval));
    }

    @Test
    public void TC04_ECP_nonValid() {
        int interval = 0;
        Exception exception = assertThrows(ModelException.class, () -> {
            //Task task = new Task("Alergat", startDate, endDate, interval);
            tasksService.add("Alergat", startDate, endDate, interval,true);
        });
        assert exception.getMessage().contains("interval");
    }

    @Test
    public void TC03_ECP_valid() {
        int interval = 20;
        //Task task = new Task("Munca", startDate1, endDate1, interval);
        tasksService.add("Munca", startDate1, endDate1, interval,true);
        assert tasksService.getObservableList().get(0).getTitle().length() > 0;
        assert tasksService.getObservableList().get(0).getTitle().length() <= 255;
        assert tasksService.getObservableList().get(0).getRepeatInterval() > 0;
        assert tasksService.getObservableList().get(0).getRepeatInterval() < Integer.MAX_VALUE;
        tasksService.delete(tasksService.getObservableList().get(0));
    }

    @Test
    public void TCO3_ECP_nonValid() {
        int interval = 20;
        Exception exception = assertThrows(ModelException.class, () -> {
            tasksService.add("", startDate, endDate, interval,true);
        });
        assert exception.getMessage().contains("title");
    }

    @Test
    public void TC03_BVA_valid() {
        int interval = 60;
        tasksService.add("M", startDate1, endDate1, interval,true);
        assert tasksService.getObservableList().get(0).getTitle().equals("M");
        assert tasksService.getObservableList().get(0).getRepeatInterval() == 60;
        tasksService.delete(tasksService.getObservableList().get(0));
    }

    @Test
    public void TC05_BVA_nonValid(){
        int interval = 0;
        Exception exception = assertThrows(ModelException.class, () -> {
           tasksService.add(null, startDate, endDate, interval,true);
        });
        assert exception.getMessage().contains("title");
    }


}
