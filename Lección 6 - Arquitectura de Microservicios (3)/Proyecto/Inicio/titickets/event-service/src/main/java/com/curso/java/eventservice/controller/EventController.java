package com.curso.java.eventservice.controller;

import com.curso.java.eventservice.model.Event;
import com.curso.java.eventservice.service.EventService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/events")
public class EventController {
    
    private final EventService eventService;
    
    @Autowired
    public EventController(EventService eventService){
        this.eventService = eventService;
    }
    
    @GetMapping("/active")
    public List<Event> getActives(){
        var users = eventService.getActiveEvents();
        return users;
    }
    
    @GetMapping("/{eventId}")
    public Event findEvent(@PathVariable long eventId){
        return null;
    }
    
}
