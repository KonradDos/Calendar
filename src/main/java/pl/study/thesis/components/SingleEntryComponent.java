package pl.study.thesis.components;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.vaadin.stefan.fullcalendar.Entry;
import pl.study.thesis.entity.Event;
import pl.study.thesis.dao.EventDao;
import pl.study.thesis.handler.RepoHandler;

import java.time.LocalDateTime;

@Component
@Scope("prototype")
public class SingleEntryComponent extends Dialog {

    @Autowired
    EventDao eventRepository;

    protected static final String FIELD_REQUIRED_ERROR_MESSAGE = "Field required!";

    protected final TextField ID = new TextField();
    protected final Label componentLabel = new Label("Add new event");
    protected final TextField title = new TextField("Title");
    protected final ComboBox<String> colors = new ComboBox<>("Red", "Yellow", "Green", "Orange", "Purple");
    protected final TextArea description = new TextArea("Description");
    protected final Checkbox allDay = new Checkbox("All day even", true);
    protected final DatePicker startDay = new DatePicker("Start");
    protected final DatePicker endDay = new DatePicker("Start");
    protected final TimePicker startTime = new TimePicker("Start time");
    protected final TimePicker endTime = new TimePicker("End time");
    protected final Button save = new Button("Save");

    protected final Binder<Event> binder = new Binder<>(Event.class);

    public SingleEntryComponent() {}

    public SingleEntryComponent(LocalDateTime startDate) {
        this.startDay.setValue(startDate.toLocalDate());
        add(componentLabel, title, colors, description, allDay, startDay, startTime, endTime, save);
        setIds();
        setStyles();
        setListeners();
        setBindings();
    }

    protected void setIds() {
        title.setId("title_input");
        colors.setId("color_select");
        description.setId("dsc_input");
        allDay.setId("allDay_checkbox");
        startDay.setId("startDay_datepicker");
        endDay.setId("endDay_datepicker");
        startTime.setId("startTime_timepicker");
        endTime.setId("endTime_timepicker");
        save.setId("save_button");
    }

    protected void setBindings() {
        binder.forField(ID)
                .bind(Event::getIdAsString, Event::setIdFromString);

        binder.forField(title)
                .asRequired(FIELD_REQUIRED_ERROR_MESSAGE)
                .bind(Event::getTitle, Event::setTitle);

        binder.forField(description)
                .bind(Event::getDescription, Event::setDescription);

        binder.forField(allDay)
                .bind(Event::isAllDay, Event::setAllDay);

        binder.forField(colors)
                .asRequired(FIELD_REQUIRED_ERROR_MESSAGE)
                .bind(Event::getColor, Event::setColor);

        binder.forField(startDay)
                .asRequired(FIELD_REQUIRED_ERROR_MESSAGE)
                .bind(Event::getStartDay, Event::setStartDay);

        binder.forField(startDay)
                .asRequired(FIELD_REQUIRED_ERROR_MESSAGE)
                .bind(Event::getStartDay, Event::setStartDay);

        binder.forField(startTime)
                .bind(Event::getStartTime, Event::setStartTime);

        binder.forField(endTime)
                .bind(Event::getEndTime, Event::setEndTime);

    }

    protected void setListeners() {
        allDay.addClickListener(this::allDayTimeFieldsVisibilityListener);
        allDay.addClickListener(this::allDayBinderListener);
        save.addClickListener(this::saveDataClickListener);
    }

    protected void setStyles() {
        setWidth("400px");
        setHeight("600px");

        componentLabel.getStyle().set("font-weight", "bold");
        componentLabel.getStyle().set("font-size", "large");
        colors.setLabel("Colors");
        colors.setMinWidth("100%");
        title.setMinWidth("100%");
        description.setMinWidth("100%");
        description.setMaxLength(255);
        startDay.setMinWidth("100%");
        startTime.setMinWidth("100%");
        startTime.setVisible(false);
        endTime.setMinWidth("100%");
        endTime.setVisible(false);
        allDay.setWidth("100%");
        save.setWidth("100%");
        endDay.setWidth("100%");
        endDay.setVisible(true);
    }

    protected void allDayTimeFieldsVisibilityListener(ClickEvent<Checkbox> clickEvent) {
        if(allDay.getValue()) {
            endTime.setVisible(false);
            startTime.setVisible(false);
        }
        else {
            endTime.setVisible(true);
            startTime.setVisible(true);
        }
    }

    protected void allDayBinderListener(ClickEvent<Checkbox> clickEvent) {
        if(!allDay.getValue()) {
            binder.forField(endTime)
                    .asRequired(FIELD_REQUIRED_ERROR_MESSAGE)
                    .bind(Event::getEndTime, Event::setEndTime);

            binder.forField(startTime)
                    .asRequired(FIELD_REQUIRED_ERROR_MESSAGE)
                    .bind(Event::getStartTime, Event::setStartTime);
        }
        else {
            binder.removeBinding(endTime);
            binder.removeBinding(startTime);
        }
    }

    protected void saveDataClickListener(ClickEvent<Button> clickEvent){
        binder.validate();
        if(binder.isValid()) {
            Event event = new Event();
            try {
                binder.writeBean(event);
            } catch (ValidationException e) {
                e.printStackTrace();
            }
            // TODO: 19.12.2020 FIX lines below
//            eventRepository.save(event);
            RepoHandler.getEventDao().save(event);

            VaadinSession.getCurrent().setAttribute(Entry.class, event.asEntry());
            this.close();
        }
    }

    protected Entry getEventAsEntry() {
        Event event = binder.getBean();
        return event.asEntry();
    }

}
