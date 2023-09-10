package org.moneyMinder.interceptors;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Base64;

/**
 * Interceptor para la autenticación básica.
 * Este interceptor verifica las credenciales de autenticación básica proporcionadas en la cabecera "Authorization".
 * Si las credenciales son válidas, permite que la solicitud continúe; de lo contrario, devuelve una respuesta de error "Unauthorized" (401).
 */
public class BasicAuthenInterceptor extends HandlerInterceptorAdapter {
    /**
     * Premanejo de la solicitud para la autenticación básica.
     *
     * @param request  La solicitud HTTP.
     * @param response La respuesta HTTP.
     * @param handler  El objeto controlador.
     * @return true si las credenciales son válidas y la solicitud puede continuar; de lo contrario, false.
     * @throws Exception Si ocurre un error durante el procesamiento de la solicitud.
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null) {
            String usernameAndPassHash = authHeader.split(" ")[1];
            String usernameAndPass = new String(Base64.getDecoder().decode(usernameAndPassHash));
            String username = usernameAndPass.split(":")[0];
            String password = usernameAndPass.split(":")[1];
            if (username.equalsIgnoreCase("MoneyMinder") && password.equalsIgnoreCase("MoneyMinder")) {
                return true;
            } else {
                response.sendError(401, "Unauthorized");
                return false;
            }
        } else {
            response.sendError(401, "Unauthorized");
            return false;
        }
    }
}
