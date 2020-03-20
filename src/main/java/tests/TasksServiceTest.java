package tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.model.ModelException;
import tasks.model.Task;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class TasksServiceTest {

    private Date startDate;
    private Date endDate;

    @BeforeEach
    public void initDate(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(2020, 3, 19, 8, 20, 0);
        startDate = calendar.getTime();
        calendar.set(2020, 3, 25, 8, 20, 0);
        endDate = calendar.getTime();
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
    public void TC04_ECP_nonValid(){
        int interval = 0;
        Exception exception = assertThrows(ModelException.class, () -> {
            Task task = new Task("Alergat", startDate, endDate, interval);
        });
        assert exception.getMessage().contains("interval");
    }
}
