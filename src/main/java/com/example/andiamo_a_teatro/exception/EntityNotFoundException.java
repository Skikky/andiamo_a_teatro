package com.example.andiamo_a_teatro.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EntityNotFoundException extends Exception {

    private Long entityId;
    private String entityName;

    @Override
    public String getMessage() {
        return String.format("%s non trovato con id: %d",entityName,entityId);
    }
}
