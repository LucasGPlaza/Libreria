package com.cursoEgg.biblioteca.servicios;

import com.cursoEgg.biblioteca.entidades.Editorial;
import com.cursoEgg.biblioteca.excepciones.MiException;
import com.cursoEgg.biblioteca.repositorios.EditorialRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EditorialServicio {

    @Autowired
    private EditorialRepositorio editorialRepositorio;

    @Transactional
    public void crearEditorial(String nombre) {
        Editorial editorial = new Editorial();

        editorial.setNombre(nombre);

        editorialRepositorio.save(editorial);
    }

    public List<Editorial> listarEditorial() {
        List<Editorial> editoriales = new ArrayList();
        editoriales = editorialRepositorio.findAll();
        return editoriales;
    }

    public void modificarEditorial(String nombre, String id) {
        Optional<Editorial> respuesta = editorialRepositorio.findById(id);

        if (respuesta.isPresent()) {
            Editorial editorial = respuesta.get();

            editorialRepositorio.save(editorial);
        }
    }

    public void validar(String nombre, String id) throws MiException {

        if (nombre == null || nombre.isEmpty()) {
            throw new MiException("El titulo no puede estar vacío");
        }
        if (id == null || id.isEmpty()) {
            throw new MiException("El autor no puede ser nulo ni estar vacío");
        }

    }
}
