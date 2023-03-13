package pl.go.volley.govolley.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.go.volley.govolley.protocol.ProtocolService;
import pl.go.volley.govolley.protocol.file.FileToDeleteExecutor;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;

@RestController
@RequestMapping("/api/generate-protocols")
public class GenerateProtocolAPI {

    private final Logger LOGGER = LoggerFactory.getLogger(GenerateProtocolAPI.class);
    private final ProtocolService protocolService;
    private final FileToDeleteExecutor fileToDeleteExecutor;

    public GenerateProtocolAPI(ProtocolService protocolService, FileToDeleteExecutor fileToDeleteExecutor) {
        this.protocolService = protocolService;
        this.fileToDeleteExecutor = fileToDeleteExecutor;
    }

    @PostMapping
    public ResponseEntity<?> generateProtocols(@RequestParam List<Long> gameIds) {
        try {
            LOGGER.info("Received request to generate protocols for games with IDs: {}", gameIds.stream().map(String::valueOf).collect(Collectors.joining("-")));
            Optional<File> mergedFile = protocolService.createMergedProtocolForGames(gameIds);
            if (mergedFile.isEmpty()) {
                return ResponseEntity.badRequest().build();
            }

            InputStreamResource resource = new InputStreamResource(new FileInputStream(mergedFile.get()));
            fileToDeleteExecutor.add(mergedFile.get());

            return ResponseEntity.ok()
                    .header(CONTENT_DISPOSITION, "attachment;filename=" + mergedFile.get().getName())
                    .contentType(MediaType.APPLICATION_PDF)
                    .contentLength(mergedFile.get().length())
                    .body(resource);
        } catch (Exception e) {
            LOGGER.error("Could not generate protocols. Error message: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}
