package pl.study.thesis.components;

import com.vaadin.flow.component.datepicker.DatePicker;
import pl.study.thesis.entity.Event;

import java.time.LocalDateTime;

public class DoubleEntryComponent extends SingleEntryComponent {

    protected final DatePicker endDay = new DatePicker("End");

    public DoubleEntryComponent(LocalDateTime startDate, LocalDateTime endDate) {
        super(startDate);

        this.endDay.setValue(endDate.toLocalDate());
        this.endDay.setWidth("100%");
        this.endDay.setVisible(true);

        binder.forField(endDay)
                .asRequired(FIELD_REQUIRED_ERROR_MESSAGE)
                .bind(Event::getEndDay, Event::setEndDay);

        addComponentAtIndex(7, endDay);
    }

}
