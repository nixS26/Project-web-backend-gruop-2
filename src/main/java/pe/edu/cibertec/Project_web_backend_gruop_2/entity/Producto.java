package pe.edu.cibertec.Project_web_backend_gruop_2.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idProducto;
    private String nombre;
    private String descripcion;
    private Double precio;

    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "idCategoria", nullable = false)
    private Categoria categoria;

    private String imagen;
}
