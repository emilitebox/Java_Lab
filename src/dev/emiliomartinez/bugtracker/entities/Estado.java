package dev.emiliomartinez.bugtracker.entities;

public enum Estado {
    ABIERTO("abierto"),
    CERRADO("cerrado");

    private final String descripcion;

    Estado(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
