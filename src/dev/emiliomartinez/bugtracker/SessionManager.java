package dev.emiliomartinez.bugtracker;
import java.util.Optional;
import dev.emiliomartinez.bugtracker.entities.Usuario;
import dev.emiliomartinez.bugtracker.services.UsuarioService;

public class SessionManager {
    private static SessionManager instance;
    private Integer currentUserId;
    private final UsuarioService usuarioService;

    private SessionManager() {
        this.usuarioService = new UsuarioService();
    }

    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public void initSession(Integer userId) throws IllegalArgumentException {
        Optional<Usuario> usuario = usuarioService.obtenerUsuario(userId);
        if (usuario.isPresent()) {
            this.currentUserId = userId;
        } else {
            throw new IllegalArgumentException("Usuario no encontrado");
        }
    }

    public Integer getCurrentUserId() {
        if (currentUserId == null) {
            throw new IllegalStateException("Sesión no inicializada");
        }
        return currentUserId;
    }
}