package com.demo.ecommerce.web.controller;

import com.demo.ecommerce.domain.entity.Categoria;
import com.demo.ecommerce.repository.CategoriaRepository;
import com.demo.ecommerce.service.CategoriaService;
import com.demo.ecommerce.web.dto.CategoriaRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/public/categorias")
@Tag(name="Categoria", description = "Operaciones CRUD para gestión de categorias")
public class CategoriaController {

    private final CategoriaRepository _categoriaRepository;
    private final CategoriaService _categoriaService;

    public CategoriaController(CategoriaRepository categoriaRepository,CategoriaService categoriaService ){
        this._categoriaRepository= categoriaRepository;
        this._categoriaService= categoriaService;
    }

    @Operation(summary = "Listar Categorias", description = "Retorna todas las categorias registradas")
    @GetMapping
    public List<Categoria> listar(){
        return _categoriaService.listar();
    }

    @Operation(summary = "Obtener Categoria", description = "Busca una categoria por su ID")
    @GetMapping("/{id}")
    public Categoria obtener (@PathVariable Long id){
        return _categoriaService.obtener(id);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar Categoria", description = "Elimina una categoria por su ID")
    @ResponseStatus(HttpStatus.NO_CONTENT)  // 204
    public void eliminar(@PathVariable Long id){
        _categoriaService.eliminar(id);
    }

    @Operation(
            summary = "Crear Categoria",
            description = """
            Registra una nueva categoría en el sistema.
            
            ### Reglas de negocio
            - El nombre no puede duplicarse (case insensitive)
            - El nombre se almacena sin espacios al inicio/fin
            
            ### Parámetros del body
            | Campo       | Tipo   | Requerido | Tamaño | Descripción              |
            |-------------|--------|-----------|--------|--------------------------|
            | nombre      | String | Sí        | 1-100  | Nombre único de categoría|
            | descripcion | String | No        | 0-250  | Descripción opcional     |
            """
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)  // 201
    public Categoria crear(@Valid @RequestBody CategoriaRequest categoria){
        return _categoriaService.crear(categoria);
    }

    @Operation(
            summary = "Actualizar Categoria",
            description = """
        Modifica los datos de una categoría existente
        
        <h3>Reglas de negocio</h3>
        <ul>
            <li>El nombre no puede duplicarse</li>
            <li>Se eliminan espacios al inicio/fin</li>
        </ul>
        
        <h3>Parámetros del body</h3>
        <table border="1" cellpadding="6" cellspacing="0">
            <thead style="background-color:#f0f0f0">
                <tr>
                    <th>Campo</th>
                    <th>Tipo</th>
                    <th>Requerido</th>
                    <th>Tamaño</th>
                    <th>Descripción</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td><b>nombre</b></td>
                    <td>String</td>
                    <td style="color:green"> Sí</td>
                    <td>1 - 100</td>
                    <td>Nombre único de categoría</td>
                </tr>
                <tr>
                    <td><b>descripcion</b></td>
                    <td>String</td>
                    <td style="color:gray"> No</td>
                    <td>0 - 250</td>
                    <td>Descripción opcional</td>
                </tr>
            </tbody>
        </table>
        
        <br/>
        <blockquote style="color:#856404; background:#fff3cd; padding:8px; border-left: 4px solid #ffc107">
            ⚠️ <b>Nota:</b> El nombre se guarda sin importar mayúsculas/minúsculas.
        </blockq
        """
    )
    @PutMapping("/{id}")
    public Categoria actualizar(@PathVariable Long id, @Valid  @RequestBody CategoriaRequest categoriaRequest){
        return _categoriaService.actualizar(id, categoriaRequest);
    }
}


