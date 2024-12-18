package pe.edu.cibertec.Project_web_backend_gruop_2.controller;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class ImagenController {

    private final String uploadDir = "src/main/resources/static/images/";

    @GetMapping("/images/{filename:.+}")
    public Resource getImage(@PathVariable String filename) {
        try {
            Path filePath = Paths.get(uploadDir).resolve(filename).normalize();
            return new UrlResource(filePath.toUri());
        } catch (Exception e) {
            throw new RuntimeException("No se pudo cargar la imagen: " + filename, e);
        }
    }
}
