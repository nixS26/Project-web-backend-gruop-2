package pe.edu.cibertec.Project_web_backend_gruop_2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import pe.edu.cibertec.Project_web_backend_gruop_2.service.Impl.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    //Filtro, aquel elemento que esta en el punto internmedio de las consultas al controller, intercepta las peticione sy las valida
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http

                //DEFINIR RUTAS PROTEGIDAS Y QUIEN PUEDE ACCEDER A ELLAS
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/maintenance-pro/**").permitAll()
                        .requestMatchers("/images/**").permitAll() // Permitir acceso público a imágenes
                        .requestMatchers("/maintenance/login").permitAll()//rutas con acceso no autenticado
                        .requestMatchers("/maintenance/start").hasAnyRole("ADMINISTRADOR","VENDEDOR")//acceso para ADMIN Y OPERATOR
                        .requestMatchers("/maintenance/detail/{id}").hasAnyRole("ADMINISTRADOR","VENDEDOR")
                        .requestMatchers("/maintenance/register").permitAll()
                        .requestMatchers("/maintenance/add").hasAnyRole("ADMINISTRADOR")
                        .requestMatchers("/maintenance/edit/{id}").hasAnyRole("ADMINISTRADOR")
                        .requestMatchers(HttpMethod.POST,"/maintenance/remove/{id}").hasRole("ADMINISTRADOR")
                        .requestMatchers("/maintenance/restricted").hasAnyRole("ADMINISTRADOR","VENDEDOR")
                        .requestMatchers("/static/**").permitAll()
                        .requestMatchers("/css/**").permitAll()
                        .requestMatchers("/maintenance/**").hasAnyRole("ADMINISTRADOR")


                        .anyRequest().authenticated()//EL resto debe autenticarse
                )

                //REEDIRECCIONAR A UN APAGINA DE ERROR EN CASO NO TENGA PERMISOS
                .exceptionHandling(ex ->ex
                        .accessDeniedHandler((request,
                                              response,
                                              accessDeniedException) -> {
                            //Redirige a la pagina restricted, sino estas autorizado
                            response.sendRedirect("/maintenance/restricted");
                        })
                )

                //configurar formulario de inicio desesión
                .formLogin(form -> form
                        .loginPage("/maintenance/login") //le estamos diciendo que esta es la pagina personalizada
                        .defaultSuccessUrl("/maintenance/start", false) //si es satisfactorio nos redirecionara a la pagina que deseabamos entrar
                        .permitAll()

                )

                //configurar salida(logout)
                .logout(logout -> logout
                        .logoutUrl("/maintenance/logout")
                        .logoutSuccessUrl("/maintenance/login?logout")
                        .permitAll()
                );

        return http.build();




    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        //nos permite encriptar los pasword
        return new BCryptPasswordEncoder();
    }





}
