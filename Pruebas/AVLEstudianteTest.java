package Pruebas;

import Estructuras.AVLEstudiante;
import Estructuras.Estudiante;

public class AVLEstudianteTest {

    public static void main(String[] args) {

        AVLEstudiante avl = new AVLEstudiante();

        // =============================
        //  PRUEBA 1: Inserciones básicas
        // =============================
        System.out.println("===== PRUEBA 1: Inserciones =====");
        Estudiante e1 = new Estudiante("Ana", 10, "ana@uni.edu");
        Estudiante e2 = new Estudiante("Luis", 20, "luis@uni.edu");
        Estudiante e3 = new Estudiante("Carlos", 5, "car@uni.edu");
        Estudiante e4 = new Estudiante("Daniela", 15, "dan@uni.edu");
        Estudiante e5 = new Estudiante("Elena", 30, "elena@uni.edu");

        avl.insertar(e1);
        avl.insertar(e2);
        avl.insertar(e3);
        avl.insertar(e4);
        avl.insertar(e5);

        System.out.print("InOrder esperado (5,10,15,20,30): ");
        avl.printInOrder();


        // =================================================
        //  PRUEBA 2: Búsqueda
        // =================================================
        System.out.println("\n===== PRUEBA 2: Búsqueda =====");
        Estudiante buscado = avl.buscar(e3);
        System.out.println("Encontrado: " + (buscado != null ? buscado.getNombre() : "NO encontrado"));


        // =================================================
        //  PRUEBA 3: Eliminación (nodo hoja)
        // =================================================
        System.out.println("\n===== PRUEBA 3: Remove Hoja (30) =====");
        avl.remove(e5);
        avl.printInOrder();


        // =================================================
        //  PRUEBA 4: Eliminación (nodo con 1 hijo)
        // =================================================
        System.out.println("\n===== PRUEBA 4: Remove con 1 hijo (20) =====");
        avl.remove(e2);
        avl.printInOrder();


        // =================================================
        //  PRUEBA 5: Eliminación (nodo con 2 hijos)
        // =================================================
        System.out.println("\n===== PRUEBA 5: Remove con 2 hijos (10) =====");
        avl.remove(e1);
        avl.printInOrder();


        // =================================================
        //  PRUEBA 6: changeKey
        // =================================================
        System.out.println("\n===== PRUEBA 6: Cambio de clave (PBM) =====");
        
        int nuevoPBM = 18;
        avl.changeKey(e3, nuevoPBM); // Cambiamos PBM de Carlos de 5 a 18

        avl.printInOrder();
    }
}
