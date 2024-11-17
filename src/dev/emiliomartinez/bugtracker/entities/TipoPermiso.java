package dev.emiliomartinez.bugtracker.entities;

public enum TipoPermiso {
    ADMIN("administrador"),
    REGULAR("regular");

    private final String descripcion;

    TipoPermiso(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
