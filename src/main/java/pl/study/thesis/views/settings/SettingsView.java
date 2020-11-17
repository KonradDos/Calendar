package pl.study.thesis.views.settings;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import pl.study.thesis.views.main.MainView;

@Route(value = "settings", layout = MainView.class)
@PageTitle("Calendar")
@CssImport("./styles/views/about/about-view.css")
@RouteAlias(value = "settings", layout = MainView.class)
public class SettingsView extends Div {
}
