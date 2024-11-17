package dev.emiliomartinez.bugtracker.gui;
import dev.emiliomartinez.bugtracker.entities.TipoPermiso;
import dev.emiliomartinez.bugtracker.entities.Usuario;
import dev.emiliomartinez.bugtracker.services.UsuarioService;
import javax.swing.*;
import java.awt.*;

class CrearUsuarioDialog extends JDialog {
    private final JTextField nombreField;
    private final JTextField emailField;
    private final JComboBox<String> tipoUsuarioCombo;
    private final UsuarioService usuarioService;
    
    public CrearUsuarioDialog(JFrame parent, UsuarioService usuarioService) {
        super(parent, "Crear Usuario", true);
        this.usuarioService = usuarioService;
        
        setLayout(new BorderLayout(10, 10));
        
        // field panel
        JPanel fieldsPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        fieldsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        nombreField = new JTextField(20);
        emailField = new JTextField(20);
        tipoUsuarioCombo = new JComboBox<>(new String[]{"Regular", "Administrador"});
        
        fieldsPanel.add(new JLabel("Nombre:"));
        fieldsPanel.add(nombreField);
        fieldsPanel.add(new JLabel("Email:"));
        fieldsPanel.add(emailField);
        fieldsPanel.add(new JLabel("Tipo:"));
        fieldsPanel.add(tipoUsuarioCombo);
        
        // button panel
        JPanel buttonPanel = new JPanel();
        JButton guardarBtn = new JButton("Guardar");
        JButton cancelarBtn = new JButton("Cancelar");
        
        guardarBtn.addActionListener(e -> guardarUsuario());
        cancelarBtn.addActionListener(e -> dispose());
        
        buttonPanel.add(guardarBtn);
        buttonPanel.add(cancelarBtn);
        
        add(fieldsPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        
        pack();
        setLocationRelativeTo(parent);
        setVisible(true);
    }
    
    private void guardarUsuario() {
        String nombre = nombreField.getText();
        String email = emailField.getText();
        TipoPermiso tipo = tipoUsuarioCombo.getSelectedItem().equals("Administrador") ? 
                            TipoPermiso.ADMIN : TipoPermiso.REGULAR;
        
        try {
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