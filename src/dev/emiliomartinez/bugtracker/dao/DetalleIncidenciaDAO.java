package dev.emiliomartinez.bugtracker.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import dev.emiliomartinez.bugtracker.entities.DetalleIncidencia;

public class DetalleIncidenciaDAO implements IDAO<DetalleIncidencia> {
    private final DatabaseConfig dbConfig;
    private final IncidenciaDAO incidenciaDAO;
    
    public DetalleIncidenciaDAO() {
        this.dbConfig = DatabaseConfig.getInstance();
        this.incidenciaDAO = new IncidenciaDAO();
    }

    @Override
    public void guardar(DetalleIncidencia detalle) {
        if (!incidenciaDAO.obtenerPorId(detalle.getIncidenciaId()).isPresent()) {
            throw new IllegalArgumentException("La incidencia no existe");
        }

        String sql = "INSERT INTO detalleincidencia (incidenciaid, tarearealizada, horasinvertidas, fechacreacion) " +
                    "VALUES (?, ?, ?, ?)";
        
        try (Connection conn = dbConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setInt(1, detalle.getIncidenciaId());
            stmt.setString(2, detalle.getTareaRealizada());
            stmt.setInt(3, detalle.getHorasInvertidas());
            stmt.setTimestamp(4, new Timestamp(detalle.getFechaCreacion().getTime()));
            
            stmt.executeUpdate();
            
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                detalle.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar detalle de incidencia", e);
        }
    }

    public List<DetalleIncidencia> obtenerDetallesPorIncidencia(Integer incidenciaId) {
        List<DetalleIncidencia> detalles = new ArrayList<>();
        String sql = "SELECT * FROM detalleincidencia WHERE incidenciaid = ? ORDER BY fechacreacion";
        
        try (Connection conn = dbConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, incidenciaId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                detalles.add(crearDetalleDesdeResultSet(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener detalles de la incidencia", e);
        }
        return detalles;
    }

    private DetalleIncidencia crearDetalleDesdeResultSet(ResultSet rs) throws SQLException {
        return new DetalleIncidencia(
            rs.getInt("id"),
            rs.getInt("incidenciaid"),
            rs.getString("tarearealizada"),
            rs.getInt("horasinvertidas"),
            rs.getTimestamp("fechacreacion")
        );
    }
    
    public Integer obtenerHorasConsumidas(Integer incidenciaId) {
        String sql = "SELECT SUM(horasinvertidas) as total FROM detalleincidencia WHERE incidenciaid = ?";
        
        try (Connection conn = dbConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, incidenciaId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt("total");
            }
            return 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener horas consumidas", e);
        }
    }


	@Override
	public Optional<DetalleIncidencia> obtenerPorId(Integer id) {
		return Optional.empty();
	}

	@Override
	public List<DetalleIncidencia> obtenerTodos() {
		return null;
	}

	@Override
	public void actualizar(DetalleIncidencia t) {
        throw new UnsupportedOperationException("Método no implementado");

	}

	@Override
	public void eliminar(Integer id) {
        throw new UnsupportedOperationException("Método no implementado");
	}

}
