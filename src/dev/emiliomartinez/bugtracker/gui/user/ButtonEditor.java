package dev.emiliomartinez.bugtracker.gui.user;
import java.awt.Component;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTable;

class ButtonEditor extends DefaultCellEditor {
    protected JButton button;
    private String label;
    private boolean isPushed;
    protected final JTable table;

    public ButtonEditor(String text, JTable table) {
        super(new JCheckBox());
        this.table = table;
        button = new JButton(text);
        button.setOpaque(true);
        button.addActionListener(e -> fireEditingStopped());
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
        label = value.toString();
        button.setText(label);
        isPushed = true;
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        if (isPushed) {
            int row = table.getSelectedRow();
            Integer proyectoId = (Integer)table.getValueAt(row, 0);
            String nombreProyecto = (String)table.getValueAt(row, 1);
            new ListaIncidenciasFrame(proyectoId, nombreProyecto);
        }
        isPushed = false;
        return label;
    }

    protected boolean isPushed() {
        return isPushed;
    }

    protected void setPushed(boolean pushed) {
        isPushed = pushed;
    }

    protected String label() {
        return label;
    }
}
