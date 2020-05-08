package ru.vkr.vkr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vkr.vkr.entity.Course;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
