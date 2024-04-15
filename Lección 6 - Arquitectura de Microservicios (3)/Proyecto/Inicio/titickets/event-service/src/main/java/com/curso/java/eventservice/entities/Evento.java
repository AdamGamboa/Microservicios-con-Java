
package com.curso.java.eventservice.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;
import java.util.Objects;

@Entity
@Table(name = "events")
public class Evento {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nombre;
    
    @Column(name = "fecha_evento")
    private OffsetDateTime fechaEvento;
    
    @Column(name = "total_espacios")
    private int totalEspacios;
    
    @Column(name = "espacios_adquiridos")
    private int espaciosAdquiridos;
    
    @Column(name = "fecha_venta_boletos")
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

    public int getEspaciosAdquiridos() {
        return espaciosAdquiridos;
    }

    public void setEspaciosAdquiridos(int espaciosAdquiridos) {
        this.espaciosAdquiridos = espaciosAdquiridos;
    }

    public OffsetDateTime getFechaVentaBoletos() {
        return fechaVentaBoletos;
    }

    public void setFechaVentaBoletos(OffsetDateTime fechaVentaBoletos) {
        this.fechaVentaBoletos = fechaVentaBoletos;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.id);
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
        final Evento other = (Evento) obj;
        return Objects.equals(this.id, other.id);
    }
    
    
    
    
}
