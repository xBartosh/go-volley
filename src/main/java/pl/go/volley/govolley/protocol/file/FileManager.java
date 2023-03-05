package pl.go.volley.govolley.protocol.file;

import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.go.volley.govolley.exception.CannotCopyFileException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.stream.Stream;

public class FileManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileManager.class);

    public static File mergeFiles() throws IOException {
        PDFMergerUtility pdfMergerUtility = new PDFMergerUtility();
        pdfMergerUtility.setDestinationFileName(PathsToProtocols.PATH_TO_MERGED_PROTOCOL_FILE);
        File generatedProtocolsFolder = new File(PathsToProtocols.PATH_TO_GENERATED_PROTOCOLS_FOLDER);
        File[] files = generatedProtocolsFolder.listFiles();
        for (File file : files){
            pdfMergerUtility.addSource(file);
        }

        pdfMergerUtility.mergeDocuments(MemoryUsageSetting.setupMainMemoryOnly());
        LOGGER.info("Successfully merged files!");

        return new File(PathsToProtocols.PATH_TO_MERGED_PROTOCOL_FILE);
    }


    public static void copyFile(File from, File to) throws CannotCopyFileException{
        try {
            com.google.common.io.Files.copy(from, to);
        } catch (IOException e) {
            LOGGER.error("Cannot copy the file from file: {} to file: {}, error message: {}", from.getName(), to.getName(), e.getMessage());
            throw new CannotCopyFileException();
        }

        LOGGER.info("Successfully copied file from file: {} to file: {}", from.getName(), to.getName());
    }
    public static Optional<String> createDirectoryAndDeleteIfExists(Path path) {
        if (Files.exists(path)) {
            Optional<String> errorMessage = deleteDirectory(path);
            if (errorMessage.isPresent()) {
                return errorMessage;
            }
        }

        return createDirectory(path);
    }

    public static Optional<String> deleteDirectory(Path path) {
        try (Stream<Path> walk = Files.walk(path)) {
            walk.map(Path::toFile)
                    .sorted((o1, o2) -> -o1.compareTo(o2))
                    .forEach(File::delete);
        } catch (IOException e) {
            LOGGER.error("Cannot delete director, error message: {}", e.getMessage());
            return Optional.of("Cannot delete directory");
        }

        LOGGER.info("Successfully deleted directory with path: {}", path);
        return Optional.empty();
    }

    public static Optional<String> createDirectory(Path path){
        try {
            Files.createDirectory(path);
        } catch (IOException e) {
            LOGGER.error("Cannot delete directory, error message: {}", e.getMessage());
            return Optional.of("Error creating directory");
        }

        LOGGER.info("Successfully created directory with path: {}", path);
        return Optional.empty();
    }
}
