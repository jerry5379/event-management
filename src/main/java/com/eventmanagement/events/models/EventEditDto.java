package com.eventmanagement.events.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventEditDto {
    private Long id;
    private String name;
    private String description;
    private String startDate;
    private String endDate;
    private String type;
    private String webLink;

}
