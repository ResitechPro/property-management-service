package com.resitechpro.propertymanagmentservice.config.resolver;
@FunctionalInterface
public interface TenantResolver<T> {
    String resolveTenant(T object);
}
