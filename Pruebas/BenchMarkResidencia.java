package Pruebas;

import Modulos.Residencia;
import Estructuras.Estudiante;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class BenchMarkResidencia {

    @SuppressWarnings("unchecked")
    public static void main(String[] args) {

        String[] archivos = {
    "mockup_100.csv",
    "mockup_1000.csv",
    "mockup_10000.csv",
    "mockup_100000.csv"
};


        System.out.println("========= BENCHMARK COMPLETO RESIDENCIA =========");

        for (String archivo : archivos) {


    File f = new File(archivo);
    System.out.println("\n--- Archivo: " + f.getAbsolutePath() + " ---");
    System.out.println("Existe?" + f.exists());            


    List<Estudiante> datos = cargarMockup(archivo);

    int n = datos.size();
    System.out.println("\n\n===== BENCHMARK CON ARCHIVO " + archivo + " (N=" + n + ") =====");

    Residencia r = new Residencia();

    long tInsertAVL = 0, tInsertHeap = 0, tInsertHash = 0;

    for (Estudiante e : datos) {
        long tStart, tEnd;

        tStart = System.nanoTime();
        r.ordenPrioridad.insertar(e);
        tInsertAVL += System.nanoTime() - tStart;

        tStart = System.nanoTime();
        r.colaPrioridad.insert(e);
        tInsertHeap += System.nanoTime() - tStart;

        tStart = System.nanoTime();
        r.estudiantesPorID.insert(e.getId(), e);
        tInsertHash += System.nanoTime() - tStart;
    }

    System.out.println("Tiempo Inserción AVL: " + (tInsertAVL / 1_000_000) + " ms");
    System.out.println("Tiempo Inserción Heap: " + (tInsertHeap / 1_000_000) + " ms");
    System.out.println("Tiempo Inserción Hash: " + (tInsertHash / 1_000_000) + " ms");

    // --- BÚSQUEDA ---
    long tSearch = 0;
    for (int i = 0; i < 5000; i++) {
        long id = 1 + (long)(Math.random() * n);
        long tStart = System.nanoTime();
        r.queryEstudianteId(id);
        tSearch += System.nanoTime() - tStart;
    }
    System.out.println("Tiempo búsqueda (5k ops): " + (tSearch / 1_000_000) + " ms");

    // --- CAMBIO DE PBM ---
    long tChange = 0;
    for (int i = 0; i < 5000; i++) {
        long id = 1 + (long)(Math.random() * n);
        int nuevoPBM = (int)(Math.random() * 100);
        long tStart = System.nanoTime();
        r.changeValue(nuevoPBM, id);
        tChange += System.nanoTime() - tStart;
    }
    System.out.println("Tiempo cambio PBM (5k ops): " + (tChange / 1_000_000) + " ms");

    // --- ELIMINACIÓN ---
    long tDelete = 0;
    for (int i = 0; i < 3000; i++) {
        long id = 1 + (long)(Math.random() * n);
        long tStart = System.nanoTime();
        r.eliminarEstudiante(id);
        tDelete += System.nanoTime() - tStart;
    }
    System.out.println("Tiempo eliminación (3k ops): " + (tDelete / 1_000_000) + " ms");

    // --- ASIGNAR CUPOS ---
    r.setCupos(5000);
    long tStart = System.nanoTime();
    r.asigCupos();
    System.out.println("Tiempo asignar cupos: " + ((System.nanoTime() - tStart) / 1_000_000) + " ms");

    // --- LISTA ORDENADA ---
    tStart = System.nanoTime();
    r.listarEstudiantesPorPrioridad();
    System.out.println("Tiempo listar (InOrder): " + ((System.nanoTime() - tStart) / 1_000_000) + " ms");
}

    }

    private static List<Estudiante> cargarMockup(String ruta) {
    List<Estudiante> lista = new ArrayList<>();

    try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
        String linea = br.readLine();

        // Quitar BOM si existe
        if (linea != null) {
            linea = linea.replace("\uFEFF", "");
        }

        // Saltar encabezado
        if (linea != null && linea.toLowerCase().contains("id")) {
            linea = null;
        }

        while ((linea = br.readLine()) != null) {

            linea = linea.trim();
            if (linea.isEmpty()) continue;

            // AHORA SÍ: separador correcto
            String[] p = linea.split("\\s*;\\s*");

            if (p.length != 4) {
                System.out.println("Línea ignorada (mal formato): " + linea);
                continue;
            }

            long id = Long.parseLong(p[0]);
            String nombre = p[1];
            int pbm = Integer.parseInt(p[2]);
            String correo = p[3];

            Estudiante e = new Estudiante(nombre, pbm, correo);
            e.setId(id);

            lista.add(e);
        }

    } catch (Exception ex) {
        ex.printStackTrace();
    }

    return lista;
}



}
