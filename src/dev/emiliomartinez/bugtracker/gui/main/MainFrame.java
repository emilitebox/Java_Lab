package dev.emiliomartinez.bugtracker.gui.main;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import dev.emiliomartinez.bugtracker.entities.TipoPermiso;
import dev.emiliomartinez.bugtracker.entities.Usuario;
import dev.emiliomartinez.bugtracker.gui.admin.AdminPanel;
import dev.emiliomartinez.bugtracker.gui.user.UserPanel;
import dev.emiliomartinez.bugtracker.services.UsuarioService;

public class MainFrame extends JFrame {
    private Usuario usuarioActual;
    private final UsuarioService usuarioService;
    
    public MainFrame(Integer userId) {
        this.usuarioService = new UsuarioService();
        
        initFrame();
        loadUser(userId);
    }
    
    private void initFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Sistema de Gestión de Incidencias");
        setSize(800, 600);
        setLocationRelativeTo(null);
    }
    
    private void loadUser(Integer userId) {
        usuarioService.obtenerUsuario(userId).ifPresentOrElse(
            usuario -> {
                this.usuarioActual = usuario;
                setupUserInterface();
            },
            () -> {
                JOptionPane.showMessageDialog(this, 
                    "Usuario no encontrado", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                System.exit(1);
            }
        );
    }
    
    private void setupUserInterface() {
        JPanel mainPanel = usuarioActual.getPermiso() == TipoPermiso.ADMIN ?
            new AdminPanel(usuarioActual) :
            new UserPanel(usuarioActual);
            
        setContentPane(mainPanel);
    }
}
