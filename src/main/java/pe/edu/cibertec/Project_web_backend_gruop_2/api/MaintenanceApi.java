package pe.edu.cibertec.Project_web_backend_gruop_2.api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pe.edu.cibertec.Project_web_backend_gruop_2.dto.ProductoDetailDto;
import pe.edu.cibertec.Project_web_backend_gruop_2.dto.ProductoDto;
import pe.edu.cibertec.Project_web_backend_gruop_2.dto.ProductoInsertDto;
import pe.edu.cibertec.Project_web_backend_gruop_2.repository.ProductoRepository;
import pe.edu.cibertec.Project_web_backend_gruop_2.response.*;
import pe.edu.cibertec.Project_web_backend_gruop_2.service.MaintenanceService;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/maintenance-pro")
public class MaintenanceApi {

    //Inyección de dependencia
    @Autowired
    MaintenanceService maintenanceService;
    @Autowired
    private ProductoRepository productoRepository;

    //------------------------API - MANEJO DE EXCEPCIONES---------------------------

    //----------------------------- ALL -------------------------------------
    @GetMapping("/all")
    public FindProductoResponse findProductos(@RequestParam(value = "id", defaultValue = "0") String id){
        try{
            if(Integer.parseInt(id)>0){
                Optional<ProductoDto> optional = maintenanceService.findAllProductoById(Integer.parseInt(id));
                if(optional.isPresent()){
                    ProductoDto productoDto = optional.get();
                    return new FindProductoResponse("01","Success", List.of(productoDto));

                }else {
                    return new FindProductoResponse("02","Producto no encontrado",null);
                }
            }else {
                List<ProductoDto> productos = maintenanceService.findAllProductos();
                if(!productos.isEmpty())
                    return new FindProductoResponse("03","Success",productos);
                else
                    return new FindProductoResponse("04","Lista de productos no encontrada",productos);
            }
        }catch (Exception e){
            e.printStackTrace();
            return new FindProductoResponse("99","Service no encontrado",null);
        }
    }

    //----------------------------- DETAIL ----------------------------------
    @GetMapping("/detail")
    public DetailProductoResponse detailProductoResponse(@RequestParam(value = "idProducto", defaultValue = "0") String id) {

        try {
            if (Integer.parseInt(id) > 0) {
                Optional<ProductoDetailDto> optional = maintenanceService.findProductoById(Integer.parseInt(id));
                if (optional.isPresent()) {
                    ProductoDetailDto productoDetailDto = optional.get();
                    return new DetailProductoResponse("01", "Exito", productoDetailDto);
                } else {
                    return new DetailProductoResponse("02", "Producto no encontrado", null);
                }

            } else
                return new DetailProductoResponse("03", "Parameter no encontrado", null);

        } catch (Exception e) {
            e.printStackTrace();
            return new DetailProductoResponse("99", "Service no encontrado", null);

        }

    }


    //----------------------------- UPDATE ----------------------------------
    @PostMapping("/update")
    public UpdateProductoResponse updateProducto(@RequestBody ProductoDetailDto productoDetailDto){
        try{
            if(maintenanceService.updateProducto(productoDetailDto)){
                return new UpdateProductoResponse("01","Exito");
            }else {
                return new UpdateProductoResponse("02","Producto no encontrado");
            }
        }catch (Exception e){
            e.printStackTrace();
            return new UpdateProductoResponse("99","Service no encontrado");
        }
    }

    //----------------------------- ADD ----------------------------------
    //No se maneja con Json, sino con form-data(necesita params), para manejar un archivo (imagen) de tipo MultipartFile
    @PostMapping("/add")
    public AddProductoResponse addProducto(@RequestParam("idProducto") String idProducto,
                                           @RequestParam("nombre") String nombre,
                                           @RequestParam("descripcion") String descripcion,
                                           @RequestParam("precio") Double precio,
                                           @RequestParam("idCategoria") Integer categoria,
                                           @RequestPart(value = "imagen", required = false) MultipartFile imagen){

        try {
            ProductoInsertDto productoInsertDto = new ProductoInsertDto(Integer.parseInt(idProducto), nombre, descripcion, precio, categoria, imagen);

            if (maintenanceService.addProducto(productoInsertDto)) {
                return new AddProductoResponse("01", "Producto agregado correctamente.");
            } else {
                return new AddProductoResponse("02", "");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new AddProductoResponse("99", "Ocurrió un error inesperado en el servicio.");
        }

    }

    //--------------------------------------DELETE----------------------------------------------
    @PostMapping("/delete")
    public DeleteProductoResponse deleteProducto(@RequestParam(value = "id", defaultValue = "0") String id){
        try{
            int idProducto = Integer.parseInt(id);
            if(idProducto>0){
                boolean isDeleted = maintenanceService.deleteProducto(idProducto);
                if(isDeleted){
                    return new DeleteProductoResponse("01","Producto eliminado correctamente");
                }else{
                    return new DeleteProductoResponse("02","Producto no encontrado");
                }
            }else {
                return new DeleteProductoResponse("03","Invalid Parameter");
            }
        }catch (Exception e){
            e.printStackTrace();
            return new DeleteProductoResponse("99","Service no encontrado");
        }
    }


}
