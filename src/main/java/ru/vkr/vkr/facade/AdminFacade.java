package ru.vkr.vkr.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.vkr.vkr.domain.RandomString;
import ru.vkr.vkr.entity.User;
import ru.vkr.vkr.form.UserForm;
import ru.vkr.vkr.service.UserService;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class AdminFacade {
    @Autowired
    private UserService userService;

    public boolean addTeacher(UserForm userForm) {
        String fios = userForm.getFios().trim();
        List<String> surname = new ArrayList<>();
        List<String> name = new ArrayList<>();
        List<String> middleName = new ArrayList<>();

        StringBuilder fio = null;
        for (int i = 0; i < fios.length(); ++i) {
            if (fios.charAt(i) != '\r' || fios.charAt(i) != '\n') {
                fio.append(fios.charAt(i));
            } else {
                if (fio != null) {
                    String[] curFIO = fio.toString().split(" ");
                    if (curFIO.length != 3) {
                        return false;
                    }
                    surname.add(curFIO[0]);
                    name.add(curFIO[1]);
                    middleName.add(curFIO[2]);
                    fio = null;
                }
            }
        }
        for (int i = 0; i < surname.size(); ++i) {
            User user = new User();
            user.setUsername(RandomString.getAlphaNumericString(5));
            user.setPassword(RandomString.getAlphaNumericString(5));
        }
        RandomString.getAlphaNumericString(5);
        return true;
    }
}
