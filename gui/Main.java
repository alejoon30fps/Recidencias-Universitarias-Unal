import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        // Ejecutar la interfaz en el hilo de eventos de Swing para evitar errores gráficos
        SwingUtilities.invokeLater(() -> {
            try {
                VentanaPrincipal ventana = new VentanaPrincipal();
                ventana.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Error al iniciar la aplicación: " + e.getMessage());
            }
        });
    }
}