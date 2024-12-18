package pe.edu.cibertec.Project_web_backend_gruop_2.dto;

public record ProductoDto(Integer idProducto,
                          String nombre,
                          String descripcion,
                          Double precio,
                          String categoria,
                          String imagen) {
}
