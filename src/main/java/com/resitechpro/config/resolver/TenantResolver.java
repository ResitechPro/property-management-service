package com.resitechpro.config.resolver;
@FunctionalInterface
public interface TenantResolver<T> {
    String resolveTenant(T object);
}
