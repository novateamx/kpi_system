package uz.java.kpisystem.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.LocaleResolver;
import uz.java.kpisystem.config.CustomUserDetails;
import uz.java.kpisystem.exception.CustomNotFoundException;
import uz.java.kpisystem.service.CustomUserDetailService;
import uz.java.kpisystem.service.JwtTokenService;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;

import static uz.java.kpisystem.config.SecurityConfig.AUTH_WHITELIST;

@Slf4j
@Component
@RequiredArgsConstructor
public class GlobalFilter extends OncePerRequestFilter {

    private final AntPathMatcher pathMatcher = new AntPathMatcher();
    private final JwtTokenService jwtTokenService;
    private final CustomUserDetailService userDetailsService;
    private final LocaleResolver localeResolver;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        long start = System.currentTimeMillis();
        String requestUri = request.getRequestURI();
        log.info("Getting request URI: " + requestUri);
        if (!isOpenPath(requestUri)) {
            try {
                String token = getTokenFromRequest(request);
                if (jwtTokenService.isValid(token)) {
                    String userId = jwtTokenService.subject(token);
                    CustomUserDetails customUserDetails = userDetailsService.loadUserByUsername(userId);
                    authenticate(request, customUserDetails);
                }
            } catch (RuntimeException e) {
                log.error("Access denied: " + e.getMessage());
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");
                return;
            }
        }
        setLang(request, response);
        filterChain.doFilter(request, response);
        long finish = System.currentTimeMillis();
        log.info("->->Request = [ {}?{} ] Elapsed time to proceed this request = {}", request.getRequestURI(),
                request.getQueryString() == null ? "" : request.getQueryString(), finish - start);
    }

    private void setLang(HttpServletRequest request, HttpServletResponse response) {
        String header = request.getHeader("Lang");
        localeResolver.setLocale(request, response, new Locale(Objects.requireNonNullElse(header, "Uz")));
    }

    private void authenticate(HttpServletRequest request, CustomUserDetails customUserDetails) {
        // user malumotlari va authoritylari(role, permission) sessiyaga saqlash joyi
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                        customUserDetails, null, customUserDetails.getAuthorities());
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
            return bearerToken.trim().substring(7);
        } else {
            throw new CustomNotFoundException("token.not.valid");
        }
    }

    private boolean isOpenPath(String currentPath) {
        return Arrays.stream(AUTH_WHITELIST).anyMatch(p -> pathMatcher.match(p, currentPath));

    }
}
