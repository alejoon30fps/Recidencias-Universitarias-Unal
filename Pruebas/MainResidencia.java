package Pruebas;

import Modulos.Residencia;

public class MainResidencia {
    public static void main(String[] args) {

        System.out.println("========= TEST DE RESIDENCIA =========\n");

        Residencia r = new Residencia();

        System.out.println(">>> Registrando estudiantes...\n");

        r.registrarEstudiante("Juan", 80, "juan@mail.com");   // ID autogenerado
        r.registrarEstudiante("Maria", 30, "maria@mail.com");
        r.registrarEstudiante("Luis", 60, "luis@mail.com");
        r.registrarEstudiante("Ana", 20, "ana@mail.com");

        // Debemos obtener los ID reales creados,
        // pero para este test asumimos que el constructor Estudiante asigna IDs consecutivos:  
        long idJuan = 1;
        long idMaria = 2;
        long idLuis = 3;
        long idAna = 4;

        // === CONSULTAR ===
        System.out.println(">>> Consultar estudiante por ID 3 (Luis):");
        System.out.println(r.queryEstudianteId(idLuis));
        System.out.println();

        // === CHANGE VALUE ===
        System.out.println(">>> Cambiando PBM de Luis (ID 3) a 10 (debe moverlo a mayor prioridad):\n");
        r.changeValue(10, idLuis);

        // === LISTA IN ORDER ===
        System.out.println(">>> Listado de estudiantes por prioridad (InOrder del AVL):");
        System.out.println(r.listarEstudiantesPorPrioridad());
        System.out.println();

        // === INGRESAR CUPOS ===
        System.out.println(">>> Ingresando cupos a 2...");
        r.setCupos(2);
        System.out.println();

        // === ASIGNAR CUPOS ===
        System.out.println(">>> Asignando cupos (los 2 con PBM más bajo serán aceptados)...\n");
        r.asigCupos();

        // === LISTA ENTERA ===
        System.out.println(">>> Comparando listas de aceptados y NO aceptados:");
        System.out.println(r.listaEntera());
        System.out.println();

        // === ESTADO FINAL ===
        System.out.println(">>> Verificando estado final de un estudiante:");
        System.out.println("¿ID " + idLuis + " fue aceptado?: " + r.estadoFinal(idLuis));
        System.out.println("¿ID " + idMaria + " fue aceptado?: " + r.estadoFinal(idMaria));
        System.out.println();

        // === ELIMINAR ===
        System.out.println(">>> Eliminando estudiante ID 3 (Luis)...");
        boolean eliminado = r.eliminarEstudiante(idLuis);
        System.out.println("¿Eliminado correctamente?: " + eliminado);
        System.out.println();
        
        // === LISTA IN ORDER ===
        System.out.println(">>> Listado de estudiantes por prioridad (InOrder del AVL):");
        System.out.println(r.listarEstudiantesPorPrioridad());
        System.out.println();

        // === LISTA FINAL ===
        System.out.println(">>> Listado final después de eliminar ID 3:");
        System.out.println(r.listaEntera());
        System.out.println();

        

        // === ASIGNAR CUPOS ===
        System.out.println(">>> Asignando cupos (los 2 con PBM más bajo serán aceptados)...\n");
        r.asigCupos();
        System.out.println();

        // === LISTA FINAL ===
        System.out.println(">>> Listado final después de eliminar ID 3:");
        System.out.println(r.listaEntera());
    }
}
