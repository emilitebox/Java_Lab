package dev.emiliomartinez.bugtracker.gui;
import dev.emiliomartinez.bugtracker.entities.TipoPermiso;
import dev.emiliomartinez.bugtracker.entities.Usuario;
import dev.emiliomartinez.bugtracker.services.UsuarioService;
import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private Usuario usuarioActual;
    private final UsuarioService usuarioService;
    
    public MainFrame(Integer userId) {
        this.usuarioService = new UsuarioService();
        
        // get user
        usuarioService.obtenerUsuario(userId).ifPresent(usuario -> {
            this.usuarioActual = usuario;
            initComponents();
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Sistema de Gestión");
        setSize(600, 400);
        setLocationRelativeTo(null);
    }
    
    private void initComponents() {
        // main panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // user info
        JPanel userInfoPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        userInfoPanel.setBorder(BorderFactory.createTitledBorder("Información del Usuario"));
        
        userInfoPanel.add(new JLabel("ID:"));
        userInfoPanel.add(new JLabel(usuarioActual.getId().toString()));
        userInfoPanel.add(new JLabel("Email:"));
        userInfoPanel.add(new JLabel(usuarioActual.getEmail()));
        userInfoPanel.add(new JLabel("Rol:"));
        userInfoPanel.add(new JLabel(usuarioActual.getPermiso().getDescripcion()));
        
        mainPanel.add(userInfoPanel, BorderLayout.NORTH);
        
        // nav panel
        JPanel navPanel = new JPanel();
        if (usuarioActual.getPermiso() == TipoPermiso.ADMIN) {
            // admin user
            JButton crearUsuarioBtn = new JButton("Crear Usuario");
            JButton crearProyectoBtn = new JButton("Crear Proyecto");
            
            crearUsuarioBtn.addActionListener(e -> mostrarPantallaCrearUsuario());
            crearProyectoBtn.addActionListener(e -> mostrarPantallaCrearProyecto());
            
            navPanel.add(crearUsuarioBtn);
            navPanel.add(crearProyectoBtn);
        } else {
            // regular user
            JPanel proyectoPanel = new JPanel(new FlowLayout());
            JComboBox<String> proyectosCombo = new JComboBox<>(new String[]{"Proyecto 1", "Proyecto 2", "Proyecto 3"});
            JButton verProyectoBtn = new JButton("Ver Proyecto");
            
            verProyectoBtn.addActionListener(e -> 
                mostrarDetalleProyecto((String)proyectosCombo.getSelectedItem()));
            
            proyectoPanel.add(new JLabel("Seleccione un proyecto:"));
            proyectoPanel.add(proyectosCombo);
            proyectoPanel.add(verProyectoBtn);
            
            navPanel.add(proyectoPanel);
        }
        
        mainPanel.add(navPanel, BorderLayout.CENTER);
        
        setContentPane(mainPanel);
    }
    
    private void mostrarPantallaCrearUsuario() {
        new CrearUsuarioDialog(this, usuarioService);
    }
    
    private void mostrarPantallaCrearProyecto() {
        JOptionPane.showMessageDialog(this, 
            "Funcionalidad de crear proyecto en desarrollo", 
            "En construcción", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void mostrarDetalleProyecto(String nombreProyecto) {
        new DetalleProyectoFrame(nombreProyecto);
    }
}
