package dev.emiliomartinez.bugtracker.gui.user;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import dev.emiliomartinez.bugtracker.entities.Usuario;

public class UserPanel extends JPanel {
    private final Usuario usuario;
    
    public UserPanel(Usuario usuario) {
        this.usuario = usuario;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        initComponents();
    }
    
    private void initComponents() {
        JPanel userInfoPanel = createUserInfoPanel();
        add(userInfoPanel, BorderLayout.NORTH);
        
        JPanel buttonPanel = createButtonPanel();
        add(buttonPanel, BorderLayout.CENTER);
    }
    
    private JPanel createUserInfoPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Información del Usuario"));
        
        panel.add(new JLabel("ID:"));
        panel.add(new JLabel(usuario.getId().toString()));
        panel.add(new JLabel("Email:"));
        panel.add(new JLabel(usuario.getEmail()));
        panel.add(new JLabel("Rol:"));
        panel.add(new JLabel(usuario.getPermiso().getDescripcion()));
        
        return panel;
    }
    
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        
        JButton verProyectosBtn = new JButton("Ver Proyectos");
        verProyectosBtn.addActionListener(e -> new ListaProyectosFrame(usuario));
        panel.add(verProyectosBtn);
        
        return panel;
    }
}
