package com.eventmanagement.events.service.implementation;

import com.eventmanagement.common.ApiResponse;
import com.eventmanagement.events.models.Events;
import com.eventmanagement.events.models.EventsDTO;
import com.eventmanagement.events.repository.EventRepository;
import com.eventmanagement.events.service.interfaces.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class EventServiceImpl implements EventService {

    @Autowired
    private EventRepository eventRepository;
    @Override
    public ApiResponse<?> addOrUpdateEvent(EventsDTO eventDTO) {
        Events event = convertToEntity(eventDTO);
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
                existingEvent.setStartDate(eventDTO.getStartDate());
            }
            if (eventDTO.getEndDate() != null) {
                existingEvent.setEndDate(eventDTO.getEndDate());
            }
            if (eventDTO.getType() != null) {
                existingEvent.setType(eventDTO.getType());
            }
            if (eventDTO.getWebLink() != null) {
                existingEvent.setWebLink(eventDTO.getWebLink());
            }
            Events updatedEvents = eventRepository.save(existingEvent);
            return ApiResponse.success(HttpStatus.CREATED.value(), "Event Updated successfully.", updatedEvents);
        }
        Events savedEvent = eventRepository.save(event);
        EventsDTO eventsDTO=  convertToDTO(savedEvent);
        return ApiResponse.success(HttpStatus.CREATED.value(), "Event Saved successfully.", eventsDTO);
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

    //
//    @Autowired
//    private EventRepository eventRepository;
//
//    @Override
//    public Page<EventsDTO> getAllEvents(int page, int size) {
//        Pageable pageable = PageRequest.of(page, size);
//        Page<Events> eventsPage = eventRepository.findAll(pageable);
//        return eventsPage.map(this::convertToDTO);
//    }
//
//    @Override
//    public List<EventsDTO> searchEvents(String name, Date startDate, Date endDate) {
//        if (name != null && !name.isEmpty()) {
//            return eventRepository.findByNameContaining(name).stream()
//                    .map(this::convertToDTO)
//                    .collect(Collectors.toList());
//        } else if (startDate != null && endDate != null) {
////            return eventRepository.findByStartDateBetween(startDate, endDate).stream()
////                    .map(this::convertToDTO)
////                    .collect(Collectors.toList());
//
//        }
//        return List.of();
//    }
//
//    @Override
//    public EventsDTO addOrUpdateEvent(EventsDTO eventDTO) {
//        Events event = convertToEntity(eventDTO);
//        Events savedEvent = eventRepository.save(event);
//        return convertToDTO(savedEvent);
//    }
//
//    @Override
//    public void removeEvent(Long id) {
//        eventRepository.deleteById(id);
//    }
//
    private EventsDTO convertToDTO(Events event) {
        EventsDTO eventsDTO = new EventsDTO();
        eventsDTO.setId(event.getId());
        eventsDTO.setName(event.getName());
        eventsDTO.setDescription(event.getDescription());
        eventsDTO.setType(event.getType());
        eventsDTO.setEndDate(event.getEndDate());
        eventsDTO.setStartDate(event.getStartDate());
        eventsDTO.setWebLink(event.getWebLink());
        return eventsDTO;
    }
//
    private Events convertToEntity(EventsDTO eventDTO) {
        Events event = new Events();
        event.setId(eventDTO.getId());
        event.setName(eventDTO.getName());
        event.setDescription(eventDTO.getDescription());
        event.setStartDate(eventDTO.getStartDate());
        event.setEndDate(eventDTO.getEndDate());
        event.setType(eventDTO.getType());
        event.setWebLink(eventDTO.getWebLink());
        return event;
    }
}
