package dev.emiliomartinez.bugtracker.services;
import java.util.List;
import dev.emiliomartinez.bugtracker.dao.IncidenciaDAO;
import dev.emiliomartinez.bugtracker.entities.Incidencia;

public class IncidenciaService {
    private final IncidenciaDAO incidenciaDAO;
    
    public IncidenciaService() {
        this.incidenciaDAO = new IncidenciaDAO();
    }
    
    public void crearIncidencia(String nombre, String descripcion, Double horasEstimadas, Integer proyectoId) {
        Incidencia incidencia = new Incidencia(nombre, descripcion, horasEstimadas, proyectoId);
        incidenciaDAO.guardar(incidencia);
    }
    
    public List<Incidencia> obtenerIncidenciasPorProyecto(Integer proyectoId) {
        return incidenciaDAO.obtenerIncidenciasPorProyecto(proyectoId);
    }
    
    public void actualizarIncidencia(Incidencia incidencia) {
        incidenciaDAO.actualizar(incidencia);
    }
}
