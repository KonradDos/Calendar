package pl.study.thesis.entity;

import org.springframework.lang.Nullable;
import org.vaadin.stefan.fullcalendar.Entry;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@XmlRootElement
public class Event implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String title;
    @Nullable
    private String description;
    private String color;
    private boolean allDay;
    private LocalDate startDay;
    private LocalDate endDay;
    @Nullable
    private LocalTime startTime;
    @Nullable
    private LocalTime endTime;

    public String getTitle() {
        return title;
    }

    @XmlElement
    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    @XmlElement
    public void setDescription(String description) {
        this.description = description;
    }

    public String getColor() {
        return color;
    }

    @XmlElement
    public void setColor(String color) {
        this.color = color;
    }

    public boolean isAllDay() {
        return allDay;
    }

    @XmlElement
    public void setAllDay(boolean allDay) {
        this.allDay = allDay;
    }

    public LocalDate getStartDay() {
        return startDay;
    }

    @XmlElement
    public void setStartDay(LocalDate startDay) {
        this.startDay = startDay;
    }

    public LocalDate getEndDay() {
        return endDay;
    }

    @XmlElement
    public void setEndDay(LocalDate endDay) {
        this.endDay = endDay;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    @XmlElement
    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    @XmlElement
    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public Long getId() {
        return id;
    }

    @XmlElement
    public void setId(Long id) {
        this.id = id;
    }

    public String getIdAsString() {
        return String.valueOf(id);
    }

    public void setIdFromString(String id) {
        if(!id.isBlank()) this.id = Long.parseLong(id);
    }

    public Entry asEntry() {
        Entry entry = new Entry(this.getId().toString());
        entry.setDescription(this.getDescription());
        entry.setTitle(this.getTitle());
        entry.setColor(this.getColor());
        entry.setAllDay(this.isAllDay());

        if(endDay == null) endDay = startDay;

        if(this.isAllDay()) {
            entry.setStart(this.getStartDay().atStartOfDay());
            entry.setEnd(this.getEndDay().atStartOfDay());
        }
        else {
            entry.setStart(this.getStartDay().atTime(this.getStartTime()));
            entry.setEnd(this.getEndDay().atTime(this.getEndTime()));
        }
        return entry;
    }

    public static Event fromEntry(Entry entry) {
        Event event = new Event();
        event.setId(Long.valueOf(entry.getId()));
        event.setTitle(entry.getTitle());
        if(entry.getDescription() != null) event.setDescription(entry.getDescription());
        event.setColor(entry.getColor());
        event.setAllDay(entry.isAllDay());
        event.setStartDay(entry.getStart().toLocalDate());
        event.setEndDay(entry.getEnd().toLocalDate());
        event.setStartTime(entry.getStart().toLocalTime());
        event.setEndTime(entry.getEnd().toLocalTime());

        return event;
    }
}
