package ar.com.ada.api.cursos.models.request;

import javax.validation.constraints.*;

/**
 * LoginRequest
 */
public class LoginRequest {
    @NotBlank(message = "username es obligatorio")
    public String username;
    @NotBlank(message = "password es obligatorio")
    public String password;
} 