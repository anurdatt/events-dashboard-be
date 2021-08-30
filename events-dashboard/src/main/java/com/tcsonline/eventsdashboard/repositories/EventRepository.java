package com.tcsonline.eventsdashboard.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tcsonline.eventsdashboard.entities.Event;

public interface EventRepository extends JpaRepository<Event, String> {

}
