package pl.go.volley.govolley.api;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.go.volley.govolley.protocol.ProtocolService;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;

@RestController
@RequestMapping("/api/generate-protocols")
public class GenerateProtocolAPI {
    private final ProtocolService protocolService;

    public GenerateProtocolAPI(ProtocolService protocolService) {
        this.protocolService = protocolService;
    }

    @PostMapping
    public ResponseEntity<?> generateProtocols(@RequestParam List<Long> gameIds) {
        try {
            Optional<File> mergedFile = protocolService.createMergedProtocolForGames(gameIds);
            if (mergedFile.isEmpty()) {
                return ResponseEntity.badRequest().build();
            }

            InputStreamResource resource = new InputStreamResource(new FileInputStream(mergedFile.get()));

            return ResponseEntity.ok()
                    .header(CONTENT_DISPOSITION, "attachment;filename=" + mergedFile.get().getName())
                    .contentType(MediaType.APPLICATION_PDF)
                    .contentLength(mergedFile.get().length())
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
