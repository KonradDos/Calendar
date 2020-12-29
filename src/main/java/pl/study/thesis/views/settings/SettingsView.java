package pl.study.thesis.views.settings;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.study.thesis.services.ExportService;
import pl.study.thesis.views.main.MainView;

import javax.xml.bind.JAXBException;

@Route(value = "settings", layout = MainView.class)
@PageTitle("Calendar")
@CssImport("./styles/views/about/about-view.css")
@RouteAlias(value = "settings", layout = MainView.class)
@Component
public class SettingsView extends Div {

    @Autowired
    ExportService exportService;

    private Button exportToXML = new Button("Export to XML");
    private Button loadFromXML = new Button("LO from XML");
    private Button exportToJSON = new Button("Export to JSON");
    private Button loadFromJSON = new Button("Load to JSON");
    private Button exportToIcalc = new Button("Export to iCalc");
    private Button loadFromIcalc = new Button("Load from iCalc");

    SettingsView() {

    }

    private void exportToXMlClickListener(ClickEvent<Button> clickEvent) {

        try {
            String result = exportService.exportToXML();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

}
