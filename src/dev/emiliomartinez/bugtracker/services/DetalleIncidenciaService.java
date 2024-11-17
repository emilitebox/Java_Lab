package dev.emiliomartinez.bugtracker.services;
import java.util.List;
import java.util.Optional;
import dev.emiliomartinez.bugtracker.dao.DetalleIncidenciaDAO;
import dev.emiliomartinez.bugtracker.entities.DetalleIncidencia;

public class DetalleIncidenciaService {
    private final DetalleIncidenciaDAO detalleDAO;
    
    public DetalleIncidenciaService() {
        this.detalleDAO = new DetalleIncidenciaDAO();
    }
    
    public void crearDetalle(Integer incidenciaId, Double tareaRealizada, Double horasInvertidas) {
        if (incidenciaId == null || incidenciaId <= 0) {
            throw new IllegalArgumentException("ID de incidencia inválido");
        }
        if (tareaRealizada == null || tareaRealizada < 0) {
            throw new IllegalArgumentException("Tarea realizada inválida");
        }
        if (horasInvertidas == null || horasInvertidas < 0) {
            throw new IllegalArgumentException("Horas invertidas inválidas");
        }

        DetalleIncidencia detalle = new DetalleIncidencia(incidenciaId, tareaRealizada, horasInvertidas);
        detalleDAO.guardar(detalle);
    }
    
    public List<DetalleIncidencia> obtenerDetallesPorIncidencia(Integer incidenciaId) {
        if (incidenciaId == null || incidenciaId <= 0) {
            throw new IllegalArgumentException("ID de incidencia inválido");
        }
        return detalleDAO.obtenerDetallesPorIncidencia(incidenciaId);
    }
    
    public Optional<DetalleIncidencia> obtenerDetalle(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID inválido");
        }
        return detalleDAO.obtenerPorId(id);
    }
    
    public void actualizarDetalle(DetalleIncidencia detalle) {
        if (detalle == null) {
            throw new IllegalArgumentException("Detalle no puede ser null");
        }
        if (detalle.getId() == null || detalle.getId() <= 0) {
            throw new IllegalArgumentException("ID de detalle inválido");
        }
        detalleDAO.actualizar(detalle);
    }
}