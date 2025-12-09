import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class PanelEstudiante extends JPanel {

    private MinHeap<Estudiante> minHeap;
    private UniversalHashTable<Estudiante> hashTable;
    private AVLTree<Estudiante> avlTree;
    private PanelAdmin panelAdminRef; // Referencia para actualizar la otra pantalla

    private JTextField txtBusquedaID;
    private JTextArea areaResultado;

    public PanelEstudiante(MinHeap<Estudiante> heap, UniversalHashTable<Estudiante> hash, AVLTree<Estudiante> avl, PanelAdmin admin) {
        this.minHeap = heap;
        this.hashTable = hash;
        this.avlTree = avl;
        this.panelAdminRef = admin;

        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // --- SECCIÓN NORTE: Consulta y Testeo ---
        JPanel panelNorte = new JPanel(new GridLayout(2, 1, 10, 10));

        // 1. Panel de Búsqueda
        JPanel busquedaPanel = new JPanel(new FlowLayout());
        txtBusquedaID = new JTextField(15);
        JButton btnBuscar = new JButton("Consultar Estado");
        busquedaPanel.add(new JLabel("Ingrese su ID:"));
        busquedaPanel.add(txtBusquedaID);
        busquedaPanel.add(btnBuscar);

        // 2. Panel de Mock Data (Testeo Masivo)
        JPanel testPanel = new JPanel(new FlowLayout());
        JButton btnGenerarDatos = new JButton("🧪 Generar 50 Datos de Prueba (Aleatorio)");
        btnGenerarDatos.setBackground(new Color(220, 255, 220)); // Color verde claro
        testPanel.add(btnGenerarDatos);

        panelNorte.add(busquedaPanel);
        panelNorte.add(testPanel);
        add(panelNorte, BorderLayout.NORTH);

        // --- SECCIÓN CENTRAL: Resultados ---
        areaResultado = new JTextArea();
        areaResultado.setEditable(false);
        areaResultado.setFont(new Font("Monospaced", Font.PLAIN, 14));
        areaResultado.setBorder(BorderFactory.createTitledBorder("Información del Estudiante"));
        add(new JScrollPane(areaResultado), BorderLayout.CENTER);

        // --- LÓGICA (LISTENERS) ---

        // A. Botón Buscar (Usa Hash Table para O(1))
        btnBuscar.addActionListener(e -> {
            try {
                int idBuscado = Integer.parseInt(txtBusquedaID.getText());
                Estudiante encontrado = hashTable.get(idBuscado);

                if (encontrado != null) {
                    areaResultado.setText("--- ESTUDIANTE ENCONTRADO ---\n\n");
                    areaResultado.append("Nombre: " + encontrado.getNombre() + "\n");
                    areaResultado.append("ID: " + encontrado.getId() + "\n");
                    areaResultado.append("PBM: " + encontrado.getPbm() + "\n");
                    areaResultado.append("Correo: " + encontrado.getCorreo() + "\n");
                } else {
                    areaResultado.setText("No se encontró ningún estudiante con el ID: " + idBuscado);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "El ID debe ser numérico.");
            } catch (Exception ex) {
                areaResultado.setText("Error en búsqueda: " + ex.getMessage());
            }
        });

        // B. Botón Generar Mock Data (Requisito de Entrega)
        btnGenerarDatos.addActionListener(e -> {
            generarDatosPrueba();
            // Actualizar la tabla del administrador automáticamente
            panelAdminRef.actualizarTabla();
            JOptionPane.showMessageDialog(this, "¡Se han generado y cargado 50 estudiantes de prueba!\nVerifique la pestaña 'Administración'.");
        });
    }

    // Método para generar datos aleatorios
    private void generarDatosPrueba() {
        Random rand = new Random();
        String[] nombres = {"Ana", "Carlos", "Sofia", "David", "Laura", "Jorge", "Valentina", "Andres"};
        String[] apellidos = {"Perez", "Gomez", "Rodriguez", "Diaz", "Martinez", "Lopez", "Garcia"};

        for (int i = 0; i < 50; i++) {
            // Datos aleatorios
            String nombre = nombres[rand.nextInt(nombres.length)] + " " + apellidos[rand.nextInt(apellidos.length)];
            int pbm = rand.nextInt(100); // 0 a 99
            int idBase = 1000 + rand.nextInt(9000); // ID entre 1000 y 9999

            // Asegurar unicidad simple para el ID en este loop (opcional)
            int id = idBase + i;

            Estudiante est = new Estudiante(nombre, pbm, "est" + id + "@unal.edu.co");
            est.setId(id);

            // Insertar en TODAS las estructuras
            try {
                minHeap.insert(est);
                hashTable.insert(id, est);
                avlTree.insert(est);
            } catch (Exception ex) {
                System.out.println("Error insertando mock: " + ex.getMessage());
            }
        }
    }
}