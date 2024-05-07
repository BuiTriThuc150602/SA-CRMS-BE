package vn.edu.iuh.fit.authservice.config;

import javax.crypto.spec.SecretKeySpec;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@Slf4j
public class AuthConfig {

  @Value("${jwt.secret}")
  private String SECRET;

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public UserDetailsService userDetailsService() {
    return new CustomUserDetailsService();
  }


  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    log.info("Configuring security filter chain : " + http.toString());
    http
        .csrf(
            csrf -> csrf.disable()
        )
        .cors(
            cors -> cors.disable()
        )
        .authorizeHttpRequests(
            authorizeRequests -> authorizeRequests
                .requestMatchers("/auth/login", "/auth/register")
                .permitAll()
                .anyRequest()
                .authenticated()
        )
        .oauth2ResourceServer(
            oauth2ResourceServer -> oauth2ResourceServer.jwt(
                Customizer.withDefaults()
            )
        )
        .sessionManagement(
            session -> session
                .sessionCreationPolicy(
                    SessionCreationPolicy.STATELESS
                )
        );
    return http.build();
  }

  @Bean
  public JwtDecoder jwtDecoder() {
    var secretKey = new SecretKeySpec(SECRET.getBytes(), "HS256");
    return NimbusJwtDecoder.withSecretKey(secretKey)
        .macAlgorithm(MacAlgorithm.HS256)
        .build();
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
      throws Exception {
    return config.getAuthenticationManager();
  }

  @Bean
  public AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
    authenticationProvider.setUserDetailsService(userDetailsService());
    authenticationProvider.setPasswordEncoder(passwordEncoder());
    return authenticationProvider;
  }


}
