package com.demo.uploadfile;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
        import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UploadController {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Operation(
            summary = "Subir archivo JSON de personas",
            description = "Recibe un archivo JSON (array de personas), lo deserializa a List<Persona> y recorre imprimiendo en consola."
    )
    @PostMapping(value = "/upload-personas-json", consumes = "multipart/form-data")
    public ResponseEntity<String> uploadPersonasJson(
            @Parameter(description = "Archivo .json con un array de personas")
            @RequestParam("file") MultipartFile file
    ) {
        try {
            if (file.isEmpty()) return ResponseEntity.badRequest().body("El archivo viene vacío.");

            List<Persona> personas = objectMapper.readValue(
                    file.getBytes(),
                    new TypeReference<List<Persona>>() {}
            );

            System.out.println("=== Personas recibidas (" + personas.size() + ") ===");
            for (Persona p : personas) {
                System.out.println(p.getId() + " | " + p.getDni() + " | " +
                        p.getNombres() + " " + p.getApellidos() + " | " + p.getDireccion());
            }

            return ResponseEntity.ok("OK. Registros procesados: " + personas.size());

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error procesando JSON: " + e.getMessage());
        }
    }

    @Operation(
            summary = "Subir TXT/CSV de personas (formato Demo 02) y procesarlo",
            description = "Recibe un archivo de texto con encabezado: id,dni,nombres,apellidos,direccion (según separador). " +
                    "Parsea líneas, crea Persona y recorre imprimiendo en consola. Soporta separador TAB, coma o pipe."
    )
    @PostMapping(value = "/upload-personas-texto", consumes = "multipart/form-data")
    public ResponseEntity<String> uploadPersonasTexto(
            @Parameter(description = "Archivo .txt o .csv con encabezado y registros")
            @RequestParam("file") MultipartFile file,
            @Parameter(description = "Separador: use \\t para TAB, , para coma, | para pipe. Ej: ,")
            @RequestParam(defaultValue = ",") String separador
    ) {
        try {
            if (file.isEmpty()) return ResponseEntity.badRequest().body("El archivo viene vacío.");

            // Permitir que en Swagger manden "\t" como texto y lo convertimos a TAB real
            String sep = "\\t".equals(separador) ? "\t" : separador;

            String contenido = new String(file.getBytes(), StandardCharsets.UTF_8);
            String[] lineas = contenido.split("\\R"); // soporta \n y \r\n

            if (lineas.length <= 1) {
                return ResponseEntity.badRequest().body("El archivo no tiene datos (solo encabezado o vacío).");
            }

            // Regex del separador para split()
            String regexSep = sep.equals("|") ? "\\|" : sep;

            List<Persona> personas = new ArrayList<>();

            for (int i = 1; i < lineas.length; i++) { // saltamos encabezado
                String linea = lineas[i].trim();
                if (linea.isEmpty()) continue;

                String[] c = linea.split(regexSep, -1); // -1 conserva vacíos
                if (c.length < 5) {
                    System.out.println("Línea inválida (faltan columnas): " + linea);
                    continue;
                }

                Persona p = new Persona();
                p.setId(parseIntSafe(c[0]));
                p.setDni(c[1]);
                p.setNombres(c[2]);
                p.setApellidos(c[3]);
                p.setDireccion(c[4]);

                personas.add(p);
            }

            System.out.println("=== Personas desde TXT/CSV (" + personas.size() + ") sep='" + (sep.equals("\t") ? "\\t" : sep) + "' ===");
            for (Persona p : personas) {
                System.out.println(p.getId() + " | " + p.getDni() + " | " +
                        p.getNombres() + " " + p.getApellidos() + " | " + p.getDireccion());
            }

            return ResponseEntity.ok("OK. Registros leídos: " + personas.size());

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error procesando TXT/CSV: " + e.getMessage());
        }
    }

    private int parseIntSafe(String value) {
        try { return Integer.parseInt(value.trim()); }
        catch (Exception e) { return 0; }
    }
}
