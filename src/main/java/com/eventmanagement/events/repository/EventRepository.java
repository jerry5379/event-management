package com.eventmanagement.events.repository;

import com.eventmanagement.events.models.Events;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Events, Long> {

    @Query("SELECT e FROM Events e WHERE (:name IS NULL OR e.name LIKE %:name%) " +
            "AND (:startDate IS NULL OR e.startDate >= :startDate) " +
            "AND (:endDate IS NULL OR e.endDate <= :endDate)")
    Page<Events> findAllByFilters(@Param("name") String name, @Param("startDate") Date startDate, @Param("endDate") Date endDate, Pageable pageable);
}
