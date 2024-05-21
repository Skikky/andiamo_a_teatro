package com.example.andiamo_a_teatro.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LikePresente extends Exception {
    private Long userId;

    @Override
    public String getMessage() {
        return String.format("l'utente con id %s ha già messo mi piace a questa news!",userId);
    }
}
