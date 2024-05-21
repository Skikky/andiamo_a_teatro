package com.example.andiamo_a_teatro.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ScheduledNewsRequest {
    private String title;
    private String body;
    private Date targetDate;
}
