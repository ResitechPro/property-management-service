package com.resitechpro.exception.customexceptions;

import com.resitechpro.utils.ErrorMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Builder
@AllArgsConstructor
@Getter
@Setter
public class ValidationException extends Exception {
    private final transient List<ErrorMessage> errors;
}
