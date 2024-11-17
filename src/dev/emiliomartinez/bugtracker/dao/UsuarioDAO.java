package dev.emiliomartinez.bugtracker.dao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import dev.emiliomartinez.bugtracker.entities.TipoPermiso;
import dev.emiliomartinez.bugtracker.entities.Usuario;

public class UsuarioDAO implements IDAO<Usuario> {
	
    private final DatabaseConfig dbConfig;
    
    public UsuarioDAO() {
        this.dbConfig = DatabaseConfig.getInstance();
    }

    private Integer obtenerIdPermiso(TipoPermiso tipoPermiso) throws SQLException {
        String sql = "SELECT id FROM tipousuarios WHERE tipopermiso = ?";
        try (Connection conn = dbConfig.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, tipoPermiso.getDescripcion());
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt("id");
            }
            throw new SQLException("No se encontró el tipo de permiso: " + tipoPermiso.getDescripcion());
        }
    }

    private TipoPermiso obtenerTipoPermiso(Integer idPermiso) throws SQLException {
        String sql = "SELECT tipopermiso FROM tipousuarios WHERE id = ?";
        try (Connection conn = dbConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, idPermiso);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                String tipoPermiso = rs.getString("tipopermiso");
                return tipoPermiso.equals("administrador") ? TipoPermiso.ADMIN : TipoPermiso.REGULAR;
            }
            throw new SQLException("No se encontró el id de permiso: " + idPermiso);
        }
    }
    
    @Override
    public Optional<Usuario> obtenerPorId(Integer id) {
        String sql = "SELECT u.*, t.tipopermiso FROM usuarios u " +
                    "JOIN tipousuarios t ON u.idpermiso = t.id " +
                    "WHERE u.id = ?";
        
        try (Connection conn = dbConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Integer idPermiso = rs.getInt("idpermiso");
                TipoPermiso tipoPermiso = obtenerTipoPermiso(idPermiso);
                
                return Optional.of(new Usuario(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("email"),
                    tipoPermiso,
                    idPermiso
                ));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
    
    @Override
    public List<Usuario> obtenerTodos() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT u.*, t.tipopermiso FROM usuarios u " +
                    "JOIN tipousuarios t ON u.idpermiso = t.id";
        
        try (Connection conn = dbConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Integer idPermiso = rs.getInt("idpermiso");
                TipoPermiso tipoPermiso = obtenerTipoPermiso(idPermiso);
                
                usuarios.add(new Usuario(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("email"),
                    tipoPermiso,
                    idPermiso
                ));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuarios;
    }
    
    @Override
    public void guardar(Usuario usuario) {
        String sql = "INSERT INTO usuarios (nombre, email, idpermiso) VALUES (?, ?, ?)";
        
        try (Connection conn = dbConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            Integer idPermiso = obtenerIdPermiso(usuario.getPermiso());
            
            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getEmail());
            stmt.setInt(3, idPermiso);
            
            stmt.executeUpdate();
            
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                usuario.setId(rs.getInt(1));
                usuario.setIdPermiso(idPermiso);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void actualizar(Usuario usuario) {
        String sql = "UPDATE usuarios SET nombre = ?, email = ?, idpermiso = ? WHERE id = ?";
        
        try (Connection conn = dbConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getEmail());
            stmt.setInt(3, usuario.getIdPermiso());
            stmt.setInt(4, usuario.getId());
            
            stmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void eliminar(Integer id) {
        String sql = "DELETE FROM usuarios WHERE id = ?";
        
        try (Connection conn = dbConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            stmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}