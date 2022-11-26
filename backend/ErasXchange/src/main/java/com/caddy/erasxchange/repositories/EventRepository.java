package com.caddy.erasxchange.repositories;

import com.caddy.erasxchange.models.Event;
import org.springframework.data.repository.CrudRepository;

public interface EventRepository extends CrudRepository<Event, Long> {
}