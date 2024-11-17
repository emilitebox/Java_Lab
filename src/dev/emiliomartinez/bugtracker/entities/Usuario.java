package dev.emiliomartinez.bugtracker.entities;

public class Usuario {
    private Integer id;
    private String nombre;
    private String email;
    private TipoPermiso permiso;
    private Integer idPermiso;

    public Usuario(String nombre, String email, TipoPermiso permiso) {
        this.nombre = nombre;
        this.email = email;
        this.permiso = permiso;
    }

    public Usuario(Integer id, String nombre, String email, TipoPermiso permiso, Integer idPermiso) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.permiso = permiso;
        this.idPermiso = idPermiso;
    }

    // Getters y setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public TipoPermiso getPermiso() {
        return permiso;
    }

    public void setPermiso(TipoPermiso permiso) {
        this.permiso = permiso;
    }

    public Integer getIdPermiso() {
        return idPermiso;
    }

    public void setIdPermiso(Integer idPermiso) {
        this.idPermiso = idPermiso;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", email='" + email + '\'' +
                ", permiso=" + permiso +
                ", idPermiso=" + idPermiso +
                '}';
    }
}
