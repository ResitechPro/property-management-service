package com.resitechpro.usermanagmentservice.config.resolver;
@FunctionalInterface
public interface TenantResolver<T> {
    String resolveTenant(T object);
}
