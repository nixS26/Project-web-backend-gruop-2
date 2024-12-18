package pe.edu.cibertec.Project_web_backend_gruop_2.dto;

import java.util.Date;

public record UsuarioInsertDTO(
                               String nombre ,
                               String email,
                               String password,
                               String telefono,
                               Date fechaRegistro,
                               String rol) {
}
