package pe.edu.cibertec.Project_web_backend_gruop_2.config;


import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

@Configuration
@EnableCaching
public class HikariCpConfig {

    @Value("${DB_TIENDABD_URL}")
    private String dbTiendabdUrl;
    @Value("${DB_TIENDADB_USER}")
    private String dbTiendabdUser;
    @Value("${DB_TIENDABD_PASS}")
    private String dbTiendabdPass;
    @Value("${DB_TIENDABD_DRIVER}")
    private String dbTiendabdDriver;

    @Bean
    public HikariDataSource hikariDataSource() {

        HikariConfig config = new HikariConfig();

        /**
         * Configurar propiedad de conexion a BD
         */
        config.setJdbcUrl(dbTiendabdUrl);
        config.setUsername(dbTiendabdUser);
        config.setPassword(dbTiendabdPass);
        config.setDriverClassName(dbTiendabdDriver);

        /**
         * Configurar propiedades del pool de HikariCP
         * - MaximumPoolSize: Máximo # de conexiones del pool.
         * - MinimumIdle: Mínimo # de conexiones inactivas del pool.
         * - IdleTimeout: Tiempo máximo de espera para
         *      eliminar una conexión inactiva.
         * - ConnectionTimeout: Tiempo máximo de espera
         *      para conectarse a la BD.
         */
        config.setMaximumPoolSize(20);
        config.setMinimumIdle(5);
        config.setIdleTimeout(300000);
        config.setConnectionTimeout(30000);

        System.out.println("###### HikariCP initialized ######");
        return new HikariDataSource(config);

    }



}
