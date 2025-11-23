import javax.swing.*;
import java.awt.*;

public class VentanaPrincipal extends JFrame {

    public VentanaPrincipal() {
        // Configuracion de la ventana principal
        setTitle("Residencias UNAL - Asignación Prioritaria");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null); // Centra la ventana

        // Contenedor principal: JTabbedPane
        JTabbedPane panelTabs = new JTabbedPane();

        // 1. Panel del Administrador (Tu panel)
        PanelAdmin panelAdmin = new PanelAdmin();
        panelTabs.addTab("Administrador", null, panelAdmin, "Gestión de Estudiantes y Cupos");

        // 2. Panel del Estudiante (Placeholder de tu compañero)
        JPanel panelEstudiante = new JPanel(new BorderLayout());
        panelEstudiante.add(new JLabel("Vista de Consulta de Estudiante (Pendiente)", SwingConstants.CENTER));
        panelTabs.addTab("Estudiante", null, panelEstudiante, "Consulta de Estado");

        // Añadir el contenedor de pestañas a la ventana
        add(panelTabs);
    }
}