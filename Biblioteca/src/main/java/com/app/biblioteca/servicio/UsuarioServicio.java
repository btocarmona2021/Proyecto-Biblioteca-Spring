package com.app.biblioteca.servicio;

import com.app.biblioteca.entidades.Imagen;
import com.app.biblioteca.entidades.Usuario;
import com.app.biblioteca.enumeraciones.Rol;
import com.app.biblioteca.excepciones.MisExcepciones;
import com.app.biblioteca.repositorios.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServicio implements UserDetailsService {
    @Autowired
    private UsuarioRepositorio usuarioRep;
    @Autowired
    private ImagenServicio imagenServ;

    @Transactional
    public void crearUsuario(MultipartFile archivo, String nombre, String correo, String password, String verificacion) throws MisExcepciones {
        validarDatos(nombre, correo, password, verificacion);
        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setCorreo(correo);
        usuario.setPassword(new BCryptPasswordEncoder().encode(password));
        Imagen imagen = imagenServ.guardarImagen(archivo);
        usuario.setImagen(imagen);
        usuario.setRol(Rol.USER);
        usuarioRep.save(usuario);
    }

    @Transactional
    public void modificarUsuario(String id, String nombre, String correo, MultipartFile archivo) throws MisExcepciones {
        if (nombre.isEmpty() || nombre == null) {
            throw new MisExcepciones("El nombre del usuario no puede estar vacio o nulo");
        }
        if (correo.isEmpty() || correo == null) {
            throw new MisExcepciones("El correo no puede estar vacio o nulo");
        }
        Optional<Usuario> respuesta = usuarioRep.findById(id);
        Usuario usuario = new Usuario();
        if (respuesta.isPresent()) {
            usuario = respuesta.get();
        }
        usuario.setNombre(nombre);
        usuario.setCorreo(correo);
        Imagen imagen = new Imagen();
        if (usuario.getImagen() != null) {
            if (!archivo.isEmpty()){
                imagen = imagenServ.actualizarImagen(usuario.getImagen().getId(), archivo);
                usuario.setImagen(imagen);
            }
        }else{
            usuario.setImagen(imagenServ.guardarImagen(archivo));
        }
        usuarioRep.save(usuario);

    }

    public void validarDatos(String nombre, String correo, String password, String verificacion) throws MisExcepciones {
        if (nombre.isEmpty() || nombre == null) {
            throw new MisExcepciones("El nombre del usuario no puede estar vacio o nulo");
        }
        if (correo.isEmpty() || correo == null) {
            throw new MisExcepciones("El correo no puede estar vacio o nulo");
        }
        if (password.length() < 5) {
            throw new MisExcepciones("El password debe contener 5 o más caracteres");
        }
        if (!password.equals(verificacion)) {
            throw new MisExcepciones("Debe indicar la misma clave dos veces");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        Usuario usuario = usuarioRep.buscarxCorreo(correo);
        if (usuario != null) {
            List<GrantedAuthority> permisos = new ArrayList<>();
            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + usuario.getRol().toString());

            permisos.add(p);
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession session = attr.getRequest().getSession(true);
            session.setAttribute("usuariosession", usuario);

            return new User(usuario.getCorreo(), usuario.getPassword(), permisos);
        } else {
            return null;
        }

    }

    public Usuario obtenerUno(String id) {
        Usuario usuario = usuarioRep.getOne(id);
        return usuario;
    }
}
