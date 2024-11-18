package dev.emiliomartinez.bugtracker.gui.user;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import dev.emiliomartinez.bugtracker.entities.Incidencia;
import dev.emiliomartinez.bugtracker.services.IncidenciaService;

public class ListaIncidenciasFrame extends JFrame {
    private final Integer proyectoId;
    private final String nombreProyecto;
    private final IncidenciaService incidenciaService;
    private JTable tablaIncidencias;
    private DefaultTableModel modeloTabla;

    public ListaIncidenciasFrame(Integer proyectoId, String nombreProyecto) {
        this.proyectoId = proyectoId;
        this.nombreProyecto = nombreProyecto;
        this.incidenciaService = new IncidenciaService();
        
        configurarVentana();
        initComponents();
        cargarIncidencias();
        setVisible(true);
    }

    private void configurarVentana() {
        setTitle("Listado de Incidencias");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
    }

    private void initComponents() {
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("PROYECTO: " + nombreProyecto);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);

        String[] columnas = {"ID", "Nombre", "Horas Est.", "Estado", "Fecha Creación", "", ""};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 5 || column == 6;
            }
        };

        tablaIncidencias = new JTable(modeloTabla);
        
        ButtonRenderer buttonRenderer = new ButtonRenderer("");
        tablaIncidencias.getColumnModel().getColumn(5).setCellRenderer(buttonRenderer);
        tablaIncidencias.getColumnModel().getColumn(5).setCellEditor(
        	    new ButtonEditor("Ver Detalle", tablaIncidencias) {
        	        @Override
        	        public Object getCellEditorValue() {
        	            if (isPushed()) {
        	                int row = table.getSelectedRow();
        	                Integer incidenciaId = (Integer)table.getValueAt(row, 0);
        	                String nombreIncidencia = (String)table.getValueAt(row, 1);
        	                new DetalleIncidenciaFrame(incidenciaId, nombreProyecto, nombreIncidencia);
        	            }
        	            setPushed(false);
        	            return label();
        	        }
        	    }
        	);

        tablaIncidencias.getColumnModel().getColumn(6).setCellRenderer(buttonRenderer);
        tablaIncidencias.getColumnModel().getColumn(6).setCellEditor(
            new ButtonEditor("Cerrar", tablaIncidencias) {
                @Override
                public Object getCellEditorValue() {
                    if (isPushed()) {
                        int row = table.getSelectedRow();
                        Integer incidenciaId = (Integer)table.getValueAt(row, 0);
                        cerrarIncidencia(incidenciaId);
                    }
                    setPushed(false);
                    return label();
                }
            }
        );

        JScrollPane scrollPane = new JScrollPane(tablaIncidencias);
        add(scrollPane, BorderLayout.CENTER);

        JButton crearIncidenciaBtn = new JButton("Crear Incidencia");
        crearIncidenciaBtn.addActionListener(e -> mostrarDialogoCrearIncidencia());

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.add(crearIncidenciaBtn);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    void cargarIncidencias() {
        modeloTabla.setRowCount(0);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        
        List<Incidencia> incidencias = incidenciaService.obtenerIncidenciasPorProyecto(proyectoId);
        for (Incidencia incidencia : incidencias) {
            String estadoString = incidencia.getEstadoId() == 1 ? "Abierto" : "Cerrado";

            modeloTabla.addRow(new Object[]{
                incidencia.getId(),
                incidencia.getNombreIncidencia(),
                incidencia.getHorasEstimadas(),
                estadoString,
                sdf.format(incidencia.getFechaCreacion()),
                "Ver Detalle",
                "Cerrar"
            });
        }
    }

    private void cerrarIncidencia(Integer incidenciaId) {
        try {
            incidenciaService.cerrarIncidencia(incidenciaId);
            cargarIncidencias(); 
            JOptionPane.showMessageDialog(this,
                "Incidencia cerrada exitosamente",
                "Éxito",
                JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error al cerrar la incidencia: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void mostrarDialogoCrearIncidencia() {
        new CrearIncidenciaDialog(this, proyectoId);
    }
}