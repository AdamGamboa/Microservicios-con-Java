
package com.curso.java.eventservice.model;

import java.time.OffsetDateTime;
import java.util.Objects;

public class Event {
    private Long id;
    private String nombre;
    private OffsetDateTime fechaEvento;
    private int totalEspacios;
    private int espaciosAdquiridos;
    private OffsetDateTime fechaVentaBoletos;
    private String ubicacion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public OffsetDateTime getFechaEvento() {
        return fechaEvento;
    }

    public void setFechaEvento(OffsetDateTime fechaEvento) {
        this.fechaEvento = fechaEvento;
    }

    public int getTotalEspacios() {
        return totalEspacios;
    }

    public void setTotalEspacios(int totalEspacios) {
        this.totalEspacios = totalEspacios;
    }


    public OffsetDateTime getFechaVentaBoletos() {
        return fechaVentaBoletos;
    }

    public void setFechaVentaBoletos(OffsetDateTime fechaVentaBoletos) {
        this.fechaVentaBoletos = fechaVentaBoletos;
    }

    public int getEspaciosAdquiridos() {
        return espaciosAdquiridos;
    }

    public void setEspaciosAdquiridos(int espaciosAdquiridos) {
        this.espaciosAdquiridos = espaciosAdquiridos;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Event other = (Event) obj;
        return Objects.equals(this.id, other.id);
    }
    
    
    
    
}
