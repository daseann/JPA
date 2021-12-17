package model;
import jakarta.persistence.*;
import java.time.LocalTime;

@Entity
@Table(uniqueConstraints={
        @UniqueConstraint(columnNames = {"daysOfWeek", "startTime","endtime"})
})
public class TimeSlot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public byte daysOfWeek;
    public LocalTime startTime;
    public LocalTime endTime;

    public TimeSlot() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte getDaysOfWeek() {
        return daysOfWeek;
    }

    public void setDaysOfWeek(byte daysOfWeek) {
        this.daysOfWeek = daysOfWeek;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "TimeSlot{" +
                "id=" + id +
                ", daysOfWeek=" + daysOfWeek +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
