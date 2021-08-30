package com.tcsonline.eventsdashboard.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tcsonline.eventsdashboard.repositories.EventRepository;

@Service
public class EventService {

	@Autowired
	EventRepository eventRepository;
	public List<String> getLiveEventLinks() {
		
		return eventRepository.findAll()
				.stream()
				.map((event) -> event.getLink())
				.collect(Collectors.toList());
	}
}
