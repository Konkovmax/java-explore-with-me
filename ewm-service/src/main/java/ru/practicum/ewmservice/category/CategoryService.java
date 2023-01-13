package ru.practicum.ewmservice.category;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewmservice.exception.ConflictException;
import ru.practicum.ewmservice.exception.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public CategoryDto create(CategoryDto categoryDto) {
        try {
            return CategoryMapper.toCategoryDto(categoryRepository.save(CategoryMapper.toCategory(categoryDto)));
        } catch (DataIntegrityViolationException e) {
            throw new ConflictException("Categories name can't duplicated");
        }
    }

    public List<CategoryDto> getAll(Pageable pageable) {
        return categoryRepository.findAll(pageable).stream()
                .map(CategoryMapper::toCategoryDto)
                .collect(Collectors.toList());
    }

@Transactional(propagation = Propagation.NEVER)//все остальные способы, которые я нашел - не помогли(((
    // norollback, rollback, и в сервисе, и в репозитории. При том что в userService всё то же самое и работает
    public CategoryDto update(CategoryDto category) {
        Category updateCategory = categoryRepository.findById(category.getId()).orElseThrow(() -> new NotFoundException(
                String.format("Category with id: %s not found", category.getId())));
        updateCategory.setName(category.getName());
        try {
            return CategoryMapper.toCategoryDto(categoryRepository.save(updateCategory));
        } catch (DataIntegrityViolationException e) {
            throw new ConflictException("Categories name can't duplicated");
        }
    }

    public void delete(int categoryId) {
        categoryRepository.deleteById(categoryId);
    }

    public CategoryDto getCategory(int categoryId) {
        return CategoryMapper.toCategoryDto(categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException(String.format("Category with id: %s not found", categoryId))));
    }
}
