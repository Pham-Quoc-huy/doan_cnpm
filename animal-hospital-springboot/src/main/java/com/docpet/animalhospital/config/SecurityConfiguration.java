package com.docpet.animalhospital.config;

import static org.springframework.security.config.Customizer.withDefaults;

import com.docpet.animalhospital.security.AuthoritiesConstants;
import com.docpet.animalhospital.security.DomainUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfiguration {

    private final DomainUserDetailsService userDetailsService;
    private final JwtDecoder jwtDecoder;

    public SecurityConfiguration(DomainUserDetailsService userDetailsService, JwtDecoder jwtDecoder) {
        this.userDetailsService = userDetailsService;
        this.jwtDecoder = jwtDecoder;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public HttpFirewall httpFirewall() {
        return new CustomHttpFirewall();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.httpFirewall(httpFirewall());
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, MvcRequestMatcher.Builder mvc) throws Exception {
        http
            .authenticationProvider(authenticationProvider())
            .cors(withDefaults())
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(authz ->
                authz
                    // Public endpoints - phải đặt trước các rule khác
                    .requestMatchers(mvc.pattern(HttpMethod.POST, "/api/authenticate")).permitAll()
                    .requestMatchers(mvc.pattern(HttpMethod.GET, "/api/authenticate")).permitAll()
                    .requestMatchers(mvc.pattern(HttpMethod.POST, "/api/register")).permitAll()
                    .requestMatchers(mvc.pattern(HttpMethod.POST, "/api/register-vet")).permitAll()
                    .requestMatchers(mvc.pattern("/api/activate")).permitAll()
                    .requestMatchers(mvc.pattern("/api/account/reset-password/init")).permitAll()
                    .requestMatchers(mvc.pattern("/api/account/reset-password/finish")).permitAll()
                    .requestMatchers(mvc.pattern("/v3/api-docs/**")).permitAll()
                    .requestMatchers(mvc.pattern("/swagger-ui/**")).permitAll()
                    .requestMatchers(mvc.pattern("/management/health")).permitAll()
                    .requestMatchers(mvc.pattern("/management/health/**")).permitAll()
                    .requestMatchers(mvc.pattern("/management/info")).permitAll()
                    .requestMatchers(mvc.pattern("/management/prometheus")).permitAll()
                    // Protected endpoints
                    .requestMatchers(mvc.pattern("/api/admin/**")).hasAuthority(AuthoritiesConstants.ADMIN)
                    .requestMatchers(mvc.pattern("/api/vet/**")).hasAuthority(AuthoritiesConstants.DOCTOR)
                    .requestMatchers(mvc.pattern("/api/**")).authenticated()
                    .requestMatchers(mvc.pattern("/management/**")).hasAuthority(AuthoritiesConstants.ADMIN)
            )
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .exceptionHandling(exceptions ->
                exceptions
                    .authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint())
                    .accessDeniedHandler(new BearerTokenAccessDeniedHandler())
            )
            .oauth2ResourceServer(oauth2 -> 
                oauth2.jwt(jwt -> {
                    jwt.decoder(jwtDecoder);
                    jwt.jwtAuthenticationConverter(jwtAuthenticationConverter());
                })
            );
        return http.build();
    }

    @Bean
    MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector) {
        return new MvcRequestMatcher.Builder(introspector);
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(jwt -> {
            // Extract authorities from "auth" claim (space-separated string)
            String authClaim = jwt.getClaimAsString("auth");
            if (authClaim == null || authClaim.trim().isEmpty()) {
                return java.util.Collections.emptyList();
            }
            return java.util.Arrays.stream(authClaim.split("\\s+"))
                .map(SimpleGrantedAuthority::new)
                .map(GrantedAuthority.class::cast)
                .collect(java.util.stream.Collectors.toList());
        });
        // Set principal name to JWT subject (username) instead of Jwt object
        converter.setPrincipalClaimName("sub");
        return converter;
    }
}

