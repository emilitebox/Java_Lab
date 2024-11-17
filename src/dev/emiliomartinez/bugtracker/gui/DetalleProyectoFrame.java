package dev.emiliomartinez.bugtracker.gui;
import javax.swing.*;
import java.awt.*;

class DetalleProyectoFrame extends JFrame {
    public DetalleProyectoFrame(String nombreProyecto) {
        setTitle("Proyecto: " + nombreProyecto);
        setSize(800, 600);
        setLocationRelativeTo(null);
        
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // list
        DefaultListModel<String> listModel = new DefaultListModel<>();
        listModel.addElement("Incidencia 1 - Alta prioridad");
        listModel.addElement("Incidencia 2 - Media prioridad");
        listModel.addElement("Incidencia 3 - Baja prioridad");
        
        JList<String> incidenciasList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(incidenciasList);
        
        // create
        JButton crearIncidenciaBtn = new JButton("Crear Nueva Incidencia");
        crearIncidenciaBtn.addActionListener(e -> mostrarDialogoCrearIncidencia());
        
        mainPanel.add(new JLabel("Incidencias del proyecto " + nombreProyecto), BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(crearIncidenciaBtn, BorderLayout.SOUTH);
        
        setContentPane(mainPanel);
        setVisible(true);
    }
    
    private void mostrarDialogoCrearIncidencia() {
        JOptionPane.showMessageDialog(this, 
            "Funcionalidad de crear incidencia en desarrollo", 
            "En construcción", 
            JOptionPane.INFORMATION_MESSAGE);
    }
}
