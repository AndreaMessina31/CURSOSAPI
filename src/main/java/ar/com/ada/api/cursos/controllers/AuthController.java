package ar.com.ada.api.cursos.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ar.com.ada.api.cursos.entities.Usuario;
import ar.com.ada.api.cursos.models.request.*;
import ar.com.ada.api.cursos.models.response.*;
import ar.com.ada.api.cursos.security.jwt.JWTTokenUtil;
import ar.com.ada.api.cursos.services.JWTUserDetailsService;
import ar.com.ada.api.cursos.services.UsuarioService;

/**
 * AuthController
 */
@RestController
public class AuthController {

    @Autowired
    UsuarioService usuarioService;

    /*
     * @Autowired private AuthenticationManager authenticationManager;
     */
    @Autowired
    private JWTTokenUtil jwtTokenUtil;

    @Autowired
    private JWTUserDetailsService userDetailsService;

    // Auth : authentication ->
    @PostMapping("/api/auth/register")
    public ResponseEntity<RegistrationResponse> postRegisterUser(@RequestBody RegistrationRequest req) {
        RegistrationResponse r = new RegistrationResponse();

        // aca creamos la persona y el usuario a traves del service.

        Usuario usuario = usuarioService.crearUsuario(req.userType, req.fullName, req.country, req.identificationType,
                req.identification, req.birthDate, req.email, req.password);

        if (usuario.getUsuarioId() == null) {
            r.isOk = false;
            r.message = "error interno al crear el usuario. chequear si esta creado previamente";
            return ResponseEntity.badRequest().body(r);
        }

        r.isOk = true;
        r.message = "Te registraste con exitoooo!!!!!!!";
        r.userId = usuario.getUsuarioId(); // <-- AQUI ponemos el numerito de id para darle a front!

        return ResponseEntity.ok(r);

    }

    @PostMapping("/api/auth/login") // probando nuestro login
    public ResponseEntity<?> createAuthenticationToken(@RequestBody LoginRequest authenticationRequest)
            throws Exception {

        Usuario usuarioLogueado = usuarioService.login(authenticationRequest.username, authenticationRequest.password);

        UserDetails userDetails = usuarioService.getUserAsUserDetail(usuarioLogueado);
        Map<String, Object> claims = usuarioService.getUserClaims(usuarioLogueado);

        // Genero los roles pero con los Claims(los propositos)
        // En este caso nuestros claims tienen info del tipo de usuario
        // y de la entidad que representa
        // Esta info va a viajar con el token, o sea, cualquiera puede
        // ver esos ids de que user pertenecen si logran interceptar el token
        // Por eso es que en cada request debemos validar el token(firma)
        String token = jwtTokenUtil.generateToken(userDetails, claims);

        // Cambio para que devuelva el full perfil
        // Usuario u = usuarioService.buscarPorUsername(authenticationRequest.username);

        LoginResponse r = new LoginResponse();
        r.id = usuarioLogueado.getUsuarioId();
        r.userType = usuarioLogueado.getTipoUsuarioId();
        r.entityId = usuarioLogueado.obtenerEntityId();
        r.username = authenticationRequest.username;
        r.email = usuarioLogueado.getEmail().toLowerCase();
        r.token = token;

        return ResponseEntity.ok(r);

    }

}