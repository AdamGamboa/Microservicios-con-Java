package com.curso.java.eventservice.service;

import com.curso.java.eventservice.entities.Evento;
import com.curso.java.eventservice.model.Event;
import com.curso.java.eventservice.repository.EventRepository;
import java.time.OffsetDateTime;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class EventService {
    
    private final EventRepository eventRepository;
    
    public EventService(EventRepository eventRepository){
        this.eventRepository = eventRepository;
    }
    
    public List<Event> getActiveEvents(){
        return this.eventRepository.getByFechaEvento(OffsetDateTime.now())
                .stream()
                .map(evento -> this.toEvent(evento))
                .toList();
    }
    
    
    private Event toEvent(Evento evento){
        Event event = new Event();
        event.setId(evento.getId());
        event.setNombre(evento.getNombre());
        event.setTotalEspacios(evento.getTotalEspacios());
        event.setEspaciosAdquiridos(evento.getEspaciosAdquiridos());
        event.setFechaEvento(evento.getFechaEvento());
        event.setFechaVentaBoletos(evento.getFechaVentaBoletos());
        event.setUbicacion(evento.getUbicacion());
        return event;
    }
}
