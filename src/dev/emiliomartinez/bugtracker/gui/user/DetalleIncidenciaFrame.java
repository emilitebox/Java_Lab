package dev.emiliomartinez.bugtracker.gui.user;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import dev.emiliomartinez.bugtracker.entities.DetalleIncidencia;
import dev.emiliomartinez.bugtracker.services.DetalleIncidenciaService;

public class DetalleIncidenciaFrame extends JFrame {
    private final Integer incidenciaId;
    private final String nombreProyecto;
    private final String nombreIncidencia;
    private final DetalleIncidenciaService detalleService;
    private JTable tablaDetalles;
    private DefaultTableModel modeloTabla;
    private JLabel horasConsumidasLabel;

    public DetalleIncidenciaFrame(Integer incidenciaId, String nombreProyecto, String nombreIncidencia) {
        this.incidenciaId = incidenciaId;
        this.nombreProyecto = nombreProyecto;
        this.nombreIncidencia = nombreIncidencia;
        this.detalleService = new DetalleIncidenciaService();
        
        configurarVentana();
        initComponents();
        cargarDetalles();
        actualizarHorasConsumidas();
        setVisible(true);
    }

    private void configurarVentana() {
        setTitle("Detalle de Incidencia");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
    }

    private void initComponents() {
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("PROYECTO: " + nombreProyecto + " -> INCIDENCIA: " + nombreIncidencia);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);

        String[] columnas = {"ID", "Incidencia ID", "Tarea Realizada", "Horas Invertidas", "Fecha Creación"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaDetalles = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaDetalles);
        add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        
        horasConsumidasLabel = new JLabel("Horas consumidas: 0");
        bottomPanel.add(horasConsumidasLabel);
        
        JButton cargarDetalleBtn = new JButton("Cargar Detalle");
        cargarDetalleBtn.addActionListener(e -> mostrarDialogoCargarDetalle());
        bottomPanel.add(cargarDetalleBtn);
        
        add(bottomPanel, BorderLayout.SOUTH);
    }

    void cargarDetalles() {
        modeloTabla.setRowCount(0);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        
        List<DetalleIncidencia> detalles = detalleService.obtenerDetallesPorIncidencia(incidenciaId);
        for (DetalleIncidencia detalle : detalles) {
            modeloTabla.addRow(new Object[]{
                detalle.getId(),
                detalle.getIncidenciaId(),
                detalle.getTareaRealizada(),
                detalle.getHorasInvertidas(),
                sdf.format(detalle.getFechaCreacion())
            });
        }
    }

    void actualizarHorasConsumidas() {
        Integer horasConsumidas = detalleService.obtenerHorasConsumidas(incidenciaId);
        horasConsumidasLabel.setText(String.format("Horas consumidas: %d", horasConsumidas));
    }

    private void mostrarDialogoCargarDetalle() {
        new CargarDetalleDialog(this, incidenciaId);
    }
}

