package dev.emiliomartinez.bugtracker.entities;

public class Administrador extends Usuario {
    
	  public Administrador(String nombre, String email) {
		  super(nombre, email, TipoPermiso.ADMIN);
	  }
}
