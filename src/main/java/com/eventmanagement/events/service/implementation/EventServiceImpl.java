package com.eventmanagement.events.service.implementation;

import com.eventmanagement.common.ApiResponse;
import com.eventmanagement.events.models.EventEditDto;
import com.eventmanagement.events.models.Events;
import com.eventmanagement.events.models.EventsDTO;
import com.eventmanagement.events.repository.EventRepository;
import com.eventmanagement.events.service.interfaces.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;

@Service
public class EventServiceImpl implements EventService {

    @Autowired
    private EventRepository eventRepository;
    @Override
    public ApiResponse<?> addOrUpdateEvent(EventsDTO eventDTO) {
        Events event = convertToEntity(eventDTO);
        Events savedEvent = eventRepository.save(event);
        return ApiResponse.success(HttpStatus.OK.value(), "Event Saved successfully.", savedEvent.getName());
    }

    @Override
    public ApiResponse<?> updateEvent(EventEditDto eventDTO) {
            Optional<Events> existingEventOptional = eventRepository.findById(eventDTO.getId());
            if (existingEventOptional.isPresent()) {
                Events existingEvent = existingEventOptional.get();

                if (eventDTO.getName() != null) {
                    existingEvent.setName(eventDTO.getName());
                }
                if (eventDTO.getDescription() != null) {
                    existingEvent.setDescription(eventDTO.getDescription());
                }
                if (eventDTO.getStartDate() != null) {
                    existingEvent.setStartDate(convertToDate(eventDTO.getStartDate()));
                }
                if (eventDTO.getEndDate() != null) {
                    existingEvent.setEndDate(convertToDate(eventDTO.getEndDate()));
                }
                if (eventDTO.getType() != null) {
                    existingEvent.setType(eventDTO.getType());
                }
                if (eventDTO.getWebLink() != null) {
                    existingEvent.setWebLink(eventDTO.getWebLink());
                }
                Events updatedEvents = eventRepository.save(existingEvent);
                return ApiResponse.success(HttpStatus.OK.value(), "Event Updated successfully.", updatedEvents.getName());
            }
        return ApiResponse.success(HttpStatus.NOT_FOUND.value(), "No Data Found.", null);

    }
    @Override
    public  ApiResponse<?> deleteEvent(Long id) {
        Optional<Events> eventOptional = eventRepository.findById(id);
        if (eventOptional.isPresent()) {
            eventRepository.deleteById(id);
            return ApiResponse.success(HttpStatus.OK.value(), "Deleted successfully.", null);
        }
        return ApiResponse.success(HttpStatus.OK.value(), "Data not Found.", null);
    }

    @Override
    public Page<EventsDTO> getEvents(Pageable pageable, String name, Date startDate, Date endDate) {
        Page<Events> eventsPage = eventRepository.findAllByFilters(name, startDate, endDate, pageable);
        return eventsPage.map(this::convertToDTO);
    }


    private EventsDTO convertToDTO(Events event) {
        EventsDTO eventsDTO = new EventsDTO();
        eventsDTO.setName(event.getName());
        eventsDTO.setDescription(event.getDescription());
        eventsDTO.setType(event.getType());
        eventsDTO.setEndDate(event.getEndDate().toString());
        eventsDTO.setStartDate(event.getStartDate().toString());
        eventsDTO.setWebLink(event.getWebLink());
        return eventsDTO;
    }
//

    private Events convertToEntity(EventsDTO eventDTO){
        Events event = new Events();
        event.setName(eventDTO.getName());
        event.setDescription(eventDTO.getDescription());
        event.setStartDate(convertToDate(eventDTO.getStartDate()));
        event.setEndDate(convertToDate(eventDTO.getEndDate()));
        event.setType(eventDTO.getType());
        event.setWebLink(eventDTO.getWebLink());
        try {
            if (eventDTO.getEventFile() != null) {
                event.setEventFile(eventDTO.getEventFile().getBytes());
            }
            if (eventDTO.getAttendeeList() != null) {
                event.setAttendeeList(eventDTO.getAttendeeList().getBytes());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return event;
    }

    public static Date convertToDate(String dateString) {
        if (dateString == null || dateString.isEmpty()) {
            return null; // or throw an exception if you prefer
        }
        try {
            // Using java.time for conversion
            DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
            LocalDateTime localDateTime = LocalDateTime.parse(dateString, formatter);
            return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        } catch (Exception e) {
            // Handle parse exceptions, log or rethrow as needed
            throw new IllegalArgumentException("Invalid date format: " + dateString, e);
        }
    }
}
