import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        try {
            //creación de carpeta
            Path carpeta= Paths.get("data");
            Files.createDirectories(carpeta);

            //crear lista de personas
            List<Persona> personas =  obtenerDatos();

            //establecer archivo salida
            //txt con tab
            escribirArchivo(personas, carpeta.resolve("personas_tab.txt"), "\t");
            //csv con coma
            escribirArchivo(personas, carpeta.resolve("personas_coma.csv"), ",");
            //txt con Pipe
            escribirArchivo(personas, carpeta.resolve("personas_pipe.txt"), "|");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static List<Persona> obtenerDatos(){
        //Obtener de la base de datos
        return List.of(
                new Persona(1, "71234567", "Ana", "Pérez", "Av. Lima 123"),
                new Persona(2, "74567890", "Luis", "García", "Jr. Sol 456"),
                new Persona(3, "70111222", "Carla", "Ramos", "Calle Norte 789")
        );
    }

    static void escribirArchivo(List<Persona> personas, Path archivo, String sep) throws IOException {
        try (BufferedWriter bw = Files.newBufferedWriter(archivo, StandardCharsets.UTF_8)) {
            bw.write("id" + sep + "dni" + sep + "nombres" + sep + "apellidos" + sep + "direccion");
            bw.newLine();

            for (Persona p : personas) {
                bw.write(p.getId() + sep + p.getDni() + sep + p.getNombres() + sep +
                        p.getApellidos() + sep + p.getDireccion());
                bw.newLine();
            }
        }
        System.out.println("Escrito: " + archivo.toAbsolutePath() + " (sep='" + sep + "')");
    }
}
