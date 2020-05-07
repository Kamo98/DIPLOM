package ru.vkr.vkr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.vkr.vkr.domain.ROLE;
import ru.vkr.vkr.entity.Teacher;
import ru.vkr.vkr.facade.AdminFacade;
import ru.vkr.vkr.form.UserForm;
import ru.vkr.vkr.repository.TeacherRepository;

import java.util.List;

@Controller
public class AdminController {
    @Autowired
    private AdminFacade adminFacade;
    @Autowired
    private TeacherRepository teacherRepository;

    @GetMapping("/admin/teachers")
    public String userList(Model model) {
        UserForm userForm = new UserForm();
        List<Teacher> teachers = teacherRepository.findAll();

        model.addAttribute("userForm", userForm);
        model.addAttribute("teachers", teachers);
        return "admin/teachers";
    }

    @PostMapping("/admin/addTeachers")
    public String addTeacher(Model model, @ModelAttribute("userForm") UserForm userForm) {
        System.out.println("/admin/addTeachers");
        if (!adminFacade.addUsers(userForm, ROLE.ROLE_TEACHER)) {
            model.addAttribute(userForm);

            System.out.println("ОШИБКА, пользователи не добавлены");
            return "admin/teachers";
        }
        return "redirect:/admin/teachers";
    }

    @ResponseBody
    @PostMapping ("/admin/editTeacher/")
    public String editFioTeacher(Model model, @RequestParam(value = "fio") String newFio,
                                              @RequestParam(value = "idTeacher") Long idTeacher) {
        adminFacade.editTeacher(idTeacher, newFio);
        return "redirect:/admin/teachers";
    }


    @GetMapping("/admin/delTeacher/{teacherId}")
    public String deleteTeacher(Model model, @PathVariable Long teacherId) {
        adminFacade.deleteTeacher(teacherId);
        return "redirect:/admin/teachers";
    }


   /* @GetMapping("/admin")
    public String userList(Model model) {
        model.addAttribute("allUsers", userService.allUsers());
        return "admin";
    }

    @PostMapping("/admin")
    public String  deleteUser(@RequestParam(required = true, defaultValue = "" ) Long userId,
                              @RequestParam(required = true, defaultValue = "" ) String action,
                              Model model) {
        if (action.equals("delete")){
            userService.deleteUser(userId);
        }
        return "redirect:/admin";
    }

    @GetMapping("/admin/gt/{userId}")
    public String  gtUser(@PathVariable("userId") Long userId, Model model) {
        model.addAttribute("allUsers", userService.usergtList(userId));
        return "admin";
    }*/
}
