package dev.emiliomartinez.bugtracker.services;
import java.util.Date;
import java.util.List;
import java.util.Optional;
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
    
    public Optional<Incidencia> obtenerPorId(Integer id) {
        return incidenciaDAO.obtenerPorId(id);
    }
    
    public void cerrarIncidencia(Integer incidenciaId) {
        Optional<Incidencia> optIncidencia = incidenciaDAO.obtenerPorId(incidenciaId);
        optIncidencia.ifPresent(incidencia -> {
            incidencia.setEstadoId(2);
            incidencia.setFechaActualizacion(new Date());
            incidenciaDAO.actualizar(incidencia);
        });
    }
}
