package dev.emiliomartinez.bugtracker.dao;
import dev.emiliomartinez.bugtracker.entities.Proyecto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProyectoDAO implements IDAO<Proyecto> {
    private final DatabaseConfig dbConfig;
    
    public ProyectoDAO() {
        this.dbConfig = DatabaseConfig.getInstance();
    }

    @Override
    public void guardar(Proyecto proyecto) {
        String sql = "INSERT INTO proyectos (nombreproyecto, userid, fecha) VALUES (?, ?, ?)";
        
        try (Connection conn = dbConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, proyecto.getNombreProyecto());
            stmt.setInt(2, proyecto.getUserId());
            stmt.setTimestamp(3, new Timestamp(proyecto.getFecha().getTime()));
            
            stmt.executeUpdate();
            
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                proyecto.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar proyecto", e);
        }
    }

    @Override
    public Optional<Proyecto> obtenerPorId(Integer id) {
        String sql = "SELECT * FROM proyectos WHERE id = ?";
        
        try (Connection conn = dbConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return Optional.of(new Proyecto(
                    rs.getInt("id"),
                    rs.getString("nombreproyecto"),
                    rs.getInt("userid"),
                    rs.getTimestamp("fecha")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener proyecto", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Proyecto> obtenerTodos() {
        List<Proyecto> proyectos = new ArrayList<>();
        String sql = "SELECT * FROM proyectos";
        
        try (Connection conn = dbConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                proyectos.add(new Proyecto(
                    rs.getInt("id"),
                    rs.getString("nombreproyecto"),
                    rs.getInt("userid"),
                    rs.getTimestamp("fecha")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener proyectos", e);
        }
        return proyectos;
    }

    public List<Proyecto> obtenerProyectosPorUsuario(Integer userId) {
        List<Proyecto> proyectos = new ArrayList<>();
        String sql = "SELECT * FROM proyectos WHERE userid = ?";
        
        try (Connection conn = dbConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                proyectos.add(new Proyecto(
                    rs.getInt("id"),
                    rs.getString("nombreproyecto"),
                    rs.getInt("userid"),
                    rs.getTimestamp("fecha")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener proyectos del usuario", e);
        }
        return proyectos;
    }

    @Override
    public void actualizar(Proyecto t) {
        throw new UnsupportedOperationException("Método no implementado");
    }

    @Override
    public void eliminar(Integer id) {
        throw new UnsupportedOperationException("Método no implementado");
    }
}

