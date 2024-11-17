package dev.emiliomartinez.bugtracker.entities;
import java.util.Date;

public class Proyecto {
    private Integer id;
    private String nombreProyecto;
    private Integer userId;
    private Date fecha;

    public Proyecto(String nombreProyecto, Integer userId) {
        this.nombreProyecto = nombreProyecto;
        this.userId = userId;
        this.fecha = new Date();
    }

    // full constructor
    public Proyecto(Integer id, String nombreProyecto, Integer userId, Date fecha) {
        this.id = id;
        this.nombreProyecto = nombreProyecto;
        this.userId = userId;
        this.fecha = fecha;
    }

    // Getters
    public Integer getId() {
        return id;
    }

    public String getNombreProyecto() {
        return nombreProyecto;
    }

    public Integer getUserId() {
        return userId;
    }

    public Date getFecha() {
        return fecha;
    }

    // Setters
    public void setId(Integer id) {
        this.id = id;
    }

    public void setNombreProyecto(String nombreProyecto) {
        this.nombreProyecto = nombreProyecto;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return "Proyecto{" +
                "id=" + id +
                ", nombreProyecto='" + nombreProyecto + '\'' +
                ", userId=" + userId +
                ", fecha=" + fecha +
                '}';
    }
}

