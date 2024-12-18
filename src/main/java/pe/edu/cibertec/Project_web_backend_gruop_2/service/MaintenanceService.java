package pe.edu.cibertec.Project_web_backend_gruop_2.service;

import pe.edu.cibertec.Project_web_backend_gruop_2.dto.*;
import pe.edu.cibertec.Project_web_backend_gruop_2.entity.Usuario;

import java.util.List;
import java.util.Optional;

public interface MaintenanceService {
    //------------------FIRMA DE METODOS PARA PRODUCTO-----------------
    List<ProductoDto> findAllProductos() throws Exception;
    Optional<ProductoDto> findAllProductoById(int id) throws Exception;
    Boolean addProducto(ProductoInsertDto productoInsertDtoDto) throws Exception ;
    Optional<ProductoDetailDto> findProductoById(int id)throws Exception;
    boolean deleteProducto(int id) throws Exception;
    Boolean updateProducto(ProductoDetailDto productoDetailDto) throws Exception;

    //-----------------FIRMA DE METODOS PARA CATEGORIA----------------
    List<CategoriaDto> findAllCategorias();

    //-----------------

    void registerUser(UsuarioInsertDTO usuarioInsertDTO);

}
