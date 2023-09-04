package org.moneyMinder.interceptors;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Base64;

public class BasicAuthenInterceptor extends HandlerInterceptorAdapter {
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
            }
            else {
                response.sendError(401, "Unauthorized");
                return false;
            }
        }
        else {
            response.sendError(401, "Unauthorized");
            return false;
        }
    }
}