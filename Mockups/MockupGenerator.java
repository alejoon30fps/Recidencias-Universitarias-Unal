package Mockup;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class MockupGenerator {

    static String[] nombres = {
        "Ana", "Juan", "Maria", "Luis", "Carlos", "Laura", "Sofia", "Miguel",
        "Pedro", "Diana", "Camila", "Jose", "Andrea", "Valentina", "Andres",
        "Felipe", "Daniel", "Lorena", "Mateo", "Samuel"
    };

    static String[] apellidos = {
        "Perez", "Gomez", "Sanchez", "Torres", "Rios", "Castro", "Ruiz",
        "Martinez", "Garcia", "Lopez", "Ramirez", "Vargas", "Cortes",
        "Hernandez", "Mendoza", "Gil", "Acosta", "Ortega"
    };

    public static void generarMockup(String nombreArchivo, int cantidad) {
        Random rand = new Random();

        try (FileWriter writer = new FileWriter(nombreArchivo)) {

            writer.write("id;nombre;pbm;correo\n");

            for (int i = 1; i <= cantidad; i++) {
                String nombre = nombres[rand.nextInt(nombres.length)];
                String apellido = apellidos[rand.nextInt(apellidos.length)];
                int pbm = rand.nextInt(100) + 1;

                String correo = (nombre + "." + apellido + i + "@mail.com")
                        .toLowerCase();

                writer.write(i + ";" + nombre + " " + apellido + ";" + pbm + ";" + correo + "\n");
            }

            System.out.println("Mockup generado: " + nombreArchivo);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        // Generar datasets para pruebas
        generarMockup("mockup_100.csv", 100);
        generarMockup("mockup_1000.csv", 1000);
        generarMockup("mockup_10000.csv", 10000);
        generarMockup("mockup_100000.csv", 100000);
        generarMockup("mockup_1000000.csv", 1000000);

    }
}
