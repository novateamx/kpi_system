package uz.java.kpisystem.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import uz.java.kpisystem.config.CustomUserDetails;
import uz.java.kpisystem.exception.CustomNotFoundException;
import uz.java.kpisystem.handler.GlobalExceptionHandler;
import uz.java.kpisystem.service.CustomUserDetailService;
import uz.java.kpisystem.service.JwtTokenService;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.Arrays;

import static uz.java.kpisystem.config.SecurityConfig.AUTH_WHITELIST;

@Slf4j
@Component
@RequiredArgsConstructor
public class GlobalFilter extends OncePerRequestFilter {

    private final AntPathMatcher pathMatcher = new AntPathMatcher();
    private final JwtTokenService jwtTokenService;
    private final CustomUserDetailService userDetailsService;

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
                    CustomUserDetails customUserDetails = userDetailsService.loadUserByUsername(username);
                    authenticate(request, customUserDetails);
                }
            } catch (GenericRuntimeException | AccessDeniedException e) {
                resolver.resolveException(request, response, null, e);
                return;
            }
        }
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
