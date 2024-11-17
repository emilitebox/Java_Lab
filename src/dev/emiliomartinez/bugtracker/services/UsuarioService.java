package dev.emiliomartinez.bugtracker.services;
import dev.emiliomartinez.bugtracker.entities.Administrador;
import dev.emiliomartinez.bugtracker.entities.Usuario;
import java.util.List;
import java.util.Optional;
import dev.emiliomartinez.bugtracker.dao.*;


public class UsuarioService {
    private final IDAO<Usuario> usuarioDAO;
    
    public UsuarioService() {
        this.usuarioDAO = new UsuarioDAO();
    }
    
    public void crearUsuario(Usuario usuario) {
        if (usuario.getNombre() == null || usuario.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        if (usuario.getEmail() == null || !usuario.getEmail().contains("@")) {
            throw new IllegalArgumentException("Email inválido");
        }
        usuarioDAO.guardar(usuario);
    }
    
    public void crearAdministrador(Administrador admin) {
        crearUsuario(admin);
    }
    
    public Optional<Usuario> obtenerUsuario(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID inválido");
        }
        return usuarioDAO.obtenerPorId(id);
    }
    
    public List<Usuario> obtenerTodosLosUsuarios() {
        return usuarioDAO.obtenerTodos();
    }
    
    public void actualizarUsuario(Usuario usuario) {
        if (usuario.getId() == null || usuario.getId() <= 0) {
            throw new IllegalArgumentException("ID de usuario inválido para actualización");
        }
        usuarioDAO.actualizar(usuario);
    }
    
}