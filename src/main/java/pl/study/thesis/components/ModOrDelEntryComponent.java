package pl.study.thesis.components;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.data.binder.ValidationException;
import org.vaadin.stefan.fullcalendar.Entry;
import pl.study.thesis.entity.Event;
import pl.study.thesis.handler.RepoHandler;

public class ModOrDelEntryComponent extends DoubleEntryComponent{
    protected final Button remove = new Button(new Icon(VaadinIcon.FOLDER_REMOVE));

    public ModOrDelEntryComponent(Entry entry) {
        super(entry.getStart(), entry.getEnd());
        add(remove);
        Event event = Event.fromEntry(entry);
        binder.readBean(Event.fromEntry(entry));

        componentLabel.setText("Modify or remove entry");
        save.setText("Modify");
        remove.getStyle().set("color", "red");
        remove.setWidth("100%");
        remove.addClickListener(this::removeDataClickListener);
    }

    @Override
    protected void saveDataClickListener(ClickEvent<Button> clickEvent){
        binder.validate();
        if(binder.isValid()) {
            Event event = new Event();
            try {
                binder.writeBean(event);
            } catch (ValidationException e) {
                e.printStackTrace();
            }
            RepoHandler.getEventDao().save(event);
            this.close();
        }
    }

    private void removeDataClickListener(ClickEvent<Button> removeButton) {
        Event event = new Event();
        try {
            binder.writeBean(event);
        } catch (ValidationException e) {
            e.printStackTrace();
        }
        RepoHandler.getEventDao().delete(event);
        this.close();
    }

}
