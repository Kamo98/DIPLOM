package ru.vkr.vkr.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.vkr.vkr.domain.RandomString;
import ru.vkr.vkr.domain.Translit;
import ru.vkr.vkr.entity.Student;
import ru.vkr.vkr.entity.Teacher;
import ru.vkr.vkr.entity.User;
import ru.vkr.vkr.form.UserForm;
import ru.vkr.vkr.repository.RoleRepository;
import ru.vkr.vkr.repository.StudentRepository;
import ru.vkr.vkr.repository.TeacherRepository;
import ru.vkr.vkr.service.UserService;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class AdminFacade {

    //todo: По-хорошему бы из таблицы значения id ролей вытащить, но пока так
    public enum ROLE {
        ROLE_TEACHER(1L),
        ROLE_ADMIN(2L),
        ROLE_STUDENT(3L);

        private Long id;
        private ROLE(Long id){
            this.id = id;
        }
        public Long getId() {return id;}
    };


    @Autowired
    private UserService userService;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private TeacherRepository teacherRepository;


    public boolean addUsers(UserForm userForm, ROLE role) {
        String fios = userForm.getFios().trim();
        List<String> surname = new ArrayList<>();
        List<String> name = new ArrayList<>();
        List<String> middleName = new ArrayList<>();


        //todo: оно не работало, поэтому написл по-своему)
//        StringBuilder fio = new StringBuilder();
//        for (int i = 0; i < fios.length(); ++i) {
//            if (fios.charAt(i) != '\r' || fios.charAt(i) != '\n') {
//                fio.append(fios.charAt(i));
//            } else {
//                if (fio != null) {
//                    String[] curFIO = fio.toString().split(" ");
//                    if (curFIO.length != 3) {
//                        return false;
//                    }
//                    surname.add(curFIO[0]);
//                    name.add(curFIO[1]);
//                    middleName.add(curFIO[2]);
//                    fio = new StringBuilder();
//                }
//            }
//        }

        //todo: я поппытался написать регулярное выражения для split, но оно всё равно неадекватно работает, поэтому просто прверяю, что фио непустое
        String[] fiosArr = fios.split("\n+|\r+");
        for (String fio : fiosArr) {
            if (!fio.trim().equals("")) {
                System.out.println("AdminFacade.addUsers: fio = " + fio);
                String[] curFIO = fio.trim().split(" +");
                if (curFIO.length < 3) {
                    //todo: тут либо выводить ошибку, что некорректное фио, либо забить)
                } else {
                    System.out.println("AdminFacade.addUsers: surname = " + curFIO[0]);
                    System.out.println("AdminFacade.addUsers: name = " + curFIO[1]);
                    System.out.println("AdminFacade.addUsers: middleName = " + curFIO[2]);
                    surname.add(curFIO[0].trim());
                    name.add(curFIO[1].trim());
                    middleName.add(curFIO[2].trim());
                }
            }
        }


        System.out.println("AdminFacade.addUsers: count surnames = " + surname.size());
        for (int i = 0; i < surname.size(); ++i) {
            User user = new User();

            //todo: логин сделал как транслит фио, по идее это норм, но длину при этом мы не контролируем
            String login = Translit.fio2login(surname.get(i), name.get(i), middleName.get(i));
            String password = RandomString.getAlphaNumericString(5);
            user.setUsername(login);
            user.setPassword(password);
            user.setRole(roleRepository.getOne(role.getId()));
            userService.saveUser(user);

            System.out.println("AdminFacade.addUsers: fio = " + fiosArr[i] + "  login = " + login + "  pass = " + password);

            //Определяем, кого добавлять, студента или преподавателя
            if (role == ROLE.ROLE_STUDENT) {
                Student student = new Student();
                student.setMiddleName(middleName.get(i));
                student.setName(name.get(i));
                student.setSurname(surname.get(i));
                student.setUser(user);
                studentRepository.save(student);
            } else if (role == ROLE.ROLE_TEACHER) {
                Teacher teacher = new Teacher();
                teacher.setMiddleName(middleName.get(i));
                teacher.setName(name.get(i));
                teacher.setSurname(surname.get(i));
                teacher.setUser(user);
                teacherRepository.save(teacher);
            }

        }

        //RandomString.getAlphaNumericString(5);
        return true;
    }
}
