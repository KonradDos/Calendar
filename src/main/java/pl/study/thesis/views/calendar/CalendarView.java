package pl.study.thesis.views.calendar;

import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.router.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.vaadin.stefan.fullcalendar.CalendarViewImpl;
import org.vaadin.stefan.fullcalendar.Entry;
import org.vaadin.stefan.fullcalendar.FullCalendar;
import org.vaadin.stefan.fullcalendar.FullCalendarBuilder;
import pl.study.thesis.components.DoubleEntryComponent;
import pl.study.thesis.components.ModOrDelEntryComponent;
import pl.study.thesis.components.SingleEntryComponent;
import pl.study.thesis.handler.RepoHandler;
import pl.study.thesis.views.main.MainView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Route(value = "calendar", layout = MainView.class)

@PageTitle("Calendar")
@CssImport("./styles/views/about/about-view.css")
@RouteAlias(value = "calendar", layout = MainView.class)
@Component
@Scope("prototype")
public class CalendarView extends Div implements BeforeEnterObserver {

    FullCalendar calendar = FullCalendarBuilder.create().build();
    protected final Button previousDate = new Button(new Icon(VaadinIcon.ARROW_LEFT));
    protected final Button nextDate = new Button(new Icon(VaadinIcon.ARROW_RIGHT));
    protected final DatePicker selectedDate = new DatePicker();
    protected final ComboBox<String> scale = new ComboBox<>("", "Day", "Week", "Month");

    public CalendarView() {
        setIds();
        add(previousDate, nextDate, scale, selectedDate, calendar);
        addListeners();
        scale.setValue("Month");
        calendar.getStyle().set("height", "50%");
        selectedDate.setValue(LocalDate.now());
    }

    private void setIds() {
        this.setId("calendar_main_element");
        previousDate.setId("previousDate_button");
        nextDate.setId("nextDate_button");
        selectedDate.setId("selectDate_picker");
        calendar.setId("calendar_widget");
    }

    private void addListeners() {
        calendar.addTimeslotsSelectedListener(e -> {
            if(e.getStartDateTime().toLocalDate().plusDays(1).isEqual(e.getEndDateTime().toLocalDate())) {
                SingleEntryComponent entryComponent = new SingleEntryComponent(e.getStartDateTime());
                entryComponent.addDetachListener(this::addEntryListener);
                entryComponent.open();
            }
            else {
                DoubleEntryComponent entryComponent = new DoubleEntryComponent(e.getStartDateTime(), e.getEndDateTime());
                entryComponent.addDetachListener(this::addEntryListener);
                entryComponent.open();
            }
        });

        calendar.addEntryClickedListener(e -> {
            ModOrDelEntryComponent modifyEntryComponent = new ModOrDelEntryComponent(e.getEntry());
            modifyEntryComponent.open();
            modifyEntryComponent.addDetachListener(w -> {
                refreshEntries();
            });
        });

        previousDate.addClickListener(e -> {
            calendar.previous();
        });

        nextDate.addClickListener(e -> {
            calendar.next();
        });

        scale.addValueChangeListener(e -> {
            if(e.getValue().equals("Day")) calendar.changeView(CalendarViewImpl.DAY_GRID_DAY);
            if(e.getValue().equals("Week")) calendar.changeView(CalendarViewImpl.DAY_GRID_WEEK);
            if(e.getValue().equals("Month")) calendar.changeView(CalendarViewImpl.DAY_GRID_MONTH);
        });

        selectedDate.addValueChangeListener(e -> {
            calendar.gotoDate(e.getValue());
        });

    }

    private void addEntryListener(DetachEvent event) {
        if(event.getSession().getAttribute(Entry.class) != null) {
            Entry entry = event.getSession().getAttribute(Entry.class);
            calendar.addEntry(entry);
        }
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        refreshEntries();
    }

    private void refreshEntries() {
        calendar.removeAllEntries();
        List<Entry> entries = new ArrayList<>();
        RepoHandler.getEventDao().findAll().forEach(event -> {
            entries.add(event.asEntry());
        });
        calendar.addEntries(entries);
    }
}
