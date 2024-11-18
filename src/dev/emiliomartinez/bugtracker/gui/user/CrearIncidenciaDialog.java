package dev.emiliomartinez.bugtracker.gui.user;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import dev.emiliomartinez.bugtracker.services.IncidenciaService;

class CrearIncidenciaDialog extends JDialog {
    private final JTextField nombreField;
    private final JTextArea descripcionArea;
    private final JTextField horasField;
    private final Integer proyectoId;
    private final IncidenciaService incidenciaService;

    public CrearIncidenciaDialog(ListaIncidenciasFrame parent, Integer proyectoId) {
        super(parent, "Crear Incidencia", true);
        this.proyectoId = proyectoId;
        this.incidenciaService = new IncidenciaService();

        nombreField = new JTextField(20);
        descripcionArea = new JTextArea(5, 20);
        horasField = new JTextField(10);

        initComponents();
        pack();
        setLocationRelativeTo(parent);
        setVisible(true);
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));

        JPanel fieldsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        fieldsPanel.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1;
        fieldsPanel.add(nombreField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        fieldsPanel.add(new JLabel("Descripción:"), gbc);
        gbc.gridx = 1;
        fieldsPanel.add(new JScrollPane(descripcionArea), gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        fieldsPanel.add(new JLabel("Horas Estimadas:"), gbc);
        gbc.gridx = 1;
        fieldsPanel.add(horasField, gbc);

        add(fieldsPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton guardarBtn = new JButton("Guardar");
        JButton cancelarBtn = new JButton("Cancelar");

        guardarBtn.addActionListener(e -> guardarIncidencia());
        cancelarBtn.addActionListener(e -> dispose());

        buttonPanel.add(guardarBtn);
        buttonPanel.add(cancelarBtn);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void guardarIncidencia() {
        try {
            String nombre = nombreField.getText().trim();
            String descripcion = descripcionArea.getText().trim();
            Integer horas = Integer.parseInt(horasField.getText().trim());

            if (nombre.isEmpty()) {
                throw new IllegalArgumentException("El nombre no puede estar vacío");
            }

            incidenciaService.crearIncidencia(nombre, descripcion, horas, proyectoId);
            
            ((ListaIncidenciasFrame)getOwner()).cargarIncidencias();
            
            dispose();
            JOptionPane.showMessageDialog(this,
                "Incidencia creada exitosamente",
                "Éxito",
                JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                "Las horas deben ser un número válido",
                "Error",
                JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error al crear la incidencia: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
}
