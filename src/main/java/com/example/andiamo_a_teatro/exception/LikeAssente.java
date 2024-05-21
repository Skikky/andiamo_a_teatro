package com.example.andiamo_a_teatro.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LikeAssente extends Exception {
    private Long userId;

    @Override
    public String getMessage() {
        return String.format("l'utente con id %s non ha messo mi piace alla news!",userId);
    }
}
