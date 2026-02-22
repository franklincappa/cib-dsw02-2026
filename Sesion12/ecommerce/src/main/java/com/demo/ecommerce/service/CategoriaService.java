package com.demo.ecommerce.service;

import com.demo.ecommerce.domain.entity.Categoria;
import com.demo.ecommerce.repository.CategoriaRepository;
import com.demo.ecommerce.web.dto.CategoriaRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoriaService {
    private final CategoriaRepository _categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository){
        this._categoriaRepository=categoriaRepository;
    }

    public Categoria obtener(Long id){
        return _categoriaRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Registro no encontrado"));
    }

    public List<Categoria> listar() {
        return _categoriaRepository.findAll();
    }

    @Transactional
    public Categoria crear(CategoriaRequest categoriaRequest){
        if (_categoriaRepository.existsByNombreIgnoreCase(categoriaRequest.getNombre())){
            throw  new IllegalArgumentException("Categoria Duplicada");
        }
        Categoria categoria = new Categoria();
        categoria.setNombre(categoriaRequest.getNombre().trim());
        categoria.setDescripcion(categoriaRequest.getDescripcion().trim());
        return _categoriaRepository.save(categoria);

    }

    @Transactional
    public Categoria actualizar(Long id, CategoriaRequest categoriaRequest){
        Categoria categoria = obtener(id);
        categoria.setNombre(categoriaRequest.getNombre().trim());
        categoria.setDescripcion(categoriaRequest.getDescripcion().trim());
        return _categoriaRepository.save(categoria);
    }

    @Transactional
    public void eliminar(Long id){
        _categoriaRepository.delete(obtener(id));
    }


}
