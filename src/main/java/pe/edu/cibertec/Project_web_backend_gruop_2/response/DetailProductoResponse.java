package pe.edu.cibertec.Project_web_backend_gruop_2.response;

import pe.edu.cibertec.Project_web_backend_gruop_2.dto.ProductoDetailDto;

public record DetailProductoResponse(String code,
                                     String error,
                                     ProductoDetailDto productoDetailDto) {
}
