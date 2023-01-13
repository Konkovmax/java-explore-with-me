package ru.practicum.ewmservice.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewmservice.exception.ConflictException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDto create(UserDto user) {
        try {
            return UserMapper.toUserDto(userRepository.save(UserMapper.toUser(user)));
        } catch (DataIntegrityViolationException e) {
            throw new ConflictException("Email can't duplicated");
        }
    }

    public List<UserDto> getAll(Integer[] ids) {
        return userRepository.findByIdIn(ids).stream()
                .map(UserMapper::toUserDto)
                .collect(Collectors.toList());
    }

    public void delete(int userId) {
        userRepository.deleteById(userId);
    }

}
