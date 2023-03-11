package pl.go.volley.govolley.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.go.volley.govolley.protocol.ProtocolService;
import pl.go.volley.govolley.protocol.RoundData;

import java.util.List;

@Controller
@RequestMapping("/results")
public class ResultsController {

    private final ProtocolService protocolService;

    public ResultsController(ProtocolService protocolService) {
        this.protocolService = protocolService;
    }

    @GetMapping
    public String getResults(Model model){
        List<RoundData> roundsData = protocolService.getRoundData();
        model.addAttribute("roundsData", roundsData);

        return "results";
    }
}
