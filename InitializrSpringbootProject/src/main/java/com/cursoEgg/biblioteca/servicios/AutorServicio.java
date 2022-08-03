package com.cursoEgg.biblioteca.servicios;

import com.cursoEgg.biblioteca.entidades.Autor;
import com.cursoEgg.biblioteca.excepciones.MiException;
import com.cursoEgg.biblioteca.repositorios.AutorRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AutorServicio {

    @Autowired
            
    private AutorRepositorio autorRepositorio;

    @Transactional
    public void crearAutor(String nombre) {
     
        Autor autor = new Autor();
        autor.setNombre(nombre);

        autorRepositorio.save(autor);
    }

    public List<Autor> listarAutores() {
        List<Autor> autores = new ArrayList();
        autores = autorRepositorio.findAll();
        return autores;
    }

    public void modificarAutor(String nombre, String id) throws MiException {
        
        validar(nombre);
        Optional<Autor> respuesta = autorRepositorio.findById(id);

        if (respuesta.isPresent()) {
            Autor autor = respuesta.get();

            autorRepositorio.save(autor);
        }
    }
    
    public Autor getOne(String id){
        return autorRepositorio.getOne(id);    }

    public void validar(String nombre) throws MiException {

        if (nombre == null || nombre.isEmpty()) {
            throw new MiException("El titulo no puede estar vacío");
        }
      

    }
}
