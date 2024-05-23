package com.example.andiamo_a_teatro.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserLikesResponse {
    private int likeCount;
    private Set<Long> newsIds;
}
