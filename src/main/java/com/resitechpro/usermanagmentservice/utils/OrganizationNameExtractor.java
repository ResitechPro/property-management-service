package com.resitechpro.usermanagmentservice.utils;

import org.springframework.stereotype.Component;

@Component
public class OrganizationNameExtractor {
    public String extract(String email) {
        return email.substring(email.indexOf("@") + 1, email.indexOf(".com"));
    }
}
