package pl.study.thesis.views.calendar;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.study.thesis.views.main.MainView;

@Route(value = "calendar", layout = MainView.class)
@PageTitle("Calendar")
@CssImport("./styles/views/about/about-view.css")
@RouteAlias(value = "calendar", layout = MainView.class)
@Component
public class CalendarView extends Div {
    public CalendarView(@Autowired Calendar calendar) {
        setId("about-view");
        add(calendar);
  }
}
