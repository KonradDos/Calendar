package pl.study.thesis.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.study.thesis.dao.EventDao;
import pl.study.thesis.entity.Event;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;

@Service
public class ExportService {

    @Autowired
    EventDao eventDao;

    public String exportToXML() throws JAXBException {
        Iterable<Event> events = eventDao.findAll();
        StringBuilder resultXML = new StringBuilder();

        for (Event event : events) {
            resultXML.append(eventToXMLString(event));
        }

        return resultXML.toString();
    }

    private String eventToXMLString(Event event) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(Event.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        StringWriter sw = new StringWriter();
        jaxbMarshaller.marshal(event, sw);
        return sw.toString();
    }
}
