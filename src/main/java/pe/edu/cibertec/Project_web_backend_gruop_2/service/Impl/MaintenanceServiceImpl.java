package pe.edu.cibertec.Project_web_backend_gruop_2.service.Impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pe.edu.cibertec.Project_web_backend_gruop_2.dto.*;
import pe.edu.cibertec.Project_web_backend_gruop_2.entity.Categoria;
import pe.edu.cibertec.Project_web_backend_gruop_2.entity.Producto;
import pe.edu.cibertec.Project_web_backend_gruop_2.entity.Usuario;
import pe.edu.cibertec.Project_web_backend_gruop_2.repository.CategoriaRepository;
import pe.edu.cibertec.Project_web_backend_gruop_2.repository.ProductoRepository;
import pe.edu.cibertec.Project_web_backend_gruop_2.repository.UsuarioRepository;
import pe.edu.cibertec.Project_web_backend_gruop_2.service.MaintenanceService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class MaintenanceServiceImpl implements MaintenanceService {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    ProductoRepository productoRepository;
    @Autowired
    CategoriaRepository categoriaRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
//---------------------LISTAR PRODUCTO-----------------------------------
    @Override
    public List<ProductoDto> findAllProductos() throws Exception{
        List<ProductoDto> productos = new ArrayList<ProductoDto>();
        //
        Iterable<Producto> iterable = productoRepository.findAll();
        iterable.forEach(producto ->{
            ProductoDto productoDto = new ProductoDto(producto.getIdProducto(),
                    producto.getNombre(),
                    producto.getDescripcion(),
                    producto.getPrecio(),
                    producto.getCategoria().getNombre(),
                    producto.getImagen());
            productos.add(productoDto);
        });
        return productos;
    }

    //---------Trae especificamente un reg, a traves del id-------
    public Optional<ProductoDto> findAllProductoById(int id) throws Exception {
        Optional<Producto> optional = productoRepository.findById(id);
        return optional.map(pro -> new ProductoDto(pro.getIdProducto(),
                pro.getNombre(),
                pro.getDescripcion(),
                pro.getPrecio(),
                pro.getCategoria().getNombre(),
                pro.getImagen()
        ));
    }





//--------------------LIST CATEGORIA----------------------------------------
    @Override
    public List<CategoriaDto> findAllCategorias() {
        List<CategoriaDto> categoriaDtos = new ArrayList<>();
        Iterable<Categoria> categorias = categoriaRepository.findAll();
        categorias.forEach(categoria ->{
            CategoriaDto categoriaDto = new CategoriaDto(categoria.getIdCategoria(),categoria.getNombre());
            categoriaDtos.add(categoriaDto);
        });
        return categoriaDtos;
    }
//----------------------DETAIL PRODUCTO---------------------------------------
@Override
public Optional<ProductoDetailDto> findProductoById(int id)throws Exception {
    return productoRepository.findById(id).map(producto ->
            new ProductoDetailDto(
                    producto.getIdProducto(),
                    producto.getNombre(),
                    producto.getDescripcion(),
                    producto.getPrecio(),
                    producto.getCategoria().getIdCategoria(),
                    producto.getCategoria().getNombre()
            )
    );
}

    //--------------------INSERTAR PRODUCTO---------------------------------------
    @Override
    public Boolean addProducto(ProductoInsertDto productoInsertDto)throws Exception {

            try {
                String imagenPath = null;
                if (!productoInsertDto.imagen().isEmpty()) {
                    String fileName = productoInsertDto.imagen().getOriginalFilename();//nombre original
                    String uploadDir = "src/main/resources/static/images/";
                    // String uploadDir = "src/main/resources/static/images/"; //directorio
                    File file = new File(uploadDir);//Crear el directorio si no existe
                    if (!file.exists()) {
                        file.mkdirs(); // Crear el directorio si no existe
                    }
                    Path path = Paths.get(uploadDir + fileName); //ruta absoluta
                    Files.copy(productoInsertDto.imagen().getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                    // Guardar solo el nombre del archivo
                    imagenPath = fileName;
                }

                Categoria categoria = categoriaRepository.findById(productoInsertDto.idCategoria())
                        .orElseThrow(() -> new RuntimeException("Categoria no encontrada"));

                Producto producto = new Producto();
                producto.setNombre(productoInsertDto.nombre());
                producto.setDescripcion(productoInsertDto.descripcion());
                producto.setPrecio(productoInsertDto.precio());
                producto.setCategoria(categoria);
                producto.setImagen(imagenPath); // Guardar el path de la imagen
                productoRepository.save(producto);

                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }

    }


//-------------------------------LOGIN--------------------------------------------------


    //Resgistrar a un usuario/cliente
    @Override
    public void registerUser(UsuarioInsertDTO usuarioInsertDTO) {
        Usuario usuario = new Usuario();
        usuario.setNombre(usuarioInsertDTO.nombre());
        usuario.setEmail(usuarioInsertDTO.email());
        usuario.setPassword(passwordEncoder.encode(usuarioInsertDTO.password()));
        usuario.setTelefono(usuarioInsertDTO.telefono());
        usuario.setFechaRegistro(new Date());
        usuario.setRol(usuarioInsertDTO.rol());
        usuarioRepository.save(usuario);

    }
//--------------------------------DELETE PRODUCTO------------------------------------
    @Override
    public boolean deleteProducto(int id) throws Exception {
        Optional<Producto> optional = productoRepository.findById(id);
        return optional.map(producto -> {
            productoRepository.delete(producto);
            return true;
        }).orElse(false);
    }

    //--------------------------EDIT PRODUCTO -----------------------------------------
    @Override
    public Boolean updateProducto(ProductoDetailDto productoDetailDto) throws Exception {
        Optional<Producto> optional = productoRepository.findById(productoDetailDto.idProducto());
        return optional.map(
                producto -> {
                    Categoria categoria = categoriaRepository.findById(productoDetailDto.idCategoria())
                            .orElseThrow(() -> new RuntimeException("Categoria no encontrada"));

                    producto.setNombre(productoDetailDto.nombre());
                    producto.setDescripcion(productoDetailDto.descripcion());
                    producto.setPrecio(productoDetailDto.precio());
                    producto.setCategoria(categoria);
                    productoRepository.save(producto);

                    return true;

                }
        ).orElse(false);
    }


}