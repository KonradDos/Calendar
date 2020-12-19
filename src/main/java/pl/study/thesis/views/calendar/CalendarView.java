package pl.study.thesis.views.calendar;

import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.vaadin.stefan.fullcalendar.Entry;
import org.vaadin.stefan.fullcalendar.FullCalendar;
import org.vaadin.stefan.fullcalendar.FullCalendarBuilder;
import pl.study.thesis.components.DoubleEntryComponent;
import pl.study.thesis.components.ModOrDelEntryComponent;
import pl.study.thesis.components.SingleEntryComponent;
import pl.study.thesis.handler.RepoHandler;
import pl.study.thesis.views.main.MainView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Route(value = "calendar", layout = MainView.class)
@PageTitle("Calendar")
@CssImport("./styles/views/about/about-view.css")
@RouteAlias(value = "calendar", layout = MainView.class)
@Component
@Scope("prototype")
public class CalendarView extends Div implements BeforeEnterObserver {

    FullCalendar calendar = FullCalendarBuilder.create().build();

    public CalendarView() {
        add(calendar);
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
        RepoHandler.getEventDao().findAll().forEach(event -> entries.add(event.asEntry()));
        calendar.addEntries(entries);
    }
}
