package com.example.andiamo_a_teatro.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewsRequest {
    private String title;
    private String body;
}
