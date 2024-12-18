package pe.edu.cibertec.Project_web_backend_gruop_2.dto;

public record ProductoDetailDto(
        Integer idProducto,
        String nombre,
        String descripcion,
        Double precio,
        Integer idCategoria,
        String categoriaNombre) {
}