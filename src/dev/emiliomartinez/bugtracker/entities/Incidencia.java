package dev.emiliomartinez.bugtracker.entities;
import java.util.Date;

public class Incidencia {
    private Integer id;
    private String nombreIncidencia;
    private String descripcion;
    private Double horasEstimadas;
    private Integer estadoId;
    private Integer proyectoId;
    private Date fechaCreacion;
    private Date fechaActualizacion;

    public Incidencia(String nombreIncidencia, String descripcion, Double horasEstimadas, Integer proyectoId) {
        this.nombreIncidencia = nombreIncidencia;
        this.descripcion = descripcion;
        this.horasEstimadas = horasEstimadas;
        this.proyectoId = proyectoId;
        this.estadoId = 1;
        this.fechaCreacion = new Date();
        this.fechaActualizacion = new Date();
    }

    // Full Constructor
    public Incidencia(Integer id, String nombreIncidencia, String descripcion, Double horasEstimadas, 
                     Integer estadoId, Integer proyectoId, Date fechaCreacion, Date fechaActualizacion) {
        this.id = id;
        this.nombreIncidencia = nombreIncidencia;
        this.descripcion = descripcion;
        this.horasEstimadas = horasEstimadas;
        this.estadoId = estadoId;
        this.proyectoId = proyectoId;
        this.fechaCreacion = fechaCreacion;
        this.fechaActualizacion = fechaActualizacion;
    }

    // Getters
    public Integer getId() {
        return id;
    }

    public String getNombreIncidencia() {
        return nombreIncidencia;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Double getHorasEstimadas() {
        return horasEstimadas;
    }

    public Integer getEstadoId() {
        return estadoId;
    }
    
    public Integer getProyectoId() {
        return proyectoId;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public Date getFechaActualizacion() {
        return fechaActualizacion;
    }

    // Setters
    public void setId(Integer id) {
        this.id = id;
    }

    public void setNombreIncidencia(String nombreIncidencia) {
        this.nombreIncidencia = nombreIncidencia;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setHorasEstimadas(Double horasEstimadas) {
        this.horasEstimadas = horasEstimadas;
    }

    public void setEstadoId(Integer estadoId) {
        this.estadoId = estadoId;
    }
    
    public void setProyectoId(Integer proyectoId) {
        this.proyectoId = proyectoId;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public void setFechaActualizacion(Date fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    @Override
    public String toString() {
        return "Incidencia{" +
                "id=" + id +
                ", nombreIncidencia='" + nombreIncidencia + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", horasEstimadas=" + horasEstimadas +
                ", estado=" + estadoId +
                ", proyectoId=" + proyectoId +
                ", fechaCreacion=" + fechaCreacion +
                ", fechaActualizacion=" + fechaActualizacion +
                '}';
    }
}
