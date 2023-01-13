package ru.practicum.ewmservice.category;

import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(path = "/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping()
    public List<CategoryDto> getAll(@RequestParam(value = "from", defaultValue = "0", required = false) int from,
                                    @RequestParam(value = "size", defaultValue = "10", required = false) int size) {
        return categoryService.getAll(PageRequest.of((size > from) ? 0 : from / size, size));
    }

    @GetMapping("/{id}")
    public CategoryDto get(@PathVariable("id") int categoryId) {
        return categoryService.getCategory(categoryId);
    }

}
