package Gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import Estructuras.AVLEstudiante;
import Estructuras.Estudiante;

public class PanelEstudiante extends JPanel {
    private AVLEstudiante arbol;
    private JTextField nombreField, pbmField, correoField;
    private JTextArea resultadoArea;

    public PanelEstudiante(AVLEstudiante arbol) {
        this.arbol = arbol;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        initUI();
    }

    private void initUI() {
        // PANEL SUPERIOR - Título
        JLabel titulo = new JLabel("Panel de Estudiante", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 28));
        titulo.setForeground(new Color(0, 100, 200));
        add(titulo, BorderLayout.NORTH);

        // PANEL CENTRAL - Formulario
        JPanel panelForm = new JPanel(new GridBagLayout());
        panelForm.setBorder(BorderFactory.createTitledBorder("Registrar Nuevo Estudiante"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Nombre
        gbc.gridx = 0; gbc.gridy = 0;
        panelForm.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1;
        nombreField = new JTextField(20);
        panelForm.add(nombreField, gbc);

        // PBM
        gbc.gridx = 0; gbc.gridy = 1;
        panelForm.add(new JLabel("PBM (1-100):"), gbc);
        gbc.gridx = 1;
        pbmField = new JTextField(10);
        panelForm.add(pbmField, gbc);

        // Correo
        gbc.gridx = 0; gbc.gridy = 2;
        panelForm.add(new JLabel("Correo:"), gbc);
        gbc.gridx = 1;
        correoField = new JTextField(20);
        panelForm.add(correoField, gbc);

        // Botones
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        JPanel panelBotones = new JPanel(new FlowLayout());

        JButton btnInsertar = new JButton("➕ Insertar Estudiante");
        btnInsertar.setBackground(new Color(50, 150, 50));
        btnInsertar.setForeground(Color.WHITE);
        btnInsertar.addActionListener(e -> insertarEstudiante());

        JButton btnBuscar = new JButton("🔍 Buscar por PBM");
        btnBuscar.setBackground(new Color(50, 100, 200));
        btnBuscar.setForeground(Color.WHITE);
        btnBuscar.addActionListener(e -> buscarPorPBM());

        panelBotones.add(btnInsertar);
        panelBotones.add(btnBuscar);
        panelForm.add(panelBotones, gbc);

        add(panelForm, BorderLayout.CENTER);

        // PANEL INFERIOR - Resultados
        JPanel panelResultado = new JPanel(new BorderLayout());
        panelResultado.setBorder(BorderFactory.createTitledBorder("Resultados"));

        resultadoArea = new JTextArea(10, 40);
        resultadoArea.setEditable(false);
        resultadoArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scroll = new JScrollPane(resultadoArea);

        panelResultado.add(scroll, BorderLayout.CENTER);

        // Botón para listar todos
        JButton btnListar = new JButton("📋 Listar Todos los Estudiantes");
        btnListar.addActionListener(e -> listarEstudiantes());
        panelResultado.add(btnListar, BorderLayout.SOUTH);

        add(panelResultado, BorderLayout.SOUTH);
    }

    private void insertarEstudiante() {
        try {
            String nombre = nombreField.getText().trim();
            String correo = correoField.getText().trim();
            int pbm = Integer.parseInt(pbmField.getText().trim());

            if (nombre.isEmpty() || correo.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nombre y correo son obligatorios");
                return;
            }

            if (pbm < 1 || pbm > 100) {
                JOptionPane.showMessageDialog(this, "PBM debe estar entre 1 y 100");
                return;
            }

            Estudiante nuevo = new Estudiante(nombre, pbm, correo);
            arbol.insertar(nuevo);

            resultadoArea.append("✅ Estudiante insertado: " + nuevo + "\n");
            limpiarCampos();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "PBM debe ser un número", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void buscarPorPBM() {
        try {
            int pbm = Integer.parseInt(JOptionPane.showInputDialog(this, "Ingrese PBM a buscar:"));

            // Crear temporal para búsqueda
            Estudiante temp = new Estudiante("", pbm, "");
            Estudiante encontrado = arbol.buscar(temp);

            if (encontrado != null) {
                resultadoArea.append("🔍 Encontrado: " + encontrado + "\n");
            } else {
                resultadoArea.append("❌ No se encontró estudiante con PBM=" + pbm + "\n");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "PBM inválido", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void listarEstudiantes() {
        resultadoArea.setText("");
        resultadoArea.append("LISTA DE ESTUDIANTES (Ordenados por PBM)\n");
        resultadoArea.append("===========================================\n");
        resultadoArea.append(arbol.listarPrioridad());
        resultadoArea.append("\nTotal: " + arbol.totalNodos() + " estudiantes\n");
    }

    private void limpiarCampos() {
        nombreField.setText("");
        pbmField.setText("");
        correoField.setText("");
    }
}