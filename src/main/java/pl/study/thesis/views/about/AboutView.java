package pl.study.thesis.views.about;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import pl.study.thesis.views.main.MainView;
import com.vaadin.flow.router.RouteAlias;

@Route(value = "about", layout = MainView.class)
@PageTitle("About")
@CssImport("./styles/views/about/about-view.css")
@RouteAlias(value = "", layout = MainView.class)
public class AboutView extends Div {

    public AboutView() {
        setId("about-view");
        add(new Label("Content placeholder"));
    }

}
