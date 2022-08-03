
package com.cursoEgg.biblioteca.servicios;

import com.cursoEgg.biblioteca.entidades.Autor;
import com.cursoEgg.biblioteca.entidades.Editorial;
import com.cursoEgg.biblioteca.entidades.Libro;
import com.cursoEgg.biblioteca.excepciones.MiException;
import com.cursoEgg.biblioteca.repositorios.AutorRepositorio;
import com.cursoEgg.biblioteca.repositorios.EditorialRepositorio;
import com.cursoEgg.biblioteca.repositorios.LibroRepositorio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LibroServicio {
    
    @Autowired
    private LibroRepositorio libroRepositorio;
    @Autowired
    private AutorRepositorio autorRepositorio;
    @Autowired
    private EditorialRepositorio editorialRepositorio;
@Transactional    
    public void crearLibro (Long isbn, String titulo, Integer ejemplares, String idAutor, String idEditorial) throws MiException{

     validar(isbn, titulo, idAutor, idEditorial, ejemplares);
        
        Autor autor = autorRepositorio.findById(idAutor).get();
        Editorial editorial = editorialRepositorio.findById(idEditorial).get();
        
        Libro libro = new Libro();
        
        libro.setIsbn(isbn);
        libro.setTitulo(titulo);
        libro.setEjemplares(ejemplares);
        libro.setAlta(new Date());
        
        libro.setAutor(autor);
        libro.setEditorial(editorial);
        
        libroRepositorio.save(libro);
    }
    
    public List<Libro> listarLibros(){
        List <Libro> libros = new ArrayList();
        libros = libroRepositorio.findAll();
        return libros;
    }
    
    public void modificarLibro (Long isbn, String titulo, String idAutor, String idEditorial, Integer ejemplares) throws MiException{
        
        validar(isbn, titulo, idAutor, idEditorial, ejemplares);
        
        Optional <Libro> respuesta = libroRepositorio.findById(isbn);
        Optional <Autor> respuestaAutor = autorRepositorio.findById(idAutor);
        Optional <Editorial> respuestaEditorial = editorialRepositorio.findById(idEditorial);
        
        Autor autor = new Autor();
        Editorial editorial = new Editorial();
        
        if (respuestaAutor.isPresent()){
            autor = respuestaAutor.get();
        }
        
        if (respuestaEditorial.isPresent()){
            editorial = respuestaEditorial.get();
        }
        
        if(respuesta.isPresent()){
            Libro libro = respuesta.get();
            libro.setTitulo(titulo);
            libro.setEjemplares(ejemplares);
            libro.setAutor(autor);
            libro.setEditorial(editorial);
            
            libroRepositorio.save(libro);
        }
    }

public void validar(Long isbn, String titulo, String idAutor, String idEditorial, Integer ejemplares) throws MiException{
      if (isbn == null) {
        throw new MiException ("El isbn no puede ser nulo");
    }
       if (titulo == null || titulo.isEmpty()) {
        throw new MiException ("El titulo no puede estar vacío");
    }
       if (ejemplares == null) {
        throw new MiException ("Los ejemplares no pueden ser nulos");
    }
       if (idAutor == null || idAutor.isEmpty()) {
        throw new MiException ("El autor no puede ser nulo ni estar vacío");
    }
       if (idEditorial == null || idEditorial.isEmpty()) {
        throw new MiException ("Editorial no puede ser nulo ni estar vacío");
    }
 
}
}
