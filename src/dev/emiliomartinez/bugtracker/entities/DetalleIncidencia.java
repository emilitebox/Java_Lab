package dev.emiliomartinez.bugtracker.entities;
import java.util.Date;

public class DetalleIncidencia {
    private Integer id;
    private Integer incidenciaId;
    private String tareaRealizada;
    private Integer horasInvertidas;
    private Date fechaCreacion;

    public DetalleIncidencia(Integer incidenciaId, String tareaRealizada, Integer horasInvertidas) {
        this.incidenciaId = incidenciaId;
        this.tareaRealizada = tareaRealizada;
        this.horasInvertidas = horasInvertidas;
        this.fechaCreacion = new Date();
    }

    // full constructor
    public DetalleIncidencia(Integer id, Integer incidenciaId, String tareaRealizada, 
                            Integer horasInvertidas, Date fechaCreacion) {
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

    public String getTareaRealizada() {
        return tareaRealizada;
    }

    public Integer getHorasInvertidas() {
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

    public void setTareaRealizada(String tareaRealizada) {
        this.tareaRealizada = tareaRealizada;
    }

    public void setHorasInvertidas(Integer horasInvertidas) {
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
