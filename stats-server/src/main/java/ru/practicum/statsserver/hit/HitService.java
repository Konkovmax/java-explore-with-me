package ru.practicum.statsserver.hit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class HitService {
    private final HitRepository hitRepository;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public HitService(HitRepository hitRepository) {
        this.hitRepository = hitRepository;
    }

    public Hit create(Hit hit) {
        return hitRepository.save(hit);
    }

    public List<Views> views(String start, String end, String[] uris, boolean unique) {
        Views view = new Views();
        view.setUri(uris.toString());
        view.setApp("main");
        List<Views> views = new ArrayList<>();
        if (unique) {
            view.setHits(hitRepository.countDistinctByUriInAndTimeStampIsBetween(uris,
                    LocalDateTime.parse(start, formatter), LocalDateTime.parse(end, formatter)));
        } else view.setHits(hitRepository.countByUriInAndTimeStampIsBetween(uris,
                LocalDateTime.parse(start, formatter), LocalDateTime.parse(end, formatter)));
        views.add(view);
        return views;
    }
}
