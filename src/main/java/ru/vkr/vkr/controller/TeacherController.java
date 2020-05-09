package ru.vkr.vkr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.vkr.vkr.entity.Course;
import ru.vkr.vkr.entity.Group;
import ru.vkr.vkr.service.CourseService;
import ru.vkr.vkr.service.GroupService;

import javax.validation.Valid;
import java.util.Collection;

@Controller
public class TeacherController {

    @Autowired
    private CourseService courseService;
    @Autowired
    private GroupService groupService;


    //На любой странице teacher/* будут отображаться курсы
    @ModelAttribute
    public void addAttributes(Model model) {
        Collection<Course> teacherCourses = courseService.getCoursesByCurentTeacher();
        Collection<Group> teacherGroups = groupService.getGroupsByCurrentTeacher();
        model.addAttribute("teacherCourses", teacherCourses);
        model.addAttribute("teacherGroups", teacherGroups);
    }


    @GetMapping("/teacher")
    public String mainTeacher() {
        //todo: нужно переделать главную страницу преподавателя
        return "redirect:/teacher/group-create";
    }


    @GetMapping("/teacher/group/{groupId}")
    public String groupGet(Model model, @PathVariable Long groupId) {
        //todo: нужен контроль доступа к группе (только владелец имеет доступ)
        Group group = groupService.getGroupById(groupId);
        model.addAttribute("group", group);
        return "teacher/group";
    }

    @PostMapping("/teacher/group/{groupId}")
    public String groupPost(@Valid Group groupForm, @PathVariable Long groupId) {
        //todo: тоже нужна валидация, как и в случае с курсом
        Group group = groupService.getGroupById(groupId);
        group.setName(groupForm.getName());
        groupService.saveGroup(group);
        return "redirect:/teacher/group/" + groupId;
    }


    //Страница с созданием новой группы
    @GetMapping("/teacher/group-create")
    public String groupCreateGet (Model model, Group group) {
        model.addAttribute("isCreate", true);
        return "teacher/group";
    }

    //Создание новой группы
    @PostMapping("/teacher/group-create")
    public String groupCreatePost (Model model, @Valid Group group, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {        //Ошибки валидации есть
            model.addAttribute("isCreate", true);
            return "teacher/group";
        }

        //Устанавливаем владельца группы
        groupService.setAuthorForNewGroup(group);
        groupService.saveGroup(group);

        //И переходим к группе
        return "redirect:/teacher/group/" + group.getId();
    }



    //Страница с курсом
    @GetMapping("/teacher/course/{courseId}")
    public String courseGet(Model model, @PathVariable Long courseId) {
        //todo: нужен контроль доступа к курсу (только автор имеет доступ)
        Course course = courseService.getCourseById(courseId);
        model.addAttribute("course", course);
        return "teacher/course";
    }

    //Для изменения параметров курса
    @PostMapping("/teacher/course/{courseId}")
    public String coursePost(@Valid Course courseForm, @PathVariable Long courseId, BindingResult bindingResult) {
        //todo: при создании курса валидация работает, при обновлении нет, нужно разбираться
//        if (bindingResult.hasErrors()) {        //Ошибки валидации есть
//            return "teacher/course";
//        }
        Course course = courseService.getCourseById(courseId);
        course.setName(courseForm.getName());
        courseService.saveCourse(course);
        return "redirect:/teacher/course/" + course.getId();
    }




    //Страница с созданием нового курса
    @GetMapping("/teacher/course-create")
    public String courseCreate(Model model, Course course) {
        model.addAttribute("isCreate", true);
        return "teacher/course";
    }

    //Создание нового курса
    @PostMapping("/teacher/course-create")
    public String courseCreatePost (Model model, @Valid Course course, BindingResult bindingResult) {
        //course.setName(course.getName().trim());
        if (bindingResult.hasErrors()) {        //Ошибки валидации есть
            model.addAttribute("isCreate", true);
            return "teacher/course";
        }

        //Устанавливаем автора курса
        courseService.setAuthorForNewCourse(course);
        courseService.saveCourse(course);

        //И переходим к курсу
        return "redirect:/teacher/course/" + course.getId();
    }


    //Удаление курса
    @GetMapping("/teacher/course-delete/{courseId}")
    public String deleteTeacher(Model model, @PathVariable Long courseId) {
        Course course = courseService.getCourseById(courseId);
        courseService.deleteCourse(course);
        return "redirect:/teacher";
    }

    @GetMapping("/teacher/theme")
    public String testTheme() {
        return "theme";
    }
}
