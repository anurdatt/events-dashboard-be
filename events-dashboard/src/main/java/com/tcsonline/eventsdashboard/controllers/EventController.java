package com.tcsonline.eventsdashboard.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tcsonline.eventsdashboard.services.EventService;

@RestController
@RequestMapping("/v1/events")
public class EventController {

	@Autowired
	EventService service;
	@GetMapping()
	public List<String> list() {
		return service.getLiveEventLinks();
	}
}
