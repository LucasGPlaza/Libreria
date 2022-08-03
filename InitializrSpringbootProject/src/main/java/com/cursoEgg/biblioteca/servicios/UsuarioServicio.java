
package com.cursoEgg.biblioteca.servicios;

import com.cursoEgg.biblioteca.entidades.Usuario;
import com.cursoEgg.biblioteca.enumeraciones.Rol;
import com.cursoEgg.biblioteca.excepciones.MiException;
import com.cursoEgg.biblioteca.repositorios.UsuarioRepositorio;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.stereotype.Service;

@Service
public class UsuarioServicio implements UserDetailsService{
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    
    @Transactional
    public void registrar (String nombre, String email, String password, String password2) throws MiException{
     
        validar (nombre, email,password, password2);
        
        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setEmail(email);
        usuario.setPassword(new BCryptPasswordEncoder().encode(password));
        usuario.setRol(Rol.USER);
        
        usuarioRepositorio.save(usuario);
    }
    
    public void validar(String nombre, String email, String password, String password2) throws MiException{
      if (nombre == null) {
        throw new MiException ("El nombre no puede estar vacio");
    }
       if (email == null || email.isEmpty()) {
        throw new MiException ("El email no puede estar vacío");
    }
       if (password == null ||  password.isEmpty()) {
        throw new MiException ("La contraseña no puede estar vacía");
    }
       if (!password.equals(password2)) {
        throw new MiException ("Las contraseñas deben ser iguales");
    }
      
}

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
       Usuario usuario = usuarioRepositorio.buscarPorEmail(email);
        if (usuario != null) {
            List<GrantedAuthority> permisos = new ArrayList();
            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_"+usuario.getRol().toString());
            permisos.add(p);
            
            return new User(usuario.getEmail(),usuario.getPassword(),permisos);
        }else{
        return null ;
    }
  
}
}

       
       
    
