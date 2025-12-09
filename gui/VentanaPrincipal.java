import javax.swing.*;
import java.awt.*;

import javax.swing.*;

public class VentanaPrincipal extends JFrame {

    // ESTRUCTURAS DE DATOS (El Modelo Unificado)
    // Se declaran aquí para que vivan durante toda la ejecución
    private MinHeap<Estudiante> minHeap;
    private UniversalHashTable<Estudiante> hashTable;
    private AVLTree<Estudiante> avlTree;

    public VentanaPrincipal() {
        // 1. Configuración de la Ventana
        setTitle("Sistema de Asignación de Residencias - UNAL (Entrega Final)");
        setSize(1100, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centrar en pantalla

        // 2. Inicialización de Estructuras
        // Ajusta la capacidad del Heap según necesites (ej. 1000)
        minHeap = new MinHeap<>(1000);
        hashTable = new UniversalHashTable<>();
        avlTree = new AVLTree<>();

        // 3. Configuración de Pestañas
        JTabbedPane tabs = new JTabbedPane();

        // 4. Inyección de Dependencias
        // Pasamos las MISMAS estructuras a ambos paneles para que compartan los datos
        PanelAdmin panelAdmin = new PanelAdmin(minHeap, hashTable, avlTree);

        // Pasamos 'panelAdmin' al estudiante para poder refrescar la tabla automáticamente al generar datos
        PanelEstudiante panelEstudiante = new PanelEstudiante(minHeap, hashTable, avlTree, panelAdmin);

        tabs.addTab("Administración (Registro y Asignación)", null, panelAdmin, "Gestión de cupos");
        tabs.addTab("Consulta Estudiante (y Mock Data)", null, panelEstudiante, "Vista de usuario y Pruebas");

        add(tabs);
    }
}