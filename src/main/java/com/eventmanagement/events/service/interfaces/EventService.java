package com.eventmanagement.events.service.interfaces;

import com.eventmanagement.common.ApiResponse;
import com.eventmanagement.events.models.EventEditDto;
import com.eventmanagement.events.models.EventsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.Date;

public interface EventService {

    ApiResponse<?> addOrUpdateEvent(EventsDTO eventDTO);

    ApiResponse<?> updateEvent(EventEditDto eventDTO);

    ApiResponse<?>  deleteEvent(Long id);

    Page<EventsDTO> getEvents(Pageable pageable, String name, Date startDate, Date endDate);
}
