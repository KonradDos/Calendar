package pl.study.thesis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.vaadin.artur.helpers.LaunchUtil;
import pl.study.thesis.dao.EventDao;
import pl.study.thesis.entity.Event;
import pl.study.thesis.handler.RepoHandler;

import java.time.LocalDate;

/**
 * The entry point of the Spring Boot application.
 */
@SpringBootApplication
@ComponentScan("pl.study.thesis")
@EnableAutoConfiguration
@EnableJpaRepositories
@EnableJpaAuditing
public class Application extends SpringBootServletInitializer {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);

        Event event = new Event();
        event.setColor("asfasf");
        event.setDescription("asdasd");
        event.setTitle("asdasd");
        event.setStartDay(LocalDate.now());
        event.setEndDay(LocalDate.now());
        event.setAllDay(true);

        EventDao test = context.getBean(EventDao.class);
        test.save(event);

        RepoHandler.setEventDao(test);
        LaunchUtil.launchBrowserInDevelopmentMode(context);
    }

}
