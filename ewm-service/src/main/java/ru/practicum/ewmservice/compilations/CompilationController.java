package ru.practicum.ewmservice.compilations;

import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(path = "/compilations")
public class CompilationController {
    private final CompilationService compilationService;

    public CompilationController(CompilationService compilationService) {
        this.compilationService = compilationService;
    }

    @GetMapping()
    public List<Compilation> getAll(@RequestParam(value = "from", defaultValue = "0", required = false) int from,
                                    @RequestParam(value = "size", defaultValue = "10", required = false) int size) {
        return compilationService.getAll(PageRequest.of((size > from) ? 0 : from / size, size));
    }

    @GetMapping("/{id}")
    public Compilation get(@PathVariable("id") Integer compilationId) {
        return compilationService.get(compilationId);
    }

}
