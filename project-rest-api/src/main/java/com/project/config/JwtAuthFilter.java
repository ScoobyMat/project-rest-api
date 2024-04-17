package com.project.config;

import java.io.IOException;

import com.project.service.JwtService;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
public class JwtAuthFilter extends OncePerRequestFilter { // filtrowanie każdego przesłanego żądania

    private final JwtService jwtService; // automatycznie wstrzykiwany, JwtService posiada adnotację @Service
    private final UserDetailsService userDetailsService; // automatycznie wstrzykiwany, zdefiniowany jako @Bean w SecurityConfig

    public JwtAuthFilter(JwtService jwtService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION); //Bearer eyJhb...
        final String prefix = "Bearer ";
        if (authHeader == null || !authHeader.startsWith(prefix)) {
            filterChain.doFilter(request, response); // brak nagłówka z tokenem, tylko przetwarzanie przez kolejne filtry
            return;
        }

        final String token = authHeader.substring(prefix.length());
        final String userName = jwtService.extractUserName(token);//e-mail

        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) { //drugi warunek sprawdza czy użytkownik nie był już uwierzytelniony
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userName);
            if (jwtService.isTokenValid(token, userDetails)) {
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null,
                                userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken); //Authentication to po prostu obiekt z danymi o uwierzytelnionym użytkowniku.
                //SecurityContextHolder zajmuje się udostępnianiem tych danych,  w obrębie jednego wątku zawsze zwróci ten sam SecurityContext.
                //Domyślnie używa obiektu ThreadLocal do przechowywania kontekstu bezpieczeństwa, co oznacza, że kontekst bezpieczeństwa jest zawsze dostępny dla metod w tym samym wątku wykonania.
            }
        }

        filterChain.doFilter(request, response); // o tym trzeba pamiętać, aby umożliwić przetwarzanie przez kolejne filtry w łańcuchu
    }
}