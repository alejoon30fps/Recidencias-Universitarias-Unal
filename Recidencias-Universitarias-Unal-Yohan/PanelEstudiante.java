import javax.swing.*;
import java.awt.*;
public class PanelEstudiante extends JPanel {

    private JTextField txtIDConsulta;
    private JButton btnConsultar;
    private JTextArea areaResultado;

    public PanelEstudiante() {
        // ERROR: La clase BorderLayout no está importada
        setLayout(new BorderLayout(10, 10));
        // ERROR: La clase BorderFactory no está importada
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // --- Panel Superior: Formulario de Consulta ---
        // ERROR: La clase FlowLayout no está importada
        JPanel panelConsulta = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        // ERROR: Las clases JTextField, JButton, JLabel, JTextArea no están importadas
        txtIDConsulta = new JTextField(15);
        btnConsultar = new JButton("Consultar Estado de Cupo");

        panelConsulta.add(new JLabel("Ingrese su ID de Estudiante:"));
        panelConsulta.add(txtIDConsulta);
        panelConsulta.add(btnConsultar);

        add(panelConsulta, BorderLayout.NORTH);

        // --- Panel Central: Área de Resultados ---
        areaResultado = new JTextArea("Estado de la asignación aparecerá aquí.");
        areaResultado.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(areaResultado);

        add(scrollPane, BorderLayout.CENTER);
    }
}