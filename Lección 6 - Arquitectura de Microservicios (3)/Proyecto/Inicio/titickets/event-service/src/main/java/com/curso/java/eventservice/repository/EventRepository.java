package com.curso.java.eventservice.repository;

import com.curso.java.eventservice.entities.Evento;
import java.time.OffsetDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface EventRepository extends CrudRepository<Evento, Long> {
    
    @Query("select e from Evento e where e.fechaEvento < :fecha")
    public List<Evento> getByFechaEvento(@Param("fecha") OffsetDateTime fecha);
}
