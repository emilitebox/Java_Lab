package dev.emiliomartinez.bugtracker.gui.user;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import dev.emiliomartinez.bugtracker.entities.Proyecto;
import dev.emiliomartinez.bugtracker.entities.Usuario;
import dev.emiliomartinez.bugtracker.services.ProyectoService;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class ListaProyectosFrame extends JFrame {
    private final Usuario usuario;
    private final ProyectoService proyectoService;
    private JTable tablaProyectos;
    private DefaultTableModel modeloTabla;
    
    public ListaProyectosFrame(Usuario usuario) {
        this.usuario = usuario;
        this.proyectoService = new ProyectoService();
        
        configurarVentana();
        initComponents();
        cargarProyectos();
        setVisible(true);
    }
    
    private void configurarVentana() {
        setTitle("Listado de Proyectos");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
    }
    
    private void initComponents() {
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("LISTADO DE PROYECTOS");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);
        
        String[] columnas = {"ID", "Nombre Proyecto", "Fecha Creación", ""};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3; // Solo el botón es editable
            }
        };
        
        tablaProyectos = new JTable(modeloTabla);
        
        tablaProyectos.getColumnModel().getColumn(3).setCellRenderer(new ButtonRenderer("Ver Detalle"));
        tablaProyectos.getColumnModel().getColumn(3).setCellEditor(new ButtonEditor("Ver Detalle"));
        
        tablaProyectos.getColumnModel().getColumn(0).setPreferredWidth(50);  // ID
        tablaProyectos.getColumnModel().getColumn(1).setPreferredWidth(300); // Nombre
        tablaProyectos.getColumnModel().getColumn(2).setPreferredWidth(150); // Fecha
        tablaProyectos.getColumnModel().getColumn(3).setPreferredWidth(100); // Botón
        
        JScrollPane scrollPane = new JScrollPane(tablaProyectos);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(scrollPane, BorderLayout.CENTER);
    }
    
    private void cargarProyectos() {
        modeloTabla.setRowCount(0);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        
        List<Proyecto> proyectos = proyectoService.obtenerProyectosPorUsuario(usuario.getId());
        for (Proyecto proyecto : proyectos) {
            modeloTabla.addRow(new Object[]{
                proyecto.getId(),
                proyecto.getNombreProyecto(),
                sdf.format(proyecto.getFecha()),
                "Ver Detalle"
            });
        }
    }
}
