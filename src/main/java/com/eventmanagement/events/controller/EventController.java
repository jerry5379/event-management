package com.eventmanagement.events.controller;


import com.eventmanagement.common.ApiResponse;
import com.eventmanagement.events.annotations.Authorization;
import com.eventmanagement.events.models.EventsDTO;
import com.eventmanagement.events.service.interfaces.EventService;


import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/event")
public class EventController {

    @Autowired
    private EventService eventService;

    @Operation(
            tags = "Events API",
            description = "View all the created the events.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Success"
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "500",
                            description = "Internal Server Error"
                    )
            }
    )
    @Authorization
    @GetMapping
    public ResponseEntity<ApiResponse<?>> viewEvents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String name,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {

        Pageable pageable = PageRequest.of(page, size);
        Page<EventsDTO> eventsPage = eventService.getEvents(pageable, name, startDate, endDate);

        Map<String, Object> response = new HashMap<>();
        response.put("events", eventsPage.getContent());
        response.put("totalElements", eventsPage.getTotalElements());
        response.put("totalPages", eventsPage.getTotalPages());
        response.put("currentPage", eventsPage.getNumber());

        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Events retrieved successfully", response));
    }

    @Operation(
            tags = "Events API",
            description = "Create the proper events.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Success"
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "500",
                            description = "Internal Server Error"
                    )
            }
    )
    @PostMapping("/create")
    @Authorization
    public ResponseEntity<?> addEvent(@RequestBody EventsDTO eventDTO) {
        ApiResponse<?> savedEvent = eventService.addOrUpdateEvent(eventDTO);
        return ResponseEntity.ok(savedEvent);
    }

    @Operation(
            tags = "Events API",
            description = "Edit the events.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Success"
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "500",
                            description = "Internal Server Error"
                    )
            }
    )
    @PostMapping("edit/{id}")
    @Authorization
    public ResponseEntity<?> editEvent(@PathVariable Long id, @RequestBody EventsDTO eventDTO) {
        eventDTO.setId(id);
        ApiResponse<?> updatedEvent = eventService.addOrUpdateEvent(eventDTO);
        return ResponseEntity.ok(updatedEvent);
    }

    @Operation(
            tags = "Events API",
            description = "Delete events.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Success"
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "500",
                            description = "Internal Server Error"
                    )
            }
    )
    @PostMapping("delete/{id}")
    @Authorization
    public ResponseEntity<?> deleteEvent(@PathVariable Long id) {
        ApiResponse<?> isDeleted = eventService.deleteEvent(id);
        return ResponseEntity.ok(isDeleted);
    }

}
