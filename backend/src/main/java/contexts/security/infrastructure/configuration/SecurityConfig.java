package contexts.security.infrastructure.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import contexts.security.application.UserFinder;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@AllArgsConstructor
@Configuration
public class SecurityConfig {

    private UserFinder userAppService;
    private final JwtConfig jwtConfig;
    private final ObjectMapper objectMapper;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                .userDetailsService(this.userAppService)
                .addFilterAfter(jwtTokenVerifierFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests()
                .and()
                .authorizeHttpRequests()
                .anyRequest().permitAll().and()
                .authenticationManager(authenticationManager(http));

        return http.build();
    }


    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http
                .getSharedObject(AuthenticationManagerBuilder.class);

        return authenticationManagerBuilder.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }


    @Bean
    public JwtTokenVerifierFilter jwtTokenVerifierFilter() {
        return new JwtTokenVerifierFilter(this.jwtConfig.getKey(), this.objectMapper);
    }
}