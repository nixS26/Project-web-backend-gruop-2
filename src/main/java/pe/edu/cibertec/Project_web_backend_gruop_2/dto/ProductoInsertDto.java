package pe.edu.cibertec.Project_web_backend_gruop_2.dto;

import org.springframework.web.multipart.MultipartFile;

public record ProductoInsertDto(Integer idProducto,
                                String nombre,
                                String descripcion,
                                Double precio,
                                Integer idCategoria,
                                MultipartFile imagen) {

}
