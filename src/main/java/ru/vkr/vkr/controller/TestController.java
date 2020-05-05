package ru.vkr.vkr.controller;


import edu.csus.ecs.pc2.api.ITeam;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.vkr.vkr.contest.Test;

import java.util.ArrayList;
import java.util.List;

@Controller
public class TestController {
    @GetMapping("/print")
    public String ptint(Model model) {
        Test test = new Test();
        ITeam[] teams_ = test.get_all_teams();
        List<String> teams = new ArrayList<>();
        for (ITeam team : teams_) {
            teams.add(team.getDisplayName());
        }

        model.addAttribute("teams", teams);

        test.disconnect();

        return "print";
    }

    @GetMapping("/403")
    public String error403() {
        return "/error/403";
    }
}
