package pl.go.volley.govolley.api;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.go.volley.govolley.protocol.RoundProtocolData;
import pl.go.volley.govolley.protocol.ProtocolService;

import java.util.List;

@Controller
@RequestMapping("/generate")
public class GenerateProtocolController {
    private final ProtocolService protocolService;

    public GenerateProtocolController(ProtocolService protocolService) {
        this.protocolService = protocolService;
    }

    @GetMapping
    public String generateProtocols(Model model) {
        List<RoundProtocolData> leagueProtocolData = protocolService.getLeagueProtocolData();
        model.addAttribute("roundsData", leagueProtocolData);

        return "generate-protocols";
    }
}
