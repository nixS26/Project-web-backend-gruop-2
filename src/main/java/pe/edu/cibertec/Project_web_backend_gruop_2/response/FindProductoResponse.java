package pe.edu.cibertec.Project_web_backend_gruop_2.response;

import pe.edu.cibertec.Project_web_backend_gruop_2.dto.ProductoDto;

public record FindProductoResponse(String code,
                                   String error,
                                   Iterable<ProductoDto> productos) {
}
