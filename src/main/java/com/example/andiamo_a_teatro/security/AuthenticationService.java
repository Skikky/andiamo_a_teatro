package com.example.andiamo_a_teatro.security;

import com.example.andiamo_a_teatro.entities.TokenBlackList;
import com.example.andiamo_a_teatro.entities.Utente;
import com.example.andiamo_a_teatro.repositories.UtenteRepository;
import com.example.andiamo_a_teatro.request.AuthenticationRequest;
import com.example.andiamo_a_teatro.request.RegistrationRequest;
import com.example.andiamo_a_teatro.response.AuthenticationResponse;
import com.example.andiamo_a_teatro.services.TokenBlackListService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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

    public AuthenticationResponse register(RegistrationRequest registrationRequest) {
        var user = Utente.builder()
                .nome(registrationRequest.getNome())
                .cognome(registrationRequest.getCognome())
                .nascita(registrationRequest.getNascita())
                .indirizzo(registrationRequest.getIndirizzo())
                .telefono(registrationRequest.getTelefono())
                .saldo(registrationRequest.getSaldo())
                .email(registrationRequest.getEmail())
                .password(passwordEncoder.encode(registrationRequest.getPassword()))
                .build();
        var jwtToken = jwtService.generateToken(user);
        user.setRegistrationToken(jwtToken);
        utenteRepository.saveAndFlush(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authenticationRequest.getEmail(),
                authenticationRequest.getPassword()
        ));
        var user = utenteRepository.findUtenteByEmail(authenticationRequest.getEmail());
        var jwtToken = jwtService.generateToken(user);
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
}
