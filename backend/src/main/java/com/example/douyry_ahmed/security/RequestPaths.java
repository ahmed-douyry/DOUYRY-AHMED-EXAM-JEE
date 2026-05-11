package com.example.douyry_ahmed.security;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpMethod;

/**
 * Chemins publics (hors JWT) : même logique pour {@link JwtAuthenticationFilter} et {@code permitAll}.
 */
public final class RequestPaths {

    private RequestPaths() {
    }

    public static String pathWithinApplication(HttpServletRequest request) {
        String uri = request.getRequestURI();
        String context = request.getContextPath();
        if (context != null && !context.isEmpty() && uri.startsWith(context)) {
            return uri.substring(context.length());
        }
        return uri;
    }

    /**
     * {@code true} pour login, swagger, H2, métadonnées OAuth well-known, et toutes les requêtes OPTIONS (CORS).
     */
    public static boolean isPublicPath(HttpServletRequest request) {
        if (HttpMethod.OPTIONS.matches(request.getMethod())) {
            return true;
        }
        String path = pathWithinApplication(request);
        return path.startsWith("/auth/")
                || path.startsWith("/v3/api-docs")
                || path.startsWith("/swagger-ui")
                || "/swagger-ui.html".equals(path)
                || path.startsWith("/h2-console")
                || path.startsWith("/.well-known/oauth-protected-resource");
    }
}
