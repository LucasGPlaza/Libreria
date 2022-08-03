package com.cursoEgg.biblioteca.controladores;

import com.cursoEgg.biblioteca.entidades.Autor;
import com.cursoEgg.biblioteca.entidades.Editorial;
import com.cursoEgg.biblioteca.entidades.Libro;
import com.cursoEgg.biblioteca.excepciones.MiException;
import com.cursoEgg.biblioteca.servicios.AutorServicio;
import com.cursoEgg.biblioteca.servicios.EditorialServicio;
import com.cursoEgg.biblioteca.servicios.LibroServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/libro")

public class LibroControlador {

    @Autowired
    private EditorialServicio editorialServicio;
    @Autowired
    private AutorServicio autorServicio;
    @Autowired
    private LibroServicio libroServicio;

    @GetMapping("/registrar")

    public String registrar(ModelMap modelo) {

        List<Autor> autores = autorServicio.listarAutores();
        List<Editorial> editoriales = editorialServicio.listarEditorial();

        modelo.addAttribute("autores", autores);
        modelo.addAttribute("editoriales", editoriales);

        return "libro_form.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam(required = false) Long isbn, @RequestParam String titulo, @RequestParam(required = false) Integer ejemplares,
            @RequestParam String idAutor, @RequestParam String idEditorial, ModelMap modelo) {
        try {
            libroServicio.crearLibro(isbn, titulo, ejemplares, idAutor, idEditorial);
            modelo.put("exito", "El libro fue cargado con exito!");

        } catch (MiException ex) {
            List<Autor> autores = autorServicio.listarAutores();
            List<Editorial> editoriales = editorialServicio.listarEditorial();

            modelo.addAttribute("autores", autores);
            modelo.addAttribute("editoriales", editoriales);
            modelo.put("err", ex.getMessage());
            return "libro_form.html";
        }
        return "index.html";

    }
    
        @GetMapping("/lista")
    public String listar(ModelMap modelo){
        
        List<Libro> libros = libroServicio.listarLibros();
          modelo.addAttribute("libros", libros);
          
        return "libro_lista.html";
}
}
