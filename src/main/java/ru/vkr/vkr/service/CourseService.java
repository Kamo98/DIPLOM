package ru.vkr.vkr.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.vkr.vkr.entity.Course;
import ru.vkr.vkr.entity.Teacher;
import ru.vkr.vkr.entity.User;
import ru.vkr.vkr.facade.AuthenticationFacade;
import ru.vkr.vkr.repository.CourseRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private AuthenticationFacade authenticationFacade;


    private static Logger logger = LoggerFactory.getLogger(UserService.class);

    public void saveCourse(Course course) {
        logger.info("save course " + course.toString());
        courseRepository.save(course);
    }

    public void deleteCourse(Course course) {
        logger.info("delete course " + course.toString());
        courseRepository.delete(course);
    }

    //Устанавливает автора создаваемого курса
    public void setAuthorForNewCourse(Course course){
        //Автор курса - текущий пользователь (преподаватель)
        course.setTeacherAuthor(authenticationFacade.getCurrentTeacher());
    }


    public Course getCourseById(Long idCourse) {
        return courseRepository.getOne(idCourse);
    }

    public Collection<Course> getCoursesByCurentTeacher() {
        return courseRepository.findByTeacherAuthor_id(authenticationFacade.getCurrentTeacher().getId());
    }
}
