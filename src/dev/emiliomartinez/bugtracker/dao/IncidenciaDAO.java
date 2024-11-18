package dev.emiliomartinez.bugtracker.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import dev.emiliomartinez.bugtracker.entities.Estado;
import dev.emiliomartinez.bugtracker.entities.Incidencia;
import java.util.Date;

public class IncidenciaDAO implements IDAO<Incidencia> {
    private final DatabaseConfig dbConfig;
    private final ProyectoDAO proyectoDAO;
    
    public IncidenciaDAO() {
        this.dbConfig = DatabaseConfig.getInstance();
        this.proyectoDAO = new ProyectoDAO();
    }

    public void guardar(Incidencia incidencia) {
        String sql = "INSERT INTO incidencias (nombreincidencia, descripcion, horasestimadas, " +
                    "estadoid, proyectoid, fechacreacion, fechaactualizacion) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = dbConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, incidencia.getNombreIncidencia());
            stmt.setString(2, incidencia.getDescripcion());
            stmt.setDouble(3, incidencia.getHorasEstimadas());
            stmt.setInt(4, 1);
            stmt.setInt(5, incidencia.getProyectoId());
            stmt.setTimestamp(6, new Timestamp(incidencia.getFechaCreacion().getTime()));
            stmt.setTimestamp(7, null);
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Creating incidencia failed, no rows affected.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    incidencia.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating incidencia failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al guardar incidencia: " + e.getMessage(), e);
        }
    }

    public List<Incidencia> obtenerIncidenciasPorProyecto(Integer proyectoId) {
        List<Incidencia> incidencias = new ArrayList<>();
        String sql = "SELECT * FROM incidencias WHERE proyectoid = ? ORDER BY fechacreacion";
        
        try (Connection conn = dbConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, proyectoId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                incidencias.add(crearIncidenciaDesdeResultSet(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener incidencias del proyecto", e);
        }
        return incidencias;
    }

    @Override
    public Optional<Incidencia> obtenerPorId(Integer id) {
        String sql = "SELECT * FROM incidencias WHERE id = ?";
        
        try (Connection conn = dbConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return Optional.of(crearIncidenciaDesdeResultSet(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener incidencia", e);
        }
        return Optional.empty();
    }

    private Incidencia crearIncidenciaDesdeResultSet(ResultSet rs) throws SQLException {
        return new Incidencia(
            rs.getInt("id"),
            rs.getString("nombreincidencia"),
            rs.getString("descripcion"),
            rs.getDouble("horasestimadas"),
            rs.getInt("estadoId"),
            rs.getInt("proyectoid"),
            rs.getTimestamp("fechacreacion"),
            rs.getTimestamp("fechaactualizacion")
        );
    }

    @Override
    public void actualizar(Incidencia incidencia) {
        String sql = "UPDATE incidencias SET estadoid = ?, fechaactualizacion = ? WHERE id = ?";
        
        try (Connection conn = dbConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
        	
            stmt.setInt(1, 2);
            stmt.setTimestamp(2, new Timestamp(incidencia.getFechaCreacion().getTime()));
            stmt.setInt(3, incidencia.getId());
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar incidencia: " + e.getMessage(), e);
        }
    }

	@Override
	public List<Incidencia> obtenerTodos() {
		return null;
	}

	@Override
	public void eliminar(Integer id) {
        throw new UnsupportedOperationException("Método no implementado");
	}
}
