package ru.practicum.ewmservice.event;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface EventRepository extends JpaRepository<Event, Integer> {

    Page<Event> getEventByInitiator_Id(int initiatorId, Pageable pageable);

    List<Event> getEventByInitiator_Id(int initiatorId);

    @Query(" SELECT b FROM Event b " +
            "WHERE b.initiator.id IN ?1")
    Page<Event> findEventByParam(Integer[] users, State[] state, Integer[] categories,
                                 Pageable pageable);

    @Query(" SELECT i FROM Event i " +
            "WHERE (upper(i.annotation) LIKE upper(concat('%', ?1, '%')) " +
            " OR upper(i.description) LIKE upper(concat('%', ?1, '%')))" +
            "AND i.category.id IN ?2 ")
    Page<Event> searchEvent(String text, Integer[] categories, boolean paid,
                            Pageable pageable);

    Event findEventByIdAndInitiatorId(int eventId, int initiatorId);

    Set<Event> findByIdIn(Set<Integer> ids);

}
