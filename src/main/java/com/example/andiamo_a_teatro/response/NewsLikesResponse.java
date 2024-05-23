package com.example.andiamo_a_teatro.response;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewsLikesResponse {
    private Integer likesCount;
    private Set<Long> userIds;
}
