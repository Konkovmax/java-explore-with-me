package ru.practicum.ewmservice.category;

public class CategoryMapper {
    public static CategoryDto toCategoryDto(Category category) {
        return new CategoryDto(
                category.getId(),
                category.getName()
        );
    }

    public static Category toCategory(CategoryDto category) {
        return new Category(
                category.getId(),
                category.getName()
        );
    }
}
