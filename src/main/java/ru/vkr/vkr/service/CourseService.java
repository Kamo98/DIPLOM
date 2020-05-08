package ru.vkr.vkr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vkr.vkr.entity.Course;
import ru.vkr.vkr.repository.CourseRepository;

@Service
public class CourseService {
    @Autowired
    CourseRepository courseRepository;

    public void saveCourse(Course course) {
        courseRepository.save(course);
    }
}
