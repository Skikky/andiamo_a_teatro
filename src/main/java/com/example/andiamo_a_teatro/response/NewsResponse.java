package com.example.andiamo_a_teatro.response;

import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewsResponse {
    private String title;
    private String body;
    private Integer likes;
    private Set<Long> likedByUsersId = new HashSet<>();
}
