package pl.go.volley.govolley.protocol.generator;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import pl.go.volley.govolley.exception.CannotCopyFileException;
import pl.go.volley.govolley.game.Game;
import pl.go.volley.govolley.protocol.file.FileManager;
import pl.go.volley.govolley.protocol.file.PathCreatorUtil;
import pl.go.volley.govolley.protocol.file.PathsToProtocols;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.itextpdf.text.PageSize.A4;
import static pl.go.volley.govolley.protocol.file.PathsToProtocols.PATH_TO_PROTOCOLS_FOLDER;

@Component
public class ProtocolGenerator {

    private final Logger LOGGER = LoggerFactory.getLogger(ProtocolGenerator.class);

    public Optional<File> generateMergedProtocolForGames(List<Game> games) throws IOException {
        LocalDateTime nowDate = LocalDateTime.now();
        String pathToGenerated = PathsToProtocols.createPathToGeneratedProtocolsFolder(nowDate);
        String pathToMerge = PathsToProtocols.createPathToMergedProtocolFile(nowDate);
        Path pathToGeneratedProtocolsFolder = Paths.get(pathToGenerated);

        LOGGER.info("Going to generate protocols to dir: {}", pathToGenerated);

        Optional<String> errorMessage = FileManager.createDirectoryAndDeleteIfExists(pathToGeneratedProtocolsFolder);
        if (errorMessage.isPresent()) {
            LOGGER.error("{}", errorMessage.get());
            return Optional.empty();
        }

        for (Game game : games) {
            try {
                generateProtocolFromGame(game, pathToGenerated);
            } catch (CannotCopyFileException | IOException | DocumentException e) {
                throw new RuntimeException(e);
            }
        }

        File mergedFile = FileManager.mergeFiles(pathToGenerated, pathToMerge);
        FileManager.deleteDirectory(pathToGeneratedProtocolsFolder);

        return Optional.of(mergedFile);
    }


    public void generateProtocolFromGame(Game game, String pathToGenerate) throws CannotCopyFileException, IOException, DocumentException {
        LOGGER.info("Going to generate protocol for game with ID: {}, to path: {}", game.getId(), pathToGenerate);
        File gameProtocol = new File(pathToGenerate + PathCreatorUtil.createFileNameForGameProtocol(game));
        Document document = new Document(A4, 30, 30, 0, 5);
        PdfWriter.getInstance(document, new FileOutputStream(gameProtocol));
        document.open();

        BaseFont baseFont = BaseFont.createFont(PATH_TO_PROTOCOLS_FOLDER + "font/arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font font = new Font(baseFont, 12, Font.BOLD);
        ProtocolParts protocolParts = ProtocolPartsFactory.createProtocolParts(document, game, font);

        document.add(protocolParts.getImage());
        document.add(protocolParts.getTitle());
        document.add(protocolParts.getField());
        document.add(protocolParts.getRound());
        document.add(protocolParts.getGameTable());
        document.add(protocolParts.getScoreAndRefSign());
        document.add(protocolParts.getGameResultsTable());
        document.add(protocolParts.getMvpTable());
        document.add(protocolParts.getCaptainsSigns());

        document.close();
        LOGGER.info("Protocol successfully generated for game with ID: {}, to path: {}", game.getId(), pathToGenerate);
    }
}
