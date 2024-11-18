package dev.emiliomartinez.bugtracker.gui.user;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.*;
import dev.emiliomartinez.bugtracker.services.DetalleIncidenciaService;

class CargarDetalleDialog extends JDialog {
    private final JTextArea tareaField;
    private final JTextField horasField;
    private final Integer incidenciaId;
    private final DetalleIncidenciaService detalleService;
    private final DetalleIncidenciaFrame parentFrame;

    public CargarDetalleDialog(DetalleIncidenciaFrame parent, Integer incidenciaId) {
        super(parent, "Cargar Detalle", true);
        this.incidenciaId = incidenciaId;
        this.detalleService = new DetalleIncidenciaService();
        this.parentFrame = parent;

        tareaField = new JTextArea(5, 30);
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
        fieldsPanel.add(new JLabel("Tarea Realizada:"), gbc);
        gbc.gridx = 1;
        fieldsPanel.add(new JScrollPane(tareaField), gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        fieldsPanel.add(new JLabel("Horas Invertidas:"), gbc);
        gbc.gridx = 1;
        fieldsPanel.add(horasField, gbc);

        add(fieldsPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton guardarBtn = new JButton("Guardar");
        JButton cancelarBtn = new JButton("Cancelar");

        guardarBtn.addActionListener(e -> guardarDetalle());
        cancelarBtn.addActionListener(e -> dispose());

        buttonPanel.add(guardarBtn);
        buttonPanel.add(cancelarBtn);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void guardarDetalle() {
        try {
            String tarea = tareaField.getText().trim();
            Integer horas = Integer.parseInt(horasField.getText().trim());

            if (tarea.isEmpty()) {
                throw new IllegalArgumentException("La tarea no puede estar vacía");
            }
            if (horas <= 0) {
                throw new IllegalArgumentException("Las horas deben ser un número positivo");
            }

            detalleService.crearDetalle(incidenciaId, tarea, horas);
            parentFrame.cargarDetalles();
            parentFrame.actualizarHorasConsumidas();
            dispose();
            
            JOptionPane.showMessageDialog(this,
                "Detalle guardado exitosamente",
                "Éxito",
                JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                "Las horas deben ser un número entero",
                "Error",
                JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error al guardar detalle: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
}
