package Pruebas;

import Estructuras.Estudiante;
import Estructuras.MinHeap;

public class TestMinHeap {

    public static void main(String[] args) {

        System.out.println("========= TEST UNITARIO MINHEAP =========\n");

        MinHeap<Estudiante> heap = new MinHeap<>(10);

        // Crear estudiantes
        Estudiante e1 = new Estudiante("Ana", 40, "ana@mail.com");
        Estudiante e2 = new Estudiante("Luis", 10, "luis@mail.com");
        Estudiante e3 = new Estudiante("Juan", 80, "juan@mail.com");
        Estudiante e4 = new Estudiante("Maria", 30, "maria@mail.com");

        System.out.println(">>> Insertando 4 estudiantes...\n");

        heap.insert(e1);
        heap.insert(e2);
        heap.insert(e3);
        heap.insert(e4);

        imprimirEstado(heap);

        // EXTRAER MIN (debe ser PBM = 10)
        System.out.println("\n>>> ExtractMin() (esperado: Luis con PBM 10)");
        Estudiante min1 = heap.extractMin();
        System.out.println("Obtenido: " + min1);

        imprimirEstado(heap);

        // CAMBIAR CLAVE (Maria 30 → 5)
        System.out.println("\n>>> changeKey(Maria, newPBM=5) (debe moverla al inicio)...");
        e4.setPbm(5);
        heap.changeKey(e4, e4);

        imprimirEstado(heap);

        // REMOVE Ana (PBM 40)
        System.out.println("\n>>> remove(Ana)...");
        boolean removed = heap.remove(e1);
        System.out.println("¿Ana eliminada?: " + removed);

        imprimirEstado(heap);

        // EXTRAER MIN (debe ser Maria con PBM 5)
        System.out.println("\n>>> ExtractMin() (esperado: Maria 5)...");
        Estudiante min2 = heap.extractMin();
        System.out.println("Obtenido: " + min2);

        imprimirEstado(heap);

        // Validar índices
        System.out.println("\n>>> Validando índices internos (indexHeap)...");
        validarIndices(heap);

        System.out.println("\n========= FIN TEST MINHEAP =========");
    }


    // ==========================================================
    // IMPRIME EL ESTADO INTERNO DEL HEAP (pbm + indexHeap)
    // ==========================================================
    private static void imprimirEstado(MinHeap<Estudiante> heap) {
        System.out.println("\n-- Estado del Heap (size = " + heap.size() + ") --");
        for (int i = 0; i < heap.size(); i++) {
            Estudiante e = obtener(heap, i);
            System.out.println("pos[" + i + "] = " + e + " | indexHeap=" + e.getIndexHeap());
        }
    }

    @SuppressWarnings("unchecked")
    private static Estudiante obtener(MinHeap<Estudiante> h, int i) {
        try {
            var f = h.getClass().getDeclaredField("heap");
            f.setAccessible(true);
            Object[] arr = (Object[]) f.get(h);
            return (Estudiante) arr[i];
        } catch (Exception ex) {
            return null;
        }
    }

    // ==========================================================
    // VALIDAR QUE LOS ÍNDICES COINCIDAN CON LAS POSICIONES
    // ==========================================================
    private static void validarIndices(MinHeap<Estudiante> heap) {
        boolean ok = true;

        for (int i = 0; i < heap.size(); i++) {
            Estudiante e = obtener(heap, i);
            if (e.getIndexHeap() != i) {
                ok = false;
                System.out.println(" ERROR: " + e + " tiene indexHeap=" + e.getIndexHeap() + " pero está en posición " + i);
            }
        }

        if (ok) {
            System.out.println("✔ Todos los índices internos coinciden correctamente.");
        }
    }
}
