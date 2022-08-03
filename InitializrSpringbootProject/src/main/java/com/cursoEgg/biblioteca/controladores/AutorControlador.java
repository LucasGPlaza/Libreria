package com.cursoEgg.biblioteca.controladores;

import com.cursoEgg.biblioteca.entidades.Autor;
import com.cursoEgg.biblioteca.excepciones.MiException;
import com.cursoEgg.biblioteca.servicios.AutorServicio;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/autor")

public class AutorControlador {

    @Autowired
    private AutorServicio autorServicio;

    @GetMapping("/registrar")

    public String registrar() {
        return "autor_form.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, ModelMap modelo) {
        autorServicio.crearAutor(nombre);
        modelo.put("exito", "El autor fue ingresado correctamente!");
        return "index.html";
        
      
    }
    
    @GetMapping("/lista")
    public String listar(ModelMap modelo){
        List<Autor> autores = autorServicio.listarAutores();
          modelo.addAttribute("autores", autores);
          
        return "autor_lista.html";
    }
    
    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, ModelMap modelo){
        modelo.put("autor", autorServicio.getOne(id));
        return "autor_modificar.html";
    }
    
    @PostMapping("/modificar/{id}")
    public String modificar(@PathVariable String id,String nombre, ModelMap modelo) {
        try {
            autorServicio.modificarAutor(nombre,id);
            return "redirect:../lista";
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            return "autor_modificar.html";
        }
        
    }
}