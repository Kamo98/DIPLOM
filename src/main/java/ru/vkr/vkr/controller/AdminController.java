package ru.vkr.vkr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.vkr.vkr.facade.AdminFacade;
import ru.vkr.vkr.form.UserForm;

@Controller
public class AdminController {
    @Autowired
    private AdminFacade adminFacade;

    @GetMapping("/admin/teachers")
    public String userList(Model model) {
        UserForm userForm = new UserForm();
        model.addAttribute("isCreate", false);
        model.addAttribute("userForm", userForm);
        return "admin/teachers";
    }

    @PostMapping("/admin/addTeachers")
    public String addTeacher(Model model, @ModelAttribute("userForm") UserForm userForm) {
        model.addAttribute("isCreate", false);
        if (!adminFacade.addTeacher(userForm)) {
            model.addAttribute(userForm);
            return "admin/teachers";
        }
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
