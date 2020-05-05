package ru.vkr.vkr.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TeacherController {

    @GetMapping("/teacher/group")
    public String getGroup() {
        return "group";
    }

    @GetMapping("/teacher/group-create")
    public String groupCreate(Model model) {
        model.addAttribute("isCreate", true);
        return "group";
    }


    @GetMapping("/teacher/course")
    public String getCourse() {
        return "course";
    }


    @GetMapping("/teacher/course-create")
    public String courseCreate(Model model) {
        model.addAttribute("isCreate", true);
        return "course";
    }

    @GetMapping("/teacher/theme")
    public String testTheme() {
        return "theme";
    }
}
