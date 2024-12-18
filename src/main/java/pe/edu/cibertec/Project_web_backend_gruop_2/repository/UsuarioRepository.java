package pe.edu.cibertec.Project_web_backend_gruop_2.repository;

import org.springframework.data.repository.CrudRepository;
import pe.edu.cibertec.Project_web_backend_gruop_2.entity.Usuario;

import java.util.Optional;

public interface UsuarioRepository  extends CrudRepository<Usuario, Integer> {
    Optional<Usuario> findByEmail(String email);

}
