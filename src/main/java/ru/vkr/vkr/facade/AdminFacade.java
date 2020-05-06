package ru.vkr.vkr.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.vkr.vkr.domain.ROLE;
import ru.vkr.vkr.form.UserForm;
import ru.vkr.vkr.service.UserService;

@Component
public class AdminFacade {
    @Autowired
    private UserService userService;

    public boolean addUsers(UserForm userForm, ROLE role) {
        return userService.addUsers(userForm, ROLE.ROLE_TEACHER);
    }
}
