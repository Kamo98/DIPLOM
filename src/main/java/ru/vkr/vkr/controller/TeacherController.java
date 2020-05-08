package ru.vkr.vkr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.vkr.vkr.entity.Course;
import ru.vkr.vkr.service.CourseService;

import javax.validation.Valid;

@Controller
public class TeacherController {

    @Autowired
    CourseService courseService;

    @GetMapping("/teacher/group")
    public String getGroup() {
        return "group";
    }


    @GetMapping("/teacher/group-create")
    public String groupCreateGet (Model model) {
        model.addAttribute("isCreate", true);
        return "group";
    }



    //Страница с курсом
    @GetMapping("/teacher/course")
    public String courseGet(Course course) {
        return "course";
    }

    //Страница с курсом
    @PostMapping("/teacher/course")
    public String coursePost(Course course) {
        return "course";
    }




    //Страница с созданием нового курса
    @GetMapping("/teacher/course-create")
    public String courseCreate(Model model, Course course) {
        model.addAttribute("isCreate", true);
        return "course";
    }

    //Страница с созданием нового курса
    @PostMapping("/teacher/course-create")
    public String courseCreatePost (Model model, @Valid Course course, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {        //Ошибки валидации есть


            model.addAttribute("isCreate", true);
            return "course";
        }


        System.out.println(course);
        //Создаём новый курс
        courseService.saveCourse(course);

        //И переходим к нему
        return "redirect:/teacher/course";
    }

    @GetMapping("/teacher/theme")
    public String testTheme() {
        return "theme";
    }
}
