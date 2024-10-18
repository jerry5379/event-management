package com.eventmanagement.events.models;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventsDTO {
    @NotNull
    @Size(max = 50)
    private String name;
    @NotNull
    private String description;
    @NotNull
    private String startDate;
    @NotNull
    private String endDate;
    @NotNull
    private String type;
    @NotNull
    private String webLink;
    private MultipartFile eventFile;
    private MultipartFile attendeeList;
}
