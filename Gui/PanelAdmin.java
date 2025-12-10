package Gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import Estructuras.AVLEstudiante;
import Estructuras.Estudiante;

public class PanelAdmin extends JPanel {
    private AVLEstudiante arbol;
    private JTextField idEliminarField, pbmField, nuevoPbmField;
    private JTextArea logArea;

    public PanelAdmin(AVLEstudiante arbol) {
        this.arbol = arbol;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        initUI();
    }

    private void initUI() {
        // PANEL SUPERIOR - Título
        JLabel titulo = new JLabel(" Panel de Administrador", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 28));
        titulo.setForeground(new Color(200, 0, 0));
        add(titulo, BorderLayout.NORTH);

        // PANEL CENTRAL - Operaciones
        JPanel panelCentral = new JPanel(new GridLayout(2, 1, 10, 10));

        // 1. PANEL ELIMINAR
        JPanel panelEliminar = new JPanel(new GridBagLayout());
        panelEliminar.setBorder(BorderFactory.createTitledBorder("❌ Eliminar Estudiante"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0; gbc.gridy = 0;
        panelEliminar.add(new JLabel("ID del estudiante:"), gbc);

        gbc.gridx = 1;
        idEliminarField = new JTextField(15);
        panelEliminar.add(idEliminarField, gbc);

        gbc.gridx = 2;
        JButton btnEliminar = new JButton("🗑️ Eliminar");
        btnEliminar.setBackground(new Color(200, 50, 50));
        btnEliminar.setForeground(Color.WHITE);
        btnEliminar.addActionListener(e -> eliminarEstudiante());
        panelEliminar.add(btnEliminar, gbc);

        // 2. PANEL MODIFICAR PBM
        JPanel panelModificar = new JPanel(new GridBagLayout());
        panelModificar.setBorder(BorderFactory.createTitledBorder("✏️ Modificar PBM"));

        gbc.gridx = 0; gbc.gridy = 0;
        panelModificar.add(new JLabel("ID del estudiante:"), gbc);

        gbc.gridx = 1;
        pbmField = new JTextField(15);
        panelModificar.add(pbmField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panelModificar.add(new JLabel("Nuevo PBM:"), gbc);

        gbc.gridx = 1;
        nuevoPbmField = new JTextField(15);
        panelModificar.add(nuevoPbmField, gbc);

        gbc.gridx = 2; gbc.gridy = 0; gbc.gridheight = 2;
        JButton btnModificar = new JButton("📝 Modificar");
        btnModificar.setBackground(new Color(50, 100, 200));
        btnModificar.setForeground(Color.WHITE);
        btnModificar.addActionListener(e -> modificarPBM());
        panelModificar.add(btnModificar, gbc);

        panelCentral.add(panelEliminar);
        panelCentral.add(panelModificar);
        add(panelCentral, BorderLayout.CENTER);

        // PANEL INFERIOR - Log/Resultados
        JPanel panelLog = new JPanel(new BorderLayout());
        panelLog.setBorder(BorderFactory.createTitledBorder("📝 Log de Operaciones"));

        logArea = new JTextArea(15, 50);
        logArea.setEditable(false);
        logArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scroll = new JScrollPane(logArea);

        JButton btnLimpiarLog = new JButton("Limpiar Log");
        btnLimpiarLog.addActionListener(e -> logArea.setText(""));

        panelLog.add(scroll, BorderLayout.CENTER);
        panelLog.add(btnLimpiarLog, BorderLayout.SOUTH);

        add(panelLog, BorderLayout.SOUTH);
    }

    private void eliminarEstudiante() {
        try {
            long id = Long.parseLong(idEliminarField.getText().trim());


            Estudiante encontrado = buscarEstudiantePorId(id);

            if (encontrado != null) {

                boolean eliminado = arbol.remove(encontrado);
                if (eliminado) {
                    logArea.append("✅ Eliminado: ID=" + id + " - " + encontrado.getNombre() + "\n");
                    idEliminarField.setText("");
                } else {
                    logArea.append("❌ Error al eliminar ID=" + id + "\n");
                }
            } else {
                logArea.append("❌ No encontrado: ID=" + id + "\n");
                JOptionPane.showMessageDialog(this,
                        "No se encontró estudiante con ID: " + id);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "ID debe ser un número válido", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void modificarPBM() {
        try {
            long id = Long.parseLong(pbmField.getText().trim());
            int nuevoPbm = Integer.parseInt(nuevoPbmField.getText().trim());

            if (nuevoPbm < 1 || nuevoPbm > 100) {
                JOptionPane.showMessageDialog(this, "PBM debe estar entre 1 y 100");
                return;
            }

            // Buscar estudiante por ID
            Estudiante encontrado = buscarEstudiantePorId(id);

            if (encontrado != null) {

                Estudiante viejo = new Estudiante(
                        encontrado.getNombre(),
                        encontrado.getPbm(),
                        encontrado.getCorreo()
                );

                viejo.setId(encontrado.getId());


                boolean modificado = arbol.changeKey(viejo, nuevoPbm);

                if (modificado) {
                    logArea.append("✏️ Modificado: ID=" + id +
                            " - " + encontrado.getNombre() +
                            " - Nuevo PBM=" + nuevoPbm + "\n");
                    pbmField.setText("");
                    nuevoPbmField.setText("");
                } else {
                    logArea.append("❌ Error modificando ID=" + id + "\n");
                }
            } else {
                logArea.append("❌ No encontrado para modificar: ID=" + id + "\n");
                JOptionPane.showMessageDialog(this, "Estudiante no encontrado");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID y PBM deben ser números válidos");
        }
    }

    // MÉTODO CORREGIDO: Busca por ID sin acceder a nodos privados
    private Estudiante buscarEstudiantePorId(long id) {
        // Usamos getByIndexInOrder para recorrer todos los estudiantes
        int totalEstudiantes = arbol.totalNodos();

        for (int i = 0; i < totalEstudiantes; i++) {
            Estudiante estudiante = arbol.getByIndexInOrder(i);
            if (estudiante != null && estudiante.getId() == id) {
                return estudiante;
            }
        }
        return null;
    }

    // Botón extra para ver todos los IDs (útil para debugging)
    private JButton crearBotonDebug() {
        JButton btnDebug = new JButton("🔍 Ver todos los IDs");
        btnDebug.addActionListener(e -> {
            StringBuilder sb = new StringBuilder();
            sb.append("IDs en el sistema:\n");
            int total = arbol.totalNodos();
            for (int i = 0; i < total; i++) {
                Estudiante e1 = arbol.getByIndexInOrder(i);
                if (e1 != null) {
                    sb.append("ID: ").append(e1.getId())
                            .append(" - ").append(e1.getNombre())
                            .append(" - PBM: ").append(e1.getPbm())
                            .append("\n");
                }
            }
            JOptionPane.showMessageDialog(this, sb.toString(), "IDs Registrados",
                    JOptionPane.INFORMATION_MESSAGE);
        });
        return btnDebug;
    }
}