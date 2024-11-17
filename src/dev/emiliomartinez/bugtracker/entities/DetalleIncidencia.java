package dev.emiliomartinez.bugtracker.entities;
import java.util.Date;

public class DetalleIncidencia {
    private Integer id;
    private Integer incidenciaId;
    private Double tareaRealizada;
    private Double horasInvertidas;
    private Date fechaCreacion;

    public DetalleIncidencia(Integer incidenciaId, Double tareaRealizada, Double horasInvertidas) {
        this.incidenciaId = incidenciaId;
        this.tareaRealizada = tareaRealizada;
        this.horasInvertidas = horasInvertidas;
        this.fechaCreacion = new Date();
    }

    // full constructor
    public DetalleIncidencia(Integer id, Integer incidenciaId, Double tareaRealizada, 
                            Double horasInvertidas, Date fechaCreacion) {
        this.id = id;
        this.incidenciaId = incidenciaId;
        this.tareaRealizada = tareaRealizada;
        this.horasInvertidas = horasInvertidas;
        this.fechaCreacion = fechaCreacion;
    }

    // Getters
    public Integer getId() {
        return id;
    }

    public Integer getIncidenciaId() {
        return incidenciaId;
    }

    public Double getTareaRealizada() {
        return tareaRealizada;
    }

    public Double getHorasInvertidas() {
        return horasInvertidas;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    // Setters
    public void setId(Integer id) {
        this.id = id;
    }

    public void setIncidenciaId(Integer incidenciaId) {
        this.incidenciaId = incidenciaId;
    }

    public void setTareaRealizada(Double tareaRealizada) {
        this.tareaRealizada = tareaRealizada;
    }

    public void setHorasInvertidas(Double horasInvertidas) {
        this.horasInvertidas = horasInvertidas;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    @Override
    public String toString() {
        return "DetalleIncidencia{" +
                "id=" + id +
                ", incidenciaId=" + incidenciaId +
                ", tareaRealizada=" + tareaRealizada +
                ", horasInvertidas=" + horasInvertidas +
                ", fechaCreacion=" + fechaCreacion +
                '}';
    }
}
