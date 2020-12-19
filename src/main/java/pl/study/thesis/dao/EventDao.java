package pl.study.thesis.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.study.thesis.entity.Event;

@Repository
public interface EventDao extends CrudRepository<Event, Long> {
}
