import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
public class PanelAdmin extends JPanel {

    // Referencias a las estructuras
    private MinHeap<Estudiante> minHeap;
    private UniversalHashTable<Estudiante> hashTable;
    private AVLTree<Estudiante> avlTree;

    // Componentes gráficos
    private JTextField txtID, txtNombre, txtPuntaje, txtCorreo, txtCupos;
    private JTable tablaResultados;
    private DefaultTableModel tableModel;

    public PanelAdmin(MinHeap<Estudiante> minHeap, UniversalHashTable<Estudiante> hashTable, AVLTree<Estudiante> avlTree) {
        this.minHeap = minHeap;
        this.hashTable = hashTable;
        this.avlTree = avlTree;

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // --- SECCIÓN SUPERIOR: Formulario y Control ---
        JPanel panelSuperior = new JPanel(new GridLayout(1, 2, 20, 0));

        // 1. Formulario de Registro
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createTitledBorder("Nuevo Estudiante"));

        txtID = new JTextField();
        txtNombre = new JTextField();
        txtPuntaje = new JTextField(); // PBM
        txtCorreo = new JTextField();

        formPanel.add(new JLabel("ID (Cédula):")); formPanel.add(txtID);
        formPanel.add(new JLabel("Nombre:")); formPanel.add(txtNombre);
        formPanel.add(new JLabel("PBM (0-100):")); formPanel.add(txtPuntaje);
        formPanel.add(new JLabel("Correo:")); formPanel.add(txtCorreo);

        JButton btnRegistrar = new JButton("Guardar Estudiante");
        formPanel.add(new JLabel("")); formPanel.add(btnRegistrar);

        // 2. Control de Asignación (Heap)
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        controlPanel.setBorder(BorderFactory.createTitledBorder("Asignación de Cupos"));

        txtCupos = new JTextField(5);
        JButton btnAsignar = new JButton("Ejecutar Asignación (Prioridad PBM)");

        controlPanel.add(new JLabel("Cupos disponibles:"));
        controlPanel.add(txtCupos);
        controlPanel.add(btnAsignar);

        panelSuperior.add(formPanel);
        panelSuperior.add(controlPanel);
        add(panelSuperior, BorderLayout.NORTH);

        // --- SECCIÓN CENTRAL: Tabla de Datos ---
        String[] columnas = {"ID", "Nombre", "PBM", "Correo", "Estado"};
        tableModel = new DefaultTableModel(columnas, 0);
        tablaResultados = new JTable(tableModel);
        add(new JScrollPane(tablaResultados), BorderLayout.CENTER);

        // --- LÓGICA (LISTENERS) ---

        // A. Botón Registrar
        btnRegistrar.addActionListener(e -> {
            try {
                // Validación básica
                if (txtID.getText().isEmpty() || txtNombre.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Por favor llene todos los campos.");
                    return;
                }

                long id = Long.parseLong(txtID.getText());
                String nombre = txtNombre.getText();
                int pbm = Integer.parseInt(txtPuntaje.getText());
                String correo = txtCorreo.getText();

                Estudiante nuevo = new Estudiante(nombre, pbm, correo);
                nuevo.setId(id);

                // Insertar en las 3 estructuras
                minHeap.insert(nuevo);           // Para prioridad
                hashTable.insert((int)id, nuevo); // Para búsqueda rápida
                avlTree.insert(nuevo);           // Para listar ordenado en tabla

                actualizarTabla(); // Refrescar vista
                limpiarCampos();
                JOptionPane.showMessageDialog(this, "Estudiante registrado correctamente.");

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Error: ID y PBM deben ser números.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al guardar: " + ex.getMessage());
            }
        });

        // B. Botón Asignar (Simulación usando Heap)
        btnAsignar.addActionListener(e -> {
            try {
                int k = Integer.parseInt(txtCupos.getText());
                StringBuilder reporte = new StringBuilder("--- ESTUDIANTES ASIGNADOS ---\n");

                for (int i = 0; i < k; i++) {
                    // Validar si hay estudiantes
                    // if (minHeap.isEmpty()) break; (Descomentar si tienen el método isEmpty)

                    Estudiante asignado = minHeap.extractMin(); // Saca el de menor PBM
                    if (asignado != null) {
                        reporte.append(asignado.getNombre())
                                .append(" (PBM: ").append(asignado.getPbm()).append(")\n");
                    }
                }
                JOptionPane.showMessageDialog(this, reporte.toString());
                actualizarTabla(); // Actualizar porque salieron del heap (opcional si queremos que desaparezcan)
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error en asignación: " + ex.getMessage());
            }
        });
    }

    // Método público para que PanelEstudiante pueda refrescar esta tabla
    public void actualizarTabla() {
        tableModel.setRowCount(0); // Limpiar

        // Requiere que AVLTree tenga un método que retorne lista (inOrder)
        // Si no lo tienen, esto lanzará error. Asegúrate de pedir ese método.
        try {
            // Suponiendo que el método se llama inOrderList() o toList()
            List<Estudiante> lista = avlTree.inOrderList();
            for (Estudiante e : lista) {
                tableModel.addRow(new Object[]{e.getId(), e.getNombre(), e.getPbm(), e.getCorreo(), "Registrado"});
            }
        } catch (Exception e) {
            System.out.println("No se pudo listar desde AVL (método faltante): " + e.getMessage());
        }
    }

    private void limpiarCampos() {
        txtID.setText(""); txtNombre.setText(""); txtPuntaje.setText(""); txtCorreo.setText("");
    }
}