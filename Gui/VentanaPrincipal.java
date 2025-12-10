package Gui;

import javax.swing.*;
import java.awt.*;
import Estructuras.AVLEstudiante;

public class VentanaPrincipal extends JFrame {
    private AVLEstudiante arbolEstudiantes;

    public VentanaPrincipal() {
        arbolEstudiantes = new AVLEstudiante();

        setTitle("Sistema de Gestión de Estudiantes - AVL");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initUI();
    }

    private void initUI() {
        JTabbedPane tabbedPane = new JTabbedPane();

        // Solo dos pestañas
        tabbedPane.addTab("👨‍💼 Administrador", new PanelAdmin(arbolEstudiantes));
        tabbedPane.addTab("👨‍🎓 Estudiante", new PanelEstudiante(arbolEstudiantes));

        add(tabbedPane);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VentanaPrincipal ventana = new VentanaPrincipal();
            ventana.setVisible(true);
        });
    }
}
