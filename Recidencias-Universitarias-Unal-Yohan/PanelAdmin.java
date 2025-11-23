import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelAdmin extends JPanel {

    // Componentes del Formulario
    private JTextField txtID, txtNombre, txtPuntaje, txtCupos;
    private JButton btnRegistrar, btnAsignar, btnModificar, btnEliminar;

    // Componentes de la Tabla
    private JTable tablaResultados;
    private DefaultTableModel tableModel;

    public PanelAdmin() {
        setLayout(new GridLayout(2, 1)); // Dividir el panel en dos filas

        // --- Panel Superior: Formulario y Botones ---
        JPanel panelSuperior = new JPanel(new GridLayout(1, 2));

        // Panel de Formulario
        JPanel panelFormulario = new JPanel(new GridLayout(5, 2, 5, 5));
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Gestión de Estudiantes"));

        // Inicialización de JTextFields
        txtID = new JTextField();
        txtNombre = new JTextField();
        txtPuntaje = new JTextField();
        txtCupos = new JTextField();

        panelFormulario.add(new JLabel("ID:"));
        panelFormulario.add(txtID);
        panelFormulario.add(new JLabel("Nombre:"));
        panelFormulario.add(txtNombre);
        panelFormulario.add(new JLabel("Puntaje SocioEconómico (Bajo = Prioridad):"));
        panelFormulario.add(txtPuntaje);
        panelFormulario.add(new JLabel("Cupos Disponibles:"));
        panelFormulario.add(txtCupos);
        panelFormulario.add(new JLabel(""));

        // Panel de Botones
        JPanel panelBotones = new JPanel(new GridLayout(5, 1, 10, 10));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Inicialización de JButtons
        btnRegistrar = new JButton("Registrar Estudiante");
        btnAsignar = new JButton("Asignar Cupos");
        btnModificar = new JButton("Modificar Puntaje");
        btnEliminar = new JButton("Eliminar Estudiante");

        panelBotones.add(btnRegistrar);
        panelBotones.add(btnModificar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnAsignar);

        // Agregar componentes a la fila superior
        panelSuperior.add(panelFormulario);
        panelSuperior.add(panelBotones);
        add(panelSuperior);

        // --- Panel Inferior: Tabla de Resultados ---

        String[] columnas = {"ID", "Nombre", "Puntaje", "Estado"};
        tableModel = new DefaultTableModel(columnas, 0);
        tablaResultados = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(tablaResultados);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Resultados de Asignación / Listado de Estudiantes"));

        add(scrollPane);

    }
}