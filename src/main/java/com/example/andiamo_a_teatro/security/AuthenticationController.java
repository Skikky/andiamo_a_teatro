package com.example.andiamo_a_teatro.security;

import com.example.andiamo_a_teatro.exception.*;
import com.example.andiamo_a_teatro.request.AuthenticationRequest;
import com.example.andiamo_a_teatro.request.ChangePasswordRequest;
import com.example.andiamo_a_teatro.request.RegistrationRequest;
import com.example.andiamo_a_teatro.response.GenericResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegistrationRequest registrationRequest) {
        try {
            return ResponseEntity.ok(authenticationService.register(registrationRequest));
        } catch (PasswordDeboleException e) {
            return new ResponseEntity<>(new ErrorResponse("PasswordDeboleException", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    ResponseEntity<?> login(@RequestBody AuthenticationRequest authenticationRequest) {
        try {
            return ResponseEntity.ok(authenticationService.authenticate(authenticationRequest));
        } catch (UserNotConfirmedException e) {
            return new ResponseEntity<>(new ErrorResponse("UserNotConfirmedException", e.getMessage()), HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("/logout/{id}")
    public void logout(HttpServletRequest httpRequest, @PathVariable Long id) {
        authenticationService.logout(httpRequest, id);
    }

    @ApiIgnore
    @GetMapping("/confirm")
    public ResponseEntity<?> confirmRegistration (@RequestParam Long id, @RequestParam String token) {
        if (authenticationService.confirmRegistration(id, token)) {
            return new ResponseEntity<>(new GenericResponse("Utente verificato con successo. "),HttpStatus.OK);
        }
        return new ResponseEntity<>(new ErrorResponse("UtenteNotConfirmedExcception","non è possibile verificare l'utente"), HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/cambia-password")
    public ResponseEntity<?> cambiaPassword (HttpServletRequest httpRequest, @RequestBody ChangePasswordRequest request) throws EntityNotFoundException, PasswordUgualiException, PasswordSbagliataException, PasswordDeboleException {
        authenticationService.cambiaPassword(request);
        authenticationService.logout(httpRequest, request.getIdUtente());
        return new ResponseEntity<>(new GenericResponse("Password cambiata con successo"),HttpStatus.OK);
    }

    @PostMapping("/password-dimenticata")
    public ResponseEntity<?> passwordDimenticata(@RequestParam String email, @RequestParam String newPassword) throws PasswordDeboleException {
        try {
            authenticationService.passwordDimenticata(email, newPassword);
            return new ResponseEntity<>(new GenericResponse("Email di reset inviata"),HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @ApiIgnore
    @GetMapping("/reset")
    public ResponseEntity<GenericResponse> resetPassword(@RequestParam String email, @RequestParam String newPassword) {
        authenticationService.resetPassword(email, newPassword);
        return new ResponseEntity<>(new GenericResponse("Email resettata con successo"), HttpStatus.OK);
    }
}
