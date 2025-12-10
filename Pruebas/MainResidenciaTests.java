package Pruebas;

import Estructuras.Estudiante;
import Modulos.Residencia;

public class MainResidenciaTests {

    public static void main(String[] args) {

        System.out.println("========= TESTS RESIDENCIA =========\n");

        test1_registroConsulta();
        test2_asignacionCupos();
        test3_cambioPBM();
        test4_eliminarYReasignar();
        test5_pbmRepetidos();
        test6_unSoloCupo();
        test7_cuposIgualN();
        test8_stress1000();
        test9_eliminacionesAleatorias();
    }

    // =======================
    //   ✔ TEST 1 – Registro + Consulta
    // =======================
    private static void test1_registroConsulta() {
        Estudiante.resetIDCounter();   // 👈 RESTART IDS

        try {
            Residencia r = new Residencia();

            r.registrarEstudiante("Juan", 80, "j@mail");
            r.registrarEstudiante("Ana", 20, "a@mail");

            boolean cond =
                    r.queryEstudianteId(1).contains("Juan") &&
                    r.queryEstudianteId(2).contains("Ana");

            System.out.println(cond ? "✓ PASSED TEST 1 – Registro + Consulta\n"
                                    : "✗ FAILED TEST 1\n");

        } catch (Exception e) {
            System.out.println("✗ FAILED TEST 1\n");
        }
    }

    // =======================
    //   ✔ TEST 2 – Asignación de cupos
    // =======================
    private static void test2_asignacionCupos() {
        Estudiante.resetIDCounter();

        try {
            Residencia r = new Residencia();

            r.registrarEstudiante("J", 50, "a");
            r.registrarEstudiante("A", 10, "b");
            r.registrarEstudiante("B", 30, "c");

            r.setCupos(2);
            r.asigCupos();

            boolean cond = r.estadoFinal(2) && r.estadoFinal(3); // PBM 10 y 30

            System.out.println(cond ? "✓ PASSED TEST 2 – Asignación correcta de cupos\n"
                                    : "✗ FAILED TEST 2\n");

        } catch (Exception e) {
            System.out.println("✗ FAILED TEST 2\n");
        }
    }

    // =======================
    //   ✔ TEST 3 – Cambio de PBM
    // =======================
    private static void test3_cambioPBM() {
        Estudiante.resetIDCounter();

        try {
            Residencia r = new Residencia();

            r.registrarEstudiante("A", 80, "a");
            r.registrarEstudiante("B", 70, "b");
            r.registrarEstudiante("C", 10, "c");

            r.changeValue(1, 1); // "A" ahora PBM=1
            r.setCupos(1);
            r.asigCupos();

            boolean cond = r.estadoFinal(1);

            System.out.println(cond ? "✓ PASSED TEST 3 – Cambio de PBM\n"
                                    : "✗ FAILED TEST 3\n");

        } catch (Exception e) {
            System.out.println("✗ FAILED TEST 3\n");
        }
    }

    // =======================
    //   ✔ TEST 4 – Eliminar aceptado y reasignar
    // =======================
    private static void test4_eliminarYReasignar() {
        Estudiante.resetIDCounter();

        try {
            Residencia r = new Residencia();

            r.registrarEstudiante("A", 5, "a");
            r.registrarEstudiante("B", 10, "b");
            r.registrarEstudiante("C", 20, "c");

            r.setCupos(2);
            r.asigCupos();

            r.eliminarEstudiante(1);
            r.asigCupos();  // Reasigna correctamente

            boolean cond = r.estadoFinal(3); // C debería entrar ahora

            System.out.println(cond ? "✓ PASSED TEST 4 – Eliminar y reasignar\n"
                                    : "✗ FAILED TEST 4\n");

        } catch (Exception e) {
            System.out.println("✗ FAILED TEST 4\n");
        }
    }

    // =======================
    //   ✔ TEST 5 – PBM repetidos
    // =======================
    private static void test5_pbmRepetidos() {
        Estudiante.resetIDCounter();

        try {
            Residencia r = new Residencia();

            r.registrarEstudiante("A", 10, "a");
            r.registrarEstudiante("B", 10, "b");
            r.registrarEstudiante("C", 10, "c");

            r.setCupos(2);
            r.asigCupos();

            boolean cond =
                    r.estadoFinal(1) &&
                    r.estadoFinal(2) &&
                    !r.estadoFinal(3);

            System.out.println(cond ? "✓ PASSED TEST 5 – PBM repetidos\n"
                                    : "✗ FAILED TEST 5\n");

        } catch (Exception e) {
            System.out.println("✗ FAILED TEST 5\n");
        }
    }

    // =======================
    //   ✔ TEST 6 – Un solo cupo
    // =======================
    private static void test6_unSoloCupo() {
        Estudiante.resetIDCounter();

        try {
            Residencia r = new Residencia();

            r.registrarEstudiante("A", 50, "a");
            r.registrarEstudiante("B", 5, "b");

            r.setCupos(1);
            r.asigCupos();

            boolean cond = r.estadoFinal(2); // PBM más bajo

            System.out.println(cond ? "✓ PASSED TEST 6 – Un solo cupo\n"
                                    : "✗ FAILED TEST 6\n");

        } catch (Exception e) {
            System.out.println("✗ FAILED TEST 6\n");
        }
    }

    // =======================
    //   ✔ TEST 7 – Cupos igual N
    // =======================
    private static void test7_cuposIgualN() {
        Estudiante.resetIDCounter();

        try {
            Residencia r = new Residencia();
            r.registrarEstudiante("A", 20, "a");
            r.registrarEstudiante("B", 40, "b");
            r.registrarEstudiante("C", 10, "c");
            r.registrarEstudiante("D", 50, "d");

            r.setCupos(4);
            r.asigCupos();

            boolean all =
                    r.estadoFinal(1) &&
                    r.estadoFinal(2) &&
                    r.estadoFinal(3) &&
                    r.estadoFinal(4);

            System.out.println(all ? "✓ PASSED TEST 7 – Cupos = N\n"
                                   : "✗ FAILED TEST 7\n");

        } catch (Exception e) {
            System.out.println("✗ FAILED TEST 7\n");
        }
    }

    // =======================
    //   ✔ TEST 8 – Stress 1000 estudiantes
    // =======================
    private static void test8_stress1000() {
        Estudiante.resetIDCounter();

        try {
            Residencia r = new Residencia();
            r.setCupos(1000);

            for (int i = 0; i < 1000; i++)
                r.registrarEstudiante("X" + i, (int)(Math.random() * 1000), "m");

            r.asigCupos();

            boolean ok = true;
            for (int i = 1; i <= 1000; i++)
                if (!r.estadoFinal(i)) { ok = false; break; }

            System.out.println(ok ? "✓ PASSED TEST 8 – Stress 1000 estudiantes\n"
                                  : "✗ FAILED TEST 8\n");

        } catch (Exception e) {
            System.out.println("✗ FAILED TEST 8\n");
        }
    }

    // =======================
    //   ✔ TEST 9 – Eliminaciones aleatorias
    // =======================
    private static void test9_eliminacionesAleatorias() {
        Estudiante.resetIDCounter();

        try {
            Residencia r = new Residencia();
            r.setCupos(500);

            for (int i = 0; i < 500; i++)
                r.registrarEstudiante("X" + i, (int)(Math.random() * 300), "m");

            r.asigCupos();

            for (int k = 0; k < 200; k++) {
                long id = 1 + (long)(Math.random() * 500);
                r.eliminarEstudiante(id);
            }

            System.out.println("✓ PASSED TEST 9 – Eliminaciones aleatorias\n");

        } catch (Exception e) {
            System.out.println("✗ FAILED TEST 9\n");
        }
    }

}
