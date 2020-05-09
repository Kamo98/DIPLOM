package ru.vkr.vkr.controller;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.vkr.vkr.domain.ROLE;
import ru.vkr.vkr.entity.Teacher;
import ru.vkr.vkr.facade.AdminFacade;
import ru.vkr.vkr.form.UserForm;
import ru.vkr.vkr.repository.TeacherRepository;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Controller
public class AdminController {
    private static Logger logger = LoggerFactory.getLogger(AdminController.class);

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
        return newFio;
    }


    @GetMapping("/admin/delTeacher/{teacherId}")
    public String deleteTeacher(Model model, @PathVariable Long teacherId) {
        adminFacade.deleteTeacher(teacherId);
        return "redirect:/admin/teachers";
    }

    @RequestMapping(value = "/admin/getpdf", method = RequestMethod.GET)
    public ResponseEntity<Object> downloadFile() throws IOException
    {
        String filename = "C:/tmp/report.pdf";
        File file = new File(filename);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition",
                String.format("attachment; filename=\"%s\"", file.getName()));
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        ResponseEntity<Object> responseEntity = ResponseEntity.ok().headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/txt")).body(resource);

        return responseEntity;
    }

   /* @RequestMapping(value="/admin/getpdf", method=RequestMethod.POST)
    public ResponseEntity<Byte[]> getPDF() {
        // generate the file
        try {
            getMyDocument();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // retrieve contents of "C:/tmp/report.pdf" that were written in showHelp
        Byte[] contents = getByteDocument();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        // Here you have to set the actual filename of your pdf
        String filename = "output.pdf";
        headers.setContentDispositionFormData(filename, filename);
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        ResponseEntity<Byte[]> response = new ResponseEntity<>(contents, headers, HttpStatus.OK);
        return response;
    }

    public static Byte[] getByteDocument () {
        java.nio.file.Path path = Paths.get("C:/tmp/report.pdf");
        ArrayList<Byte> doc = new ArrayList<>();
        try (FileInputStream fileInputStream = new FileInputStream(String.valueOf(path));
             BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream)) {
            int count = 0;
            int current = 0;
            while ((current = fileInputStream.read()) != -1) {
                count = fileInputStream.read();
                for (int i = 0; i < count; ++i) {
                    doc.add((byte) current);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Byte[] result = new Byte[doc.size()];
        return doc.toArray(result);
    }

   */
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
