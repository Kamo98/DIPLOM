package ru.vkr.vkr.controller;


import edu.csus.ecs.pc2.api.ITeam;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.GetMapping;
import ru.vkr.vkr.contest.Test;
import sun.text.normalizer.NormalizerBase;

import java.util.ArrayList;
import java.util.List;

@Controller
public class TestController {

    @GetMapping("/genTeams")
    public String genTeams(Model model) {
        Test test = new Test();

        List<String> resultsGen = test.generate_teams();
        test.disconnect();

        model.addAttribute("resultsGen", resultsGen);
        return "gen";
    }

    @GetMapping("/print")
    public String ptint(Model model) {
        Test test = new Test();
        ITeam[] teams_ = test.get_all_teams();
        List<String> teams = new ArrayList<>();
        for (ITeam team: teams_) {
            teams.add(team.getDisplayName());
        }

        model.addAttribute("teams", teams);
        //test.generate_teams();
        test.disconnect();

        return "print";
    }

    @GetMapping("/group")
    public String testGroup() {
        return "group";
    }

    @GetMapping("/group-create")
    public String group_create(Model model) {
        model.addAttribute("isCreate", true);
        return "group";
    }

    @GetMapping("/course")
    public String testCourse() {
        return "course";
    }


    @GetMapping("/theme")
    public String testTheme() {
        return "theme";
    }



}
