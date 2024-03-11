package com.resitechpro.usermanagmentservice.config.resolver;


import com.resitechpro.usermanagmentservice.config.resolver.TenantResolver;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

@Component
public class JwtTenantResolver implements TenantResolver<HttpServletRequest> {

    public JwtTenantResolver() { /* there is no injectable beans needed */ }

    @Override
    public String resolveTenant(HttpServletRequest request) {
        if( request.getHeader("X-tenant-id") != null){
            return request.getHeader("X-tenant-id");
        }
        return "public";
    }
}
