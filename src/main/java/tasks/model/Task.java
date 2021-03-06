package tasks.model;

import org.apache.log4j.Logger;
import tasks.utils.TaskIO;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Task implements Serializable {
    private String title;
    private Date currentTime;
    private Date startTime;
    private Date endTime;
    private int interval;
    private boolean active;

    private static final Logger log = Logger.getLogger(Task.class.getName());
    private static final String SIMPLE_DATE_FORMAT = "yyyy-MM-dd HH:mm";

    public static SimpleDateFormat getDateFormat() {
        return new SimpleDateFormat(SIMPLE_DATE_FORMAT);
    }

    public Task(String title, Date currentTime) {
        if (title.length() > 255 || title.length() == 0) {
            log.error("invalid title");
            throw new ModelException("invalid title");
        }
        if (currentTime == null) {
            log.error("currentTime is null");
            throw new ModelException("currentTime cannot be null");
        }
        if (currentTime.getTime() < 0) {
            log.error("time below bound");
            throw new ModelException("Time cannot be negative");
        }
        this.title = title;
        this.currentTime = currentTime;
        this.startTime = currentTime;
        this.endTime = currentTime;
    }

    public Task(String title, Date startTime, Date endTime, int interval) {
        if (title==null || title.length() > 255 || title.length() == 0) {
            log.error("invalid title");
            throw new ModelException("invalid title");
        }
        if (startTime == null || endTime == null) {
            log.error("startTime or endTime cannot be null");
            throw new ModelException("startTime or endTime cannot be null");
        }
        if (startTime.getTime() < 0 || endTime.getTime() < 0) {
            log.error("time below bound");
            throw new ModelException("Time cannot be negative");
        }
        if (interval == 0) {
            log.error("interval is 0");
            throw new ModelException("interval should be >= 1");
        }
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
        this.interval = interval;
        this.currentTime = startTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isActive() {
        return this.active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Date getTime() {
        return currentTime;
    }

    public void setTime(Date currentTime) {
        this.currentTime = currentTime;
        this.startTime = currentTime;
        this.endTime = currentTime;
        this.interval = 0;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public int getRepeatInterval() {
        return interval > 0 ? interval : 0;
    }

    public void setTime(Date startTime, Date endTime, int interval) {
        this.currentTime = startTime;
        this.startTime = startTime;
        this.endTime = endTime;
        this.interval = interval;

    }

    public boolean isRepeated() {
        return this.interval != 0;

    }

    public Date nextTimeAfter(Date current) {
        if (current.after(endTime) || current.equals(endTime)) return null;
        if (isRepeated() && isActive()) {
            Date timeBefore = startTime;
            Date timeAfter = startTime;
            if (current.before(startTime)) {
                return startTime;
            }
            if ((current.after(startTime) || current.equals(startTime)) && (current.before(endTime) || current.equals(endTime))) {
                for (long i = startTime.getTime(); i <= endTime.getTime(); i += interval * 1000) {
                    if (current.equals(timeAfter)) return new Date(timeAfter.getTime() + interval * 1000);
                    if (current.after(timeBefore) && current.before(timeAfter)) return timeBefore;//return timeAfter
                    timeBefore = timeAfter;
                    timeAfter = new Date(timeAfter.getTime() + interval * 1000);
                }
            }
        }
        if (!isRepeated() && current.before(currentTime) && isActive()) {
            return currentTime;
        }
        return null;
    }

    //duplicate methods for TableView which sets column
    // value by single method and doesn't allow passing parameters
    public String getFormattedDateStartTime() {
        return getDateFormat().format(startTime);
    }

    public String getFormattedDateEndTime() {
        return getDateFormat().format(endTime);
    }

    public String getFormattedRepeated() {
        if (isRepeated()) {
            String formattedInterval = TaskIO.getFormattedInterval(interval);
            return "Every " + formattedInterval;
        } else {
            return "No";
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Task task = (Task) o;

        if (!currentTime.equals(task.currentTime)) return false;
        if (!startTime.equals(task.startTime)) return false;
        if (!endTime.equals(task.endTime)) return false;
        if (interval != task.interval) return false;
        if (active != task.active) return false;
        return title.equals(task.title);
    }

    @Override
    public int hashCode() {
        int result = title.hashCode();
        result = 31 * result + currentTime.hashCode();
        result = 31 * result + startTime.hashCode();
        result = 31 * result + endTime.hashCode();
        result = 31 * result + interval;
        result = 31 * result + (active ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Task{" +
                "title='" + title + '\'' +
                ", time=" + currentTime +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", interval=" + interval +
                ", active=" + active +
                '}';
    }
}


