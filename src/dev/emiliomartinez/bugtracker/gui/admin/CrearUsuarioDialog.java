package dev.emiliomartinez.bugtracker.gui.admin;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import dev.emiliomartinez.bugtracker.entities.TipoPermiso;
import dev.emiliomartinez.bugtracker.entities.Usuario;
import dev.emiliomartinez.bugtracker.services.UsuarioService;

public class CrearUsuarioDialog extends JDialog {
    private final JTextField nombreField;
    private final JTextField emailField;
    private final JComboBox<String> tipoUsuarioCombo;
    private final UsuarioService usuarioService;
    
    public CrearUsuarioDialog(JFrame parent) {
        super(parent, "Crear Usuario", true);
        this.usuarioService = new UsuarioService();
        
        nombreField = new JTextField(20);
        emailField = new JTextField(20);
        tipoUsuarioCombo = new JComboBox<>(new String[]{"Regular", "Administrador"});
        
        initComponents();
        
        pack();
        setLocationRelativeTo(parent);
        setVisible(true);
    }
    
    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        
        JPanel fieldsPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        fieldsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        fieldsPanel.add(new JLabel("Nombre:"));
        fieldsPanel.add(nombreField);
        fieldsPanel.add(new JLabel("Email:"));
        fieldsPanel.add(emailField);
        fieldsPanel.add(new JLabel("Tipo:"));
        fieldsPanel.add(tipoUsuarioCombo);
        
        JPanel buttonPanel = new JPanel();
        JButton guardarBtn = new JButton("Guardar");
        JButton cancelarBtn = new JButton("Cancelar");
        
        guardarBtn.addActionListener(e -> guardarUsuario());
        cancelarBtn.addActionListener(e -> dispose());
        
        buttonPanel.add(guardarBtn);
        buttonPanel.add(cancelarBtn);
        
        add(fieldsPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private void guardarUsuario() {
        try {
            String nombre = nombreField.getText().trim();
            String email = emailField.getText().trim();
            TipoPermiso tipo = tipoUsuarioCombo.getSelectedItem().equals("Administrador") ? 
                TipoPermiso.ADMIN : TipoPermiso.REGULAR;
            
            if (nombre.isEmpty()) {
                throw new IllegalArgumentException("El nombre no puede estar vacío");
            }
            if (!email.contains("@")) {
                throw new IllegalArgumentException("El email no es válido");
            }
            
            Usuario nuevoUsuario = new Usuario(nombre, email, tipo);
            usuarioService.crearUsuario(nuevoUsuario);
            
            JOptionPane.showMessageDialog(this, 
                "Usuario creado exitosamente", 
                "Éxito", 
                JOptionPane.INFORMATION_MESSAGE);
            dispose();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error al crear usuario: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
}
