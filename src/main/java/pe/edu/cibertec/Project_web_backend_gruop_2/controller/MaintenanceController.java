package pe.edu.cibertec.Project_web_backend_gruop_2.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pe.edu.cibertec.Project_web_backend_gruop_2.dto.ProductoDetailDto;
import pe.edu.cibertec.Project_web_backend_gruop_2.dto.ProductoDto;
import pe.edu.cibertec.Project_web_backend_gruop_2.dto.ProductoInsertDto;
import pe.edu.cibertec.Project_web_backend_gruop_2.dto.UsuarioInsertDTO;
import pe.edu.cibertec.Project_web_backend_gruop_2.entity.Producto;
import pe.edu.cibertec.Project_web_backend_gruop_2.entity.Usuario;
import pe.edu.cibertec.Project_web_backend_gruop_2.service.MaintenanceService;
import org.springframework.ui.Model;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/maintenance")
public class MaintenanceController {

    @Autowired
    MaintenanceService maintenanceService;

    //-------------------------LOGIN--------------------------------------------------
    //
    @GetMapping("/login")
    public String login(Model model) {
        return "login_user";

    }

    @GetMapping("/restricted")
    public String restricted(Model model) {
        return "restricted_user";

    }
    //--------------------------------REGISTRO-----------------------------------
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        UsuarioInsertDTO usuarioInsertDTO = new UsuarioInsertDTO(
                "",
                "",
                "",
                "",
                new Date(),
                ""
        );
        model.addAttribute("usuarioInsertDTO", usuarioInsertDTO);
        return "maintenance_register_user";
    }
    @PostMapping("/register")
    public String insertFilm (UsuarioInsertDTO usuarioInsertDTO){
        maintenanceService.registerUser(usuarioInsertDTO);
        return "redirect:/maintenance/start";
    }

   //--------------------------LIST--------------------------------------
    @GetMapping("/start")
    public String star(Model model){
        List<ProductoDto> productos= null;
        try {
            productos = maintenanceService.findAllProductos();
            model.addAttribute("productos", productos);
            model.addAttribute("error","Maintenance");
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "ocurrio un error al obtener los datos");

        }

        return "maintenance";
    }

    //-------------------------ADD-----------------------------------
    @GetMapping("/add") //SE ASOCIA CON LOS METODO DEL CONTROLADOR
    public String showAddForm(Model model) {
        model.addAttribute("productoInsertDto", new ProductoInsertDto(0 ,"", "", null, null,null));
        model.addAttribute("categorias", maintenanceService.findAllCategorias());
        return "maintenance_add";
    }

    @PostMapping("/add-confirm")
    public String addConfirm(@ModelAttribute ProductoInsertDto productoInsertDto) {
        boolean success = false;
        try {
            success = maintenanceService.addProducto(productoInsertDto);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (success) {
            return "redirect:/maintenance/start";
        } else {
            return "maintenance_add";
        }
    }
    //------------------------DETAIL-----------------------------------
    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Integer id, Model model) {
        try {
            Optional<ProductoDetailDto> optionalProductoDetail = maintenanceService.findProductoById(id);
            if (optionalProductoDetail.isPresent()) {
                ProductoDetailDto productoDetailDto = optionalProductoDetail.get();
                model.addAttribute("producto", productoDetailDto);
                model.addAttribute("error", null);
            } else {
                model.addAttribute("producto", null);
                model.addAttribute("error", "Producto no encontrado");
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("producto", null);
            model.addAttribute("error", "Ocurrió un error al obtener los datos");
        }

        return "maintenance_detail";
    }


    //-----------------------DELETE------------------------------------

    @PostMapping("/remove/{id}")
    public String eliminarProducto(@PathVariable int id) {
        try {
            maintenanceService.deleteProducto(id);
            return "redirect:/maintenance/start";
        } catch (Exception ex) {
            return "error"; // Ajusta según tu lógica de error
        }

    }
    //------------------------EDIT------------------------------------
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        try {
            Optional<ProductoDetailDto> optionalProducto = maintenanceService.findProductoById(id);

            if (optionalProducto.isPresent()) {
                ProductoDetailDto productoDetailDto = optionalProducto.get();
                model.addAttribute("producto", productoDetailDto);
                model.addAttribute("categorias", maintenanceService.findAllCategorias());
            } else {
                model.addAttribute("error", "El producto con ID " + id + " no fue encontrado.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Ocurrió un error al actualizar los datos");
        }

        return "maintenance_edit";
    }

    @PostMapping("/edit-confirm")
    public String editConfirm(@ModelAttribute ProductoDetailDto productoDetailDto, Model model) {

        try {
            maintenanceService.updateProducto(productoDetailDto);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return "redirect:/maintenance/start";

    }


}
