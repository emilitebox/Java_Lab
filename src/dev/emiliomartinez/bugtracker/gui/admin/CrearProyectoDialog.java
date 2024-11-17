package dev.emiliomartinez.bugtracker.gui.admin;
import javax.swing.*;

import dev.emiliomartinez.bugtracker.services.ProyectoService;

import java.awt.*;

public class CrearProyectoDialog extends JDialog {
    private final JTextField nombreField;
    private final ProyectoService proyectoService;
    private final Integer userId;
    
    public CrearProyectoDialog(JFrame parent, Integer userId) {
        super(parent, "Crear Proyecto", true);
        this.proyectoService = new ProyectoService();
        this.userId = userId;
        
        nombreField = new JTextField(20);
        
        initComponents();
        
        pack();
        setLocationRelativeTo(parent);
        setVisible(true);
    }
    
    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        
        JPanel fieldsPanel = new JPanel(new GridLayout(1, 2, 5, 5));
        fieldsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        fieldsPanel.add(new JLabel("Nombre del Proyecto:"));
        fieldsPanel.add(nombreField);
        
        JPanel buttonPanel = new JPanel();
        JButton guardarBtn = new JButton("Guardar");
        JButton cancelarBtn = new JButton("Cancelar");
        
        guardarBtn.addActionListener(e -> guardarProyecto());
        cancelarBtn.addActionListener(e -> dispose());
        
        buttonPanel.add(guardarBtn);
        buttonPanel.add(cancelarBtn);
        
        add(fieldsPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private void guardarProyecto() {
        try {
            String nombre = nombreField.getText().trim();
            
            // Validación
            if (nombre.isEmpty()) {
                throw new IllegalArgumentException("El nombre del proyecto no puede estar vacío");
            }
            
            proyectoService.crearProyecto(nombre, userId);
            
            JOptionPane.showMessageDialog(this, 
                "Proyecto creado exitosamente", 
                "Éxito", 
                JOptionPane.INFORMATION_MESSAGE);
            dispose();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error al crear proyecto: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
}