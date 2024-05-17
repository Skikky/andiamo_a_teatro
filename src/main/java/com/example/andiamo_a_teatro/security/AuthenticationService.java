package com.example.andiamo_a_teatro.security;

import com.example.andiamo_a_teatro.entities.Comune;
import com.example.andiamo_a_teatro.entities.TokenBlackList;
import com.example.andiamo_a_teatro.entities.Utente;
import com.example.andiamo_a_teatro.enums.Role;
import com.example.andiamo_a_teatro.exception.UserNotConfirmedException;
import com.example.andiamo_a_teatro.repositories.ComuneRepository;
import com.example.andiamo_a_teatro.repositories.UtenteRepository;
import com.example.andiamo_a_teatro.request.AuthenticationRequest;
import com.example.andiamo_a_teatro.request.RegistrationRequest;
import com.example.andiamo_a_teatro.response.AuthenticationResponse;
import com.example.andiamo_a_teatro.services.EmailService;
import com.example.andiamo_a_teatro.services.TokenBlackListService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
public class AuthenticationService {
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UtenteRepository utenteRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private TokenBlackListService tokenBlackListService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private ComuneRepository comuneRepository;

    public AuthenticationResponse register(RegistrationRequest registrationRequest) {

        Comune comune = null;
        if (registrationRequest.getId_comune() != null) {
            Optional<Comune> comuneOptional = comuneRepository.findById(registrationRequest.getId_comune());
            comune = comuneOptional.orElse(null);
        }

        var user = Utente.builder()
                .nome(registrationRequest.getNome())
                .cognome(registrationRequest.getCognome())
                .nascita(registrationRequest.getNascita())
                .indirizzo(registrationRequest.getIndirizzo())
                .telefono(registrationRequest.getTelefono())
                .saldo(registrationRequest.getSaldo())
                .email(registrationRequest.getEmail())
                .comune(comune)
                .role(Role.TOCONFIRM)
                .password(passwordEncoder.encode(registrationRequest.getPassword()))
                .build();
        var jwtToken = jwtService.generateToken(user);
        user.setRegistrationToken(jwtToken);
        utenteRepository.saveAndFlush(user);

        sendConfirmationEmail(user);

        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    private void sendConfirmationEmail(Utente utente) {
        String url = "http://localhost:8080/auth/confirm?id=" + utente.getId() + "&token=" + utente.getRegistrationToken();
        String text = "Clicca per confermare la registrazione: " + url;
        emailService.sendEmail(utente.getEmail(), "Conferma", text);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) throws UserNotConfirmedException {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authenticationRequest.getEmail(),
                authenticationRequest.getPassword()
        ));
        var user = utenteRepository.findUtenteByEmail(authenticationRequest.getEmail());
        if (user.getRole().equals(Role.TOCONFIRM)) {
            throw new UserNotConfirmedException();
        }
        var jwtToken = jwtService.generateToken(user);
        if (tokenBlackListService.tokenNotValidFromUtenteById(user.getId()).contains(jwtToken)) {
            String email = jwtService.extractUsername(jwtToken);
            // Carica l'utente dal database
            UserDetails userDetails = utenteRepository.findUtenteByEmail(email);

            // Genera un nuovo token con le informazioni aggiornate
            String newToken = jwtService.generateToken(userDetails);
            return AuthenticationResponse.builder().token(newToken).build();
        }
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    public void logout(HttpServletRequest httpRequest, Long id) {
        String token = extractTokenFromRequest(httpRequest);
        TokenBlackList tokenBlackList = TokenBlackList.builder()
                .utente(utenteRepository.getReferenceById(id))
                .token(token)
                .build();
        tokenBlackListService.createTokenBlackList(tokenBlackList);
    }

    public String extractTokenFromRequest(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        return null;
    }

    public boolean confirmRegistration (Long id, String token) {
        Utente utente = utenteRepository.getReferenceById(id);
        if (utente.getRegistrationToken().equals(token)) {
            utente.setRole(Role.USER);
            utenteRepository.saveAndFlush(utente);
            return true;
        }
        return false;
    }
}
