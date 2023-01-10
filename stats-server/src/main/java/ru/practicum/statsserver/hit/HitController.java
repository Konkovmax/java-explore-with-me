package ru.practicum.statsserver.hit;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/")
public class HitController {
    private final HitService hitService;

    public HitController(HitService hitService) {
        this.hitService = hitService;
    }

    @PostMapping("/hit")
    public Hit hit(@RequestBody Hit hit) {
        return hitService.create(hit);
    }

    @GetMapping("/stats")
    public List<Views> views(@RequestParam(value = "start") String start,
                             @RequestParam(value = "end") String end,
                             @RequestParam(value = "uris", required = false) String[] uris,
                             @RequestParam(value = "unique", defaultValue = "false", required = false) boolean unique) {
        return hitService.views(start, end, uris, unique);
    }

}
