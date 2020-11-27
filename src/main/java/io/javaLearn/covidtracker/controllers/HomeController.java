package io.javaLearn.covidtracker.controllers;

import io.javaLearn.covidtracker.models.LocationStates;
import io.javaLearn.covidtracker.services.CoronaVirusDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {
    @Autowired
    CoronaVirusDataService coronaVirusDataService;

    @GetMapping("/")
    public String home(Model model){
        List<LocationStates> allState = coronaVirusDataService.getAllState();
        int getLatesTotalCases = allState.stream().mapToInt(stat -> stat.getLatesCases()).sum();
        int getLatesNewCases = allState.stream().mapToInt(stat -> stat.getDiffFromPrevDay()).sum();
        model.addAttribute("allState", allState );
        model.addAttribute("getLatesTotalCases", getLatesTotalCases);
        model.addAttribute("getLatesNewCases", getLatesNewCases);
        return "home";
    };
}
