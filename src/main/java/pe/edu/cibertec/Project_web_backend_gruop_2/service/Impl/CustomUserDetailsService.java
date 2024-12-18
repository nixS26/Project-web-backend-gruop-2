package pe.edu.cibertec.Project_web_backend_gruop_2.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pe.edu.cibertec.Project_web_backend_gruop_2.entity.Usuario;
import pe.edu.cibertec.Project_web_backend_gruop_2.repository.UsuarioRepository;

import java.util.Optional;

@Service
@Primary // Indica que este es el servicio principal de UserDetailsService
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Usuario> optional = usuarioRepository.findByEmail(email);

        if (optional.isEmpty()) {
            throw new UsernameNotFoundException("Correo no encontrado");
        }

        Usuario usuario = optional.get();

        return org.springframework.security.core.userdetails.User.builder()
                .username(usuario.getEmail())
                .password(usuario.getPassword()) // Contraseña encriptada
                .roles(usuario.getRol()) // Asignar roles
                .build();
    }
}

