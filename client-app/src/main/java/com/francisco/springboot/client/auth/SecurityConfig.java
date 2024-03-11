package com.francisco.springboot.client.auth;

import jakarta.annotation.security.PermitAll;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;  //importamos esta configuracion default

@Configuration
public class SecurityConfig {
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.authorizeHttpRequests((authHttp)-> authHttp
          //configuramos la autorizacion de cada ruta
          .requestMatchers(HttpMethod.GET,"/authorized").permitAll()
          .requestMatchers(HttpMethod.GET, "/list").hasAnyAuthority("SCOPE_read", "SCOPE_write") //Cuando son varios roles usamos hasAnyAuthority
          .requestMatchers(HttpMethod.POST, "/create").hasAuthority("SCOPE_write") //Cuando es un solo rol usamos hasAuthority
          .anyRequest().authenticated()) //Esto es que todas las demas rutas deben ser autenticadas
          .csrf(csrf->csrf.disable()) //Deshabilitamos la funcion csrf que es solamente para formularios, ya que estamos en APIRest y no es necesario
          .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))  //Con "SessionCreationPolicy.STATELESS" evito que los datos del usuario se guarden en la session http.
                                                                                                        //en pocas palabras le decimos que lo vamos a manejar en el token
          .oauth2Login(login -> login.loginPage("/oauth2/authorization/client-app"))
          .oauth2Client(withDefaults())
          .oauth2ResourceServer(resourceserver ->resourceserver.jwt(withDefaults()));
          return http.build();
    }

}
