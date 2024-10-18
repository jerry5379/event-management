package com.eventmanagement.events.models;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventsDTO {
    private Long id;
    private String name;
    private String description;
    private Date startDate;
    private Date endDate;
    private String type;
    private String webLink;
}
