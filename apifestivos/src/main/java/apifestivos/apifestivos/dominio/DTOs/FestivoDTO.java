package apifestivos.apifestivos.dominio.DTOs;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

public class FestivoDTO {
    
    private String nombre;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate fecha;

    // Constructor vacío
    public FestivoDTO() {
    }

    // Constructor con parámetros
    public FestivoDTO(String nombre, LocalDate fecha) {
        this.nombre = nombre;
        this.fecha = fecha;
    }

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
}