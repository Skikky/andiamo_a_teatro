package com.example.andiamo_a_teatro.services;

import com.example.andiamo_a_teatro.entities.TokenBlackList;
import com.example.andiamo_a_teatro.repositories.TokenBlackListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TokenBlackListService {
    @Autowired
    private TokenBlackListRepository tokenBlackListRepository;

    public List<String> tokenNotValidFromClienteById(Long id_cliente) {
        return tokenBlackListRepository.getTokenBlackListFromClienteId(id_cliente)
                .stream()
                .map(TokenBlackList::getToken)
                .toList();
    }

    public void createTokenBlackList(TokenBlackList tokenBlackList) {
        tokenBlackListRepository.saveAndFlush(tokenBlackList);
    }
}
