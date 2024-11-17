package dev.emiliomartinez.bugtracker.services;
import java.util.List;
import java.util.Optional;
import dev.emiliomartinez.bugtracker.dao.IDAO;
import dev.emiliomartinez.bugtracker.dao.ProyectoDAO;
import dev.emiliomartinez.bugtracker.entities.Proyecto;

public class ProyectoService {
    private final IDAO<Proyecto> proyectoDAO;
    
    public ProyectoService() {
        this.proyectoDAO = new ProyectoDAO();
    }
    
    public void crearProyecto(String nombre, Integer userId) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del proyecto no puede estar vacío");
        }
        
        Proyecto proyecto = new Proyecto(nombre, userId);
        proyectoDAO.guardar(proyecto);
    }
    
    public List<Proyecto> obtenerProyectosPorUsuario(Integer userId) {
        return ((ProyectoDAO)proyectoDAO).obtenerProyectosPorUsuario(userId);
    }
    
    public Optional<Proyecto> obtenerProyecto(Integer id) {
        return proyectoDAO.obtenerPorId(id);
    }
}
