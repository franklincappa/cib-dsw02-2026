
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.io.*;

public class Main {
    public static void main(String[] args) {
        try {
            Path carpeta = Paths.get("data");

            //leerEImprimir(carpeta.resolve("personas_tab.txt"), "\t");
            leerEImprimir(carpeta.resolve("personas_pipe.txt"), "|");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void leerEImprimir(Path archivo, String sep) throws IOException {
        System.out.println("\n=== Leyendo: " + archivo.getFileName() + " (sep='" + sep + "') ===");

        String regexSep = sep.equals("|") ? "\\|" : sep;

        try (BufferedReader br = Files.newBufferedReader(archivo, StandardCharsets.UTF_8)) {
            String linea;
            boolean primera = true;

            while ((linea = br.readLine()) != null) {
                if (primera) { // saltar encabezado
                    primera = false;
                    continue;
                }

                String[] c = linea.split(regexSep);

                if (c.length < 5) {
                    System.out.println("Línea inválida: " + linea);
                    continue;
                }

                System.out.println(
                        "id=" + c[0] + ", dni=" + c[1] + ", nombres=" + c[2] +
                                ", apellidos=" + c[3] + ", direccion=" + c[4]
                );
            }
        }
    }
}
