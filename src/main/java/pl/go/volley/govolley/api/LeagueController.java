package pl.go.volley.govolley.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/leagues")
public class LeagueController {

    @GetMapping
    public String getLeagues(){
        return "leagues";
    }

}
