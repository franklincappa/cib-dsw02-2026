import java.nio.file.*;

public class Main {
    public static void main(String[] args) {
        try {
            Path carpeta= Paths.get("data");
            //Path carpeta= Paths.get("F:\\CIBERTEC\\file\\demo");
            Files.createDirectories(carpeta);

            Path archivoTxt = carpeta.resolve("salida.txt");
            Path archivoCsv = carpeta.resolve("salida.csv");

            Files.deleteIfExists(archivoTxt);

            if (Files.notExists(archivoTxt)) Files.createFile(archivoTxt);
            if (Files.notExists(archivoCsv)) Files.createFile(archivoCsv);

            System.out.println("Carpeta: " + carpeta.toAbsolutePath());
            System.out.println("Txt paths: " + archivoTxt.toAbsolutePath());
            System.out.println("CSV Paths: " + archivoCsv.toAbsolutePath());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

