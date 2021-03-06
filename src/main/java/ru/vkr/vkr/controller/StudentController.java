package ru.vkr.vkr.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.vkr.vkr.contest.Test;

import java.util.List;

@Controller
public class StudentController {
    @GetMapping("/student/genTeams")
    public String genTeams(Model model) {
        Test test = new Test();

        List<String> resultsGen = test.generate_teams();
        test.disconnect();

        model.addAttribute("resultsGen", resultsGen);
        return "gen";
    }
}
