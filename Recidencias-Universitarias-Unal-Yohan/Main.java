import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        // Ejecutar la interfaz gráfica en el Event Dispatch Thread (hilo de Swing)
        SwingUtilities.invokeLater(() -> {
            VentanaPrincipal ventana = new VentanaPrincipal();
            ventana.setVisible(true);
        });
    }
}