package ru.practicum.ewmservice.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {

    List<User> findByIdIn(Integer[] ids);
}
